package com.korealong.salesup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.korealong.salesup.R;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.adapter.SaleProductAdapter;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Factory;
import com.korealong.salesup.model.Product;
import com.korealong.salesup.model.SaleProduct;
import com.korealong.salesup.utils.PreferenceUtils;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbarHome;
    public static ListView viewProducts;
    RecyclerView viewFactories,viewSaleProducts;
    ProgressBar progress_loadmore;
    NavigationView navigation_view;
    DrawerLayout drawer_layout;
    Button btnCreateExhibition, btnCancelExhibition, btnRoute, btnReport, btnLogout;
    View layout_home;

    ArrayList<Factory> arrFactory;
    FactoryAdapter factoryAdapter;

    ArrayList<SaleProduct> arrSaleProduct;
    SaleProductAdapter saleProductAdapter;

    ArrayList<Product> arrProduct;
    ProductAdapter productAdapter;

    ServerHelper serverHelper;
    int pagination= 1;
    public static boolean noMoreProduct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initObject();
        initEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        toolbarHome = findViewById(R.id.toolbar_home);
        viewProducts = findViewById(R.id.listview_products);
        viewFactories = findViewById(R.id.recyclerview_factories);
        viewSaleProducts = findViewById(R.id.recyclerview_saleproducts);
        progress_loadmore = findViewById(R.id.progress_loadmore);
        navigation_view = findViewById(R.id.navigation_view);
        btnLogout = findViewById(R.id.btn_logout);
        btnRoute = findViewById(R.id.btn_route);
        btnCreateExhibition = findViewById(R.id.btn_create_exhibition);
        btnReport = findViewById(R.id.btn_report);

        drawer_layout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbarHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarHome.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        LinearLayoutManager llmSale = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        viewSaleProducts.setLayoutManager(llmSale);
        arrSaleProduct = new ArrayList<>();
        saleProductAdapter = new SaleProductAdapter(this,arrSaleProduct);

        arrProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(this,arrProduct);
        viewProducts.setAdapter(productAdapter);
    }

    private void initObject() {
        serverHelper = new ServerHelper();
    }

    private void initEvent() {
        toolbarHome.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.clearCurrentUser(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMap = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intentMap);
            }
        });

        btnCreateExhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOrder = new Intent(getApplicationContext(), CreateOrder.class);
                startActivity(intentOrder);
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReport = new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(intentReport);
            }
        });
    }
}
