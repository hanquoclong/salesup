package com.korealong.salesup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.korealong.salesup.model.Factorys;
import com.korealong.salesup.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.korealong.salesup.utils.Constants.URL_GET_FACTORY;

public class MainActivity extends AppCompatActivity {

    public RequestQueue requestQueue;

    ImageView imgSale1,imgSale2;
    Toolbar toolbar_home;
    RecyclerView recyclerview_factory,recyclerView_product;
    ArrayList<Factorys> arrFac;
    FactoryAdapter factoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        requestQueue = Volley.newRequestQueue(this);

        initView();
        getFactoryFromServer();

    }

    private void getFactoryFromServer() {
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(URL_GET_FACTORY, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null)
                {
                    int ID = 0;
                    String nameFac = "";
                    for (int i = 0; i < response.length(); i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("idfac");
                            nameFac = jsonObject.getString("namefac");
                            arrFac.add(new Factorys(ID,nameFac));
                            factoryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(MainActivity.this, ""+arrFac.size(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void initView() {
        imgSale1 = findViewById(R.id.imgSale1);
        imgSale2 = findViewById(R.id.imgSale2);
        toolbar_home = findViewById(R.id.toolbar_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerview_factory = findViewById(R.id.recyclerView_factory);
        recyclerview_factory.setLayoutManager(linearLayoutManager);
        recyclerView_product = findViewById(R.id.recyclerView_product);
        arrFac = new ArrayList<>();
        factoryAdapter = new FactoryAdapter(arrFac,getApplicationContext());
        recyclerview_factory.setAdapter(factoryAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
