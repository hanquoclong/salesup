package com.korealong.salesup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.ImageView;

import android.widget.ListView;

import com.korealong.salesup.activities.DetailProductActivity;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.adapter.SaleProductAdapter;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Factorys;
import com.korealong.salesup.model.Products;
import com.korealong.salesup.model.SaleProduct;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    Toolbar toolbar_home;
    ListView listview_product;
    RecyclerView recyclerview_factory,recyclerview_saleproduct;
    View footer_progress;

    ArrayList<Factorys> arrFac;
    FactoryAdapter factoryAdapter;

    ArrayList<Products> arrProduct;
    ProductAdapter productAdapter;

    ArrayList<SaleProduct> arrSale;
    SaleProductAdapter saleProductAdapter;

    ServerHelper serverHelper;
    int page = 0;
    boolean isLoading = false;
    public static boolean limitdata = false;
    mHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        initView();
        iniObject();
        serverHelper.getFactoryFromServer(arrFac,factoryAdapter,this);
        serverHelper.getSaleProductFromServer(this,saleProductAdapter,arrSale);
        loadMoreProduct();
    }

    private void loadMoreProduct() {
        listview_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailProductActivity.class);
                intent.putExtra("idproduct",arrProduct.get(position));
                startActivity(intent);
            }
        });
        listview_product.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount & totalItemCount!=0 && isLoading == false && limitdata ==  false)
                {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void iniObject() {
        serverHelper = new ServerHelper();
        mHandler = new mHandler();
    }


    private void initView() {
        recyclerview_factory = findViewById(R.id.recyclerViewItems);
        recyclerview_saleproduct = findViewById(R.id.recyclerview_saleproduct);
        toolbar_home = findViewById(R.id.toolbar_home);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer_progress = inflater.inflate(R.layout.layout_progressbar,null );


        LinearLayoutManager llmFac = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerview_factory.setLayoutManager(llmFac);
        listview_product = findViewById(R.id.listview_product);

        arrFac = new ArrayList<>();
        factoryAdapter = new FactoryAdapter(getApplicationContext(),arrFac);
        recyclerview_factory.setAdapter(factoryAdapter);

        arrProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),arrProduct);
        listview_product.setAdapter(productAdapter);
        listview_product.addFooterView(footer_progress);

        LinearLayoutManager llmSale = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerview_saleproduct.setLayoutManager(llmSale);
        arrSale = new ArrayList<>();
        saleProductAdapter = new SaleProductAdapter(getApplicationContext(),arrSale);
        recyclerview_saleproduct.setAdapter(saleProductAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listview_product.addFooterView(footer_progress);
                    break;
                case 1:
                    serverHelper.getAllProductFromServer(getApplicationContext(),++page,arrProduct,productAdapter,listview_product,footer_progress);
                    isLoading = false;
                    break;
            }
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
