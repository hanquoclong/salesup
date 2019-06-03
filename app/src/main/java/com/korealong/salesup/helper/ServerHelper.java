package com.korealong.salesup.helper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.korealong.salesup.activities.HomeActivity;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.adapter.SaleProductAdapter;
import com.korealong.salesup.model.Factory;
import com.korealong.salesup.model.Product;
import com.korealong.salesup.model.SaleProduct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.korealong.salesup.utils.Constants.URL_GET_ALL_PRODUCT;
import static com.korealong.salesup.utils.Constants.URL_GET_FACTORY;
import static com.korealong.salesup.utils.Constants.URL_GET_PRODUCT;
import static com.korealong.salesup.utils.Constants.URL_GET_SALE_PRODUCT;

public class ServerHelper {

    public void getFactoryFromServer(Context context, final ArrayList<Factory> arrFactory, final FactoryAdapter adapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(URL_GET_FACTORY, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String nameFac = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("idfac");
                            nameFac = jsonObject.getString("namefac");
                            arrFactory.add(new Factory(ID,nameFac));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getProductByFactoryFromServer(final Context context, final ArrayList<Product> arrProduct, int mpage, final int factoryID) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String link = URL_GET_PRODUCT+String.valueOf(mpage)+"&idfac="+factoryID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    int productID = 0;
                    int factoryID = 0;
                    String nameproduct ="";
                    String description = "";
                    int unitprice = 0;
                    int instock = 0;
                    String img;
                    ProductAdapter productAdapter = new ProductAdapter(context,arrProduct);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            productID = jsonObject.getInt("idproduct");
                            factoryID = jsonObject.getInt("idfac");
                            nameproduct = jsonObject.getString("nameproduct");
                            description = jsonObject.getString("description");
                            unitprice = jsonObject.getInt("unitprice");
                            instock = jsonObject.getInt("instock");
                            img = jsonObject.getString("img");
                            arrProduct.add(new Product(productID,factoryID,nameproduct,description,unitprice,instock,img));
                            productAdapter.notifyDataSetChanged();
                        }
                        HomeActivity.viewProducts.setAdapter(productAdapter);
                        Log.d("click item fac" , ""+arrProduct.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void getAllProductFromServer(final Context context, int page , final ArrayList<Product> arrProduct, final ProductAdapter productAdapter, final ProgressBar progressBar) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String link = URL_GET_ALL_PRODUCT+String.valueOf(page);
        final JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null && response.length() > 2) {
                    progressBar.setVisibility(View.GONE);
                    int productID = 0;
                    int factoryID = 0;
                    String nameproduct = "";
                    String description = "";
                    int unitprice = 0;
                    int instock = 0;
                    String img;
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            productID = jsonObject.getInt("idproduct");
                            factoryID = jsonObject.getInt("idfac");
                            nameproduct = jsonObject.getString("nameproduct");
                            description = jsonObject.getString("description");
                            unitprice = jsonObject.getInt("unitprice");
                            instock = jsonObject.getInt("instock");
                            img = jsonObject.getString("img");
                            arrProduct.add(new Product(productID, factoryID, nameproduct, description, unitprice, instock, img));
                        }
                        productAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    HomeActivity.noMoreProduct = true;
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Đã hết dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getSaleProductFromServer(Context context,final ArrayList<SaleProduct> arrSaleP, final SaleProductAdapter saleProductAdapter){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_GET_SALE_PRODUCT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int saleID = 0;
                    int typeID = 0;
                    int productID = 0;
                    int factoryID = 0;
                    String nametype = "";
                    String nameproduct = "";
                    String description = "";
                    int unitprice = 0;
                    int instock = 0;
                    String img;
                    for (int i = 0 ; i< response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            saleID = jsonObject.getInt("idsale");
                            typeID = jsonObject.getInt("idtype");
                            productID= jsonObject.getInt("idproduct");
                            factoryID = jsonObject.getInt("idfac");
                            nametype = jsonObject.getString("nametype");
                            nameproduct = jsonObject.getString("nameproduct");
                            description = jsonObject.getString("description");
                            unitprice = jsonObject.getInt("unitprice");
                            instock = jsonObject.getInt("instock");
                            img = jsonObject.getString("img");
                            arrSaleP.add(new SaleProduct(saleID,typeID,productID,factoryID,nametype,nameproduct,description,unitprice,instock,img));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        saleProductAdapter.notifyDataSetChanged();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
