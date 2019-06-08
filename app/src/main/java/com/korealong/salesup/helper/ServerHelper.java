package com.korealong.salesup.helper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.korealong.salesup.activities.HomeActivity;
import com.korealong.salesup.adapter.CancelOrderAdapter;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.adapter.ReportAdapter;
import com.korealong.salesup.adapter.SaleProductAdapter;
import com.korealong.salesup.adapter.SalonAdapter;
import com.korealong.salesup.model.Factory;
import com.korealong.salesup.model.Product;
import com.korealong.salesup.model.Report;
import com.korealong.salesup.model.SaleProduct;
import com.korealong.salesup.model.Salon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.korealong.salesup.utils.Constants.URL_DELETE_REPORT;
import static com.korealong.salesup.utils.Constants.URL_GET_ALL_PRODUCT;
import static com.korealong.salesup.utils.Constants.URL_GET_CANCEL_REPORT;
import static com.korealong.salesup.utils.Constants.URL_GET_FACTORY;
import static com.korealong.salesup.utils.Constants.URL_GET_PRODUCT;
import static com.korealong.salesup.utils.Constants.URL_GET_REPORT;
import static com.korealong.salesup.utils.Constants.URL_GET_SALE;
import static com.korealong.salesup.utils.Constants.URL_GET_SALON;
import static com.korealong.salesup.utils.Constants.URL_LOCATION;

public class ServerHelper {

    public void getFactoryFromServer(final Context context, final ArrayList<Factory> arrFactory, final FactoryAdapter factoryAdapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(URL_GET_FACTORY, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String nameFactory = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("idfac");
                            nameFactory = jsonObject.getString("namefac");
                            arrFactory.add(new Factory(ID,nameFactory));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    factoryAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getProductByFactoryFromServer(final Context context, final ArrayList<Product> arrProduct, final ProductAdapter productAdapter, int mpage, final int factoryID) {
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
                    int idsale = 0;
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
                            idsale = jsonObject.getInt("idsale");
                            arrProduct.add(new Product(productID,factoryID,nameproduct,description,unitprice,instock,idsale));
                        }
                        productAdapter.notifyDataSetChanged();
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

                    int productID = 0;
                    int factoryID = 0;
                    String nameproduct = "";
                    String description = "";
                    int unitprice = 0;
                    int instock = 0;
                    int idsale =0;
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            productID = jsonObject.getInt("idproduct");
                            factoryID = jsonObject.getInt("idfac");
                            nameproduct = jsonObject.getString("nameproduct");
                            description = jsonObject.getString("description");
                            unitprice = jsonObject.getInt("unitprice");
                            instock = jsonObject.getInt("instock");
                            idsale = jsonObject.getInt("idsale");
                            arrProduct.add(new Product(productID, factoryID, nameproduct, description, unitprice, instock, idsale));
                        }
                        productAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
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

    public void getLocationFromServer(final Context context, final GoogleMap googleMap) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LOCATION, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    Double iLatitude = 0.0;
                    Double iLongitude = 0.0;
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    for (int i = 0 ; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            iLatitude = jsonObject.getDouble("latitude");
                            iLongitude = jsonObject.getDouble("longitude");
                            List<Address> listAddress = geocoder.getFromLocation(iLatitude,iLongitude,1);
                            String address = listAddress.get(0).getAddressLine(0);
                            googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(iLatitude,iLongitude))
                                .title(String.valueOf(id))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                .snippet(address));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
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
        int socketTimeout = 10000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonArrayRequest);
    }

    public void getSalonFromServer(final Context context, final ArrayList<Salon> arrSalon, final SalonAdapter salonAdapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(URL_GET_SALON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null && response.length() > 2) {
                    int salonID = 0;
                    String salonname = "";
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            salonID = jsonObject.getInt("salonid");
                            salonname = jsonObject.getString("salonname");
                            arrSalon.add(new Salon(salonID,salonname));
                        }
                        salonAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public void getSaleFromServer(Context context, final ArrayList<SaleProduct> arrSaleProduct, final SaleProductAdapter saleProductAdapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_GET_SALE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int saleID = 0;
                    int saleType = 0;
                    int productID = 0;
                    String productName = "";
                    int saleNumber= 0;
                    String orderDate;
                    int saleProductPrice = 0;
                    String typeName = "";
                    for (int i = 0; i <response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            saleID = jsonObject.getInt("saleid");
                            saleType = jsonObject.getInt("saletype");
                            productID = jsonObject.getInt("productid");
                            productName = jsonObject.getString("nameproduct");
                            saleNumber = jsonObject.getInt("numbersale");
                            orderDate = jsonObject.getString("orderdate");
                            saleProductPrice = jsonObject.getInt("saleproductprice");
                            typeName = jsonObject.getString("typename");
                            arrSaleProduct.add(new SaleProduct(saleID,saleType,productID,productName,saleNumber,orderDate,saleProductPrice,typeName));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    saleProductAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getReportFromServer(Context context, final ArrayList<Report> arrReport, final ReportAdapter reportAdapter,String dateFrom, String dateTo) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        String link = URL_GET_REPORT+dateFrom+"&dateto="+dateTo;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int reportID =0;
                    String productName ="";
                    String date = "";
                    String status = "";
                    for (int i = 0; i < response.length() ; i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            reportID = jsonObject.getInt("idreport");
                            productName = jsonObject.getString("nameproduct");
                            date = jsonObject.getString("date");
                            status = jsonObject.getString("status");
                            arrReport.add(new Report(productName,date,status,reportID));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    reportAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getCancelReportFromServer(Context context, final ArrayList<Report> arrReport, final CancelOrderAdapter cancelOrderAdapter) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_GET_CANCEL_REPORT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int reportID = 0;
                    String productName ="";
                    String date = "";
                    String status = "";
                    for (int i = 0; i < response.length() ; i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            reportID = jsonObject.getInt("idreport");
                            productName = jsonObject.getString("nameproduct");
                            date = jsonObject.getString("date");
                            status = jsonObject.getString("status");
                            arrReport.add(new Report(productName,date,status,reportID));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    cancelOrderAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void deleteOrderFromServer(Context context, final int reportID) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        String link = URL_DELETE_REPORT+reportID;
        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
