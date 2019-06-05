package com.korealong.salesup.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.korealong.salesup.R;
import com.korealong.salesup.helper.ServerHelper;

public class CreateOrder extends AppCompatActivity {
    Toolbar toolbarExhibition;
    ListView viewProducts;
    ProgressBar progress_loadmore;
    ImageButton btnSalon, btnFactory, btnSearch;
    Button btnDone;
    EditText  edtNote, edtTax, edtTotalAmount;
    AutoCompleteTextView edtSalon, edtFactory, edtProduct;

    ServerHelper serverHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exhibition);

        initView();
        initObject();
        initEvent();
    }

    private void initView() {
        toolbarExhibition = findViewById(R.id.toolbar_exhibition);
        viewProducts = findViewById(R.id.listview_products_exhibition);
        progress_loadmore = findViewById(R.id.progress_loadmore_exhibition);
        btnSalon = findViewById(R.id.btn_salon);
        btnFactory = findViewById(R.id.btn_factory);
        btnSearch = findViewById(R.id.btn_search);
        btnDone = findViewById(R.id.btn_done);
        edtSalon = findViewById(R.id.edt_salon);
        edtSalon.setHint("Nhap ten salon");
        edtFactory = findViewById(R.id.edt_factory);
        edtFactory.setHint("Nhap ten nha phan phoi");
        edtProduct = findViewById(R.id.edt_product);
        edtNote = findViewById(R.id.edt_note);
        edtTax = findViewById(R.id.edt_tax);
        edtTotalAmount = findViewById(R.id.edt_total_price);
    }

    private void initObject() {
        serverHelper = new ServerHelper();
    }
    private void initEvent() {
        serverHelper.getSalonFromServer(getApplicationContext(), edtSalon);
        serverHelper.getFactoryFromServer(getApplicationContext(),edtFactory);
    }
}
