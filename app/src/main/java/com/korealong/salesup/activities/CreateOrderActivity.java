package com.korealong.salesup.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.OrderProductAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.adapter.SaleProductAdapter;
import com.korealong.salesup.adapter.SalonAdapter;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Factory;
import com.korealong.salesup.model.OrderProduct;
import com.korealong.salesup.model.Product;
import com.korealong.salesup.model.SaleProduct;
import com.korealong.salesup.model.Salon;
import java.util.ArrayList;

public class CreateOrderActivity extends AppCompatActivity {
    Toolbar toolbarExhibition;
    public static ListView viewProducts;
    ImageButton btnSalon, btnFactory, btnSearch;
    Button btnDone;
    EditText  edtNote, edtTax;
    public static TextView txtSalon, txtFactory, txtTotalAmount;
    AutoCompleteTextView edtProduct;
    Dialog dialogSalon;

    ArrayList<Salon> arrSalon;
    SalonAdapter salonAdapter;

    ArrayList<Factory> arrFactory;
    FactoryAdapter factoryAdapter;

    ArrayList<Product> arrProduct;
    ProductAdapter productAdapter;

    ArrayList<OrderProduct> arrOderProduct;
    OrderProductAdapter orderProductAdapter;

    ArrayList<SaleProduct> arrSaleProduct;
    SaleProductAdapter saleProductAdapter;

    ServerHelper serverHelper;
    public static int totalamount = 0;
    public int factoryID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        initView();
        initObject();
        initEvent();
    }

    private void initView() {
        toolbarExhibition = findViewById(R.id.toolbar_exhibition);
        viewProducts = findViewById(R.id.listview_products_order);
        btnSalon = findViewById(R.id.btn_salon);
        btnFactory = findViewById(R.id.btn_factory);
        btnSearch = findViewById(R.id.btn_search);
        btnDone = findViewById(R.id.btn_done);
        txtSalon = findViewById(R.id.txt_salon);
        txtFactory = findViewById(R.id.txt_factory);
        edtProduct = findViewById(R.id.edt_product);
        edtNote = findViewById(R.id.edt_note);
        edtTax = findViewById(R.id.edt_tax);
        txtTotalAmount = findViewById(R.id.txt_total_amount);

        arrOderProduct = new ArrayList<>();
        orderProductAdapter = new OrderProductAdapter(getApplicationContext(),arrOderProduct);
    }

    private void initObject() {
        serverHelper = new ServerHelper();
        dialogSalon = new Dialog(this);
    }
    private void initEvent() {
        txtSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSalon.setContentView(R.layout.layout_popup);
                ImageButton btnClosePopup = dialogSalon.findViewById(R.id.btn_close_popup);
                ListView viewPopup = dialogSalon.findViewById(R.id.listview_popup);
                arrSalon = new ArrayList<>();
                salonAdapter = new SalonAdapter(getApplicationContext(),arrSalon);
                viewPopup.setAdapter(salonAdapter);
                serverHelper.getSalonFromServer(getApplicationContext(),arrSalon,salonAdapter);
                btnClosePopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSalon.dismiss();
                    }
                });
                viewPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtSalon.setText(arrSalon.get(position).salonname);
                        dialogSalon.dismiss();
                    }
                });
                dialogSalon.show();
            }
        });

        txtFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSalon.setContentView(R.layout.layout_popup);
                ImageButton btnClosePopup = dialogSalon.findViewById(R.id.btn_close_popup);
                ListView viewPopup = dialogSalon.findViewById(R.id.listview_popup);
                arrFactory = new ArrayList<>();
                factoryAdapter = new FactoryAdapter(getApplicationContext(),arrFactory);
                viewPopup.setAdapter(factoryAdapter);
                serverHelper.getFactoryFromServer(getApplicationContext(),arrFactory,factoryAdapter);
                btnClosePopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSalon.dismiss();
                    }
                });
                viewPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtFactory.setText(arrFactory.get(position).nameFactory);
                        factoryID = arrFactory.get(position).idFactory;
                        dialogSalon.dismiss();
                    }
                });
                dialogSalon.show();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSalon.setContentView(R.layout.layout_popup);
                ImageButton btnClosePopup = dialogSalon.findViewById(R.id.btn_close_popup);
                final ListView viewPopup = dialogSalon.findViewById(R.id.listview_popup);
                arrProduct = new ArrayList<>();
                productAdapter = new ProductAdapter(getApplicationContext(),arrProduct);
                viewPopup.setAdapter(productAdapter);
                serverHelper.getProductByFactoryFromServer(getApplicationContext(),arrProduct,productAdapter,1,factoryID);
                btnClosePopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSalon.dismiss();
                    }
                });

                viewPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int unitprice =0;
                        int total =0;
                        if (arrProduct.get(position).saleID == 0) {
                            arrOderProduct.add(new OrderProduct(arrProduct.get(position).productID, arrProduct.get(position).unitPrice,OrderProductAdapter.number,arrProduct.get(position).nameProduct));
                            orderProductAdapter.notifyDataSetChanged();
                            viewProducts.setAdapter(orderProductAdapter);
                            for (int i=0; i < viewProducts.getCount(); i++) {
                                unitprice = arrProduct.get(position).unitPrice;
                                total = OrderProductAdapter.number * unitprice;
                            }
                            totalamount = totalamount + total;
                            //DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                            txtTotalAmount.setText(String.valueOf(totalamount));
                        }
                        if (arrProduct.get(position).saleID != 0){
                            dialogSalon.setContentView(R.layout.layout_popup_sale_product);
                            ImageButton btnClosePopupSale = dialogSalon.findViewById(R.id.btn_close_popup_sale);
                            final TextView txtNameSaleProduct = dialogSalon.findViewById(R.id.txt_name_sale_product);
                            ListView viewPopupSaleProduct = dialogSalon.findViewById(R.id.listview_popup_sale_product);
                            arrSaleProduct = new ArrayList<>();
                            saleProductAdapter = new SaleProductAdapter(getApplicationContext(),arrSaleProduct);
                            viewPopupSaleProduct.setAdapter(saleProductAdapter);
                            btnClosePopupSale.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogSalon.dismiss();
                                }
                            });
                            final int productPrice = arrProduct.get(position).unitPrice;
                            final int productID = arrProduct.get(position).productID;
                            final String productName = arrProduct.get(position).nameProduct;
                            txtNameSaleProduct.setText(productName + " ("+ productPrice +")");
                            serverHelper.getSaleFromServer(getApplicationContext(),arrSaleProduct,saleProductAdapter);
                            viewPopupSaleProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    int unitprice =0;
                                    int total =0;
                                    arrOderProduct.add(new OrderProduct(productID, productPrice,OrderProductAdapter.number,productName+" ("+arrSaleProduct.get(position).productName+")"));
                                    orderProductAdapter.notifyDataSetChanged();
                                    String sale = arrSaleProduct.get(position).productName;
                                    //arrOderProduct.add(new OrderProduct());
                                    viewProducts.setAdapter(orderProductAdapter);
                                    for (int i=0; i < viewProducts.getCount(); i++) {
                                        total = OrderProductAdapter.number * productPrice;
                                    }
                                    totalamount = totalamount + total;
                                    txtTotalAmount.setText(String.valueOf(totalamount));
                                }
                            });
                            dialogSalon.show();
                        }
                    }
                });
                dialogSalon.show();
            }
        });
        edtTax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tax = s.toString().trim();
                if (tax.isEmpty() || tax.length() == 0 || tax.equals("") || tax == null) {
                    txtTotalAmount.setText(String.valueOf(totalamount));
                } else {
                    txtTotalAmount.setText(String.valueOf(totalamount - (totalamount * Integer.parseInt(tax) / 100)));
                }
            }
        });
    }
}
