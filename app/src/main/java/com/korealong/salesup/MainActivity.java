package com.korealong.salesup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Factorys;
import com.korealong.salesup.model.Products;
import com.korealong.salesup.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.korealong.salesup.utils.Constants.URL_GET_FACTORY;
import static com.korealong.salesup.utils.Constants.idfactory;

public class MainActivity extends AppCompatActivity {

    //public RequestQueue requestQueue;

    ImageView imgSale1,imgSale2;
    Toolbar toolbar_home;
    ListView listview_product;
    RecyclerView recyclerview_factory;

    ArrayList<Factorys> arrFac;
    FactoryAdapter factoryAdapter;

    ArrayList<Products> arrProduct;
    ProductAdapter productAdapter;

    ServerHelper serverHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        initView();
        iniObject();
        serverHelper.getFactoryFromServer(arrFac,factoryAdapter,this);
        imgSale1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, ""+idfactory, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void iniObject() {
        serverHelper = new ServerHelper();
    }


    private void initView() {
        imgSale1 = findViewById(R.id.imgSale1);
        imgSale2 = findViewById(R.id.imgSale2);
        recyclerview_factory = findViewById(R.id.recyclerViewItems);
        toolbar_home = findViewById(R.id.toolbar_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerview_factory.setLayoutManager(linearLayoutManager);
        listview_product = findViewById(R.id.listview_product);

        arrFac = new ArrayList<>();
        factoryAdapter = new FactoryAdapter(getApplicationContext(),arrFac);
        recyclerview_factory.setAdapter(factoryAdapter);

        arrProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),arrProduct);
        listview_product.setAdapter(productAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
