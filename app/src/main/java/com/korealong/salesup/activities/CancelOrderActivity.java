package com.korealong.salesup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.korealong.salesup.R;
import com.korealong.salesup.adapter.CancelOrderAdapter;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Report;

import java.util.ArrayList;

public class CancelOrderActivity extends AppCompatActivity {

    public static ListView viewCancelOrders;
    ArrayList<Report> arrReport;
    CancelOrderAdapter cancelOrderAdapter;
    ServerHelper serverHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        viewCancelOrders = findViewById(R.id.listview_cancel_order);
        arrReport = new ArrayList<>();
        cancelOrderAdapter = new CancelOrderAdapter(getApplicationContext(),arrReport);
        viewCancelOrders.setAdapter(cancelOrderAdapter);

        serverHelper = new ServerHelper();
        serverHelper.getCancelReportFromServer(getApplicationContext(),arrReport, cancelOrderAdapter);


    }
}
