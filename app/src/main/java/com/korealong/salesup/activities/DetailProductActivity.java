package com.korealong.salesup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailProductActivity extends AppCompatActivity {

    Toolbar toolbarDetail;
    ImageButton btnPre, btnNext, btnUp, btnDown;
    Button btnAddCart;
    ImageView imgDetailProduct;
    TextView txtTotal, txtNameProduct, txtPrice, txtDescription;
    EditText edtNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        toolbarDetail = findViewById(R.id.toolbar_detail);
        btnPre = findViewById(R.id.btn_pre);
        btnNext = findViewById(R.id.btn_next);
        btnUp = findViewById(R.id.btn_up);
        btnDown = findViewById(R.id.btn_down);
        btnAddCart = findViewById(R.id.btn_add_cart);
        imgDetailProduct = findViewById(R.id.img_detail);
        txtTotal = findViewById(R.id.txt_total);
        txtNameProduct = findViewById(R.id.txt_name_product);
        txtPrice= findViewById(R.id.txt_price);
        txtDescription = findViewById(R.id.txt_description);
        edtNumber = findViewById(R.id.edt_number);
    }

    private void initData() {
        int productID = 0;
        int factoryID = 0;
        int instock =0;
        String nameProduct = "";
        int productPrice = 0;
        String img = "";
        String description = "";
        Product product = (Product) getIntent().getSerializableExtra("productID");

        productID = product.productID;
        factoryID = product.factoryID;
        instock = product.inStock;
        nameProduct = product.nameProduct;
        productPrice = product.unitPrice;
        img = product.img;
        description = product.description;
        txtNameProduct.setText(nameProduct);
        txtDescription.setText(description);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText(decimalFormat.format(productPrice));
        Picasso.with(this).load(img)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(imgDetailProduct);
    }
    private void initEvent() {
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtNumber.getText().toString();
                int mnumber = Integer.parseInt(s);
                if (mnumber > 0) {
                    mnumber--;
                    s = String.valueOf(mnumber);
                    edtNumber.setText(s);
                }
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtNumber.getText().toString();
                int mnumber = Integer.parseInt(s);
                mnumber++;
                s = String.valueOf(mnumber);
                edtNumber.setText(s);
            }
        });
    }
}
