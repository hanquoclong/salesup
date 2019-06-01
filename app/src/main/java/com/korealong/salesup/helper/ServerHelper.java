package com.korealong.salesup.helper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.korealong.salesup.MainActivity;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.adapter.SaleProductAdapter;
import com.korealong.salesup.model.Factorys;
import com.korealong.salesup.model.Products;
import com.korealong.salesup.model.SaleProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static android.content.ContentValues.TAG;
import static com.korealong.salesup.utils.Constants.URL_GET_ALL_PRODUCT;
import static com.korealong.salesup.utils.Constants.URL_GET_FACTORY;
import static com.korealong.salesup.utils.Constants.URL_GET_PRODUCT;
import static com.korealong.salesup.utils.Constants.URL_GET_SALE_PRODUCT;

public class ServerHelper extends MainActivity {

    //public RequestQueue requestQueue;


    public void getFactoryFromServer(final ArrayList<Factorys> aFac, final FactoryAdapter adapter, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                            aFac.add(new Factorys(ID,nameFac));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void getProductFromServer(int mpage,Context context, final int IDFac)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String link = URL_GET_PRODUCT+String.valueOf(mpage);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null)
                {
                    int idproduct = 0;
                    int idfac = 0;
                    String nameproduct ="";
                    String description = "";
                    int unitprice = 0;
                    int instock = 0;
                    String img;
                    MainActivity mainActivity = new MainActivity();
                    ArrayList<Products> arrProduct = new ArrayList<>();
                    ProductAdapter productAdapter = new ProductAdapter(mainActivity,arrProduct);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idproduct = jsonObject.getInt("idproduct");
                            idfac = jsonObject.getInt("idfac");
                            nameproduct = jsonObject.getString("nameproduct");
                            description = jsonObject.getString("description");
                            unitprice = jsonObject.getInt("unitprice");
                            instock = jsonObject.getInt("instock");
                            img = jsonObject.getString("img");
                            arrProduct.add(new Products(idproduct,idfac,nameproduct,description,unitprice,instock,img));
                            productAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idfac", String.valueOf(IDFac));
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getAllProductFromServer(final Context context, int page , final ArrayList<Products> arrProduct, final ProductAdapter productAdapter, final ListView lvProduct, final View ftProgress){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String link = URL_GET_ALL_PRODUCT+String.valueOf(page);
        final JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null && response.length() > 2) {
                    lvProduct.removeFooterView(ftProgress);
                    int idproduct = 0;
                    int idfac = 0;
                    String nameproduct = "";
                    String description = "";
                    int unitprice = 0;
                    int instock = 0;
                    String img;
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idproduct = jsonObject.getInt("idproduct");
                            idfac = jsonObject.getInt("idfac");
                            nameproduct = jsonObject.getString("nameproduct");
                            description = jsonObject.getString("description");
                            unitprice = jsonObject.getInt("unitprice");
                            instock = jsonObject.getInt("instock");
                            img = jsonObject.getString("img");
                            arrProduct.add(new Products(idproduct, idfac, nameproduct, description, unitprice, instock, img));
                            productAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitdata = true;
                    lvProduct.removeFooterView(ftProgress);
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

    public void getSaleProductFromServer(Context context, final SaleProductAdapter saleProductAdapter, final ArrayList<SaleProduct> arrSaleP){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_GET_SALE_PRODUCT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null)
                {
                    int idsale = 0;
                    int idtype = 0;
                    int idproduct = 0;
                    int idfac = 0;
                    String nametype = "";
                    String nameproduct = "";
                    String description = "";
                    int unitprice = 0;
                    int instock = 0;
                    String img;
                    for (int i = 0 ; i< response.length(); i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idsale = jsonObject.getInt("idsale");
                            idtype = jsonObject.getInt("idtype");
                            idproduct= jsonObject.getInt("idproduct");
                            idfac = jsonObject.getInt("idfac");
                            nametype = jsonObject.getString("nametype");
                            nameproduct = jsonObject.getString("nameproduct");
                            description = jsonObject.getString("description");
                            unitprice = jsonObject.getInt("unitprice");
                            instock = jsonObject.getInt("instock");
                            img = jsonObject.getString("img");
                            arrSaleP.add(new SaleProduct(idsale,idtype,idproduct,idfac,nametype,nameproduct,description,unitprice,instock,img));
                            saleProductAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
