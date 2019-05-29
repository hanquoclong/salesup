package com.korealong.salesup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.korealong.salesup.utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {
    private TextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHello = findViewById(R.id.txtHello);

        Intent intent = getIntent();
        if (intent.hasExtra("EMAIL"))
        {
            String nameFromIntent = getIntent().getStringExtra("EMAIL");
            txtHello.setText("Welcome "+ nameFromIntent);
        }
        else {
            String email = PreferenceUtils.getEmail(this);
            txtHello.setText("Welcome "+ email);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
