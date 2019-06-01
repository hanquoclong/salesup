package com.korealong.salesup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.korealong.salesup.R;

public class DetailProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        getSupportActionBar().hide();
    }
}
