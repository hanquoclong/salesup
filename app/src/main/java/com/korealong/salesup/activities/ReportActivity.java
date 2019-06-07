package com.korealong.salesup.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.korealong.salesup.MainActivity;
import com.korealong.salesup.R;
import com.korealong.salesup.adapter.ReportAdapter;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    TextView txtDateFrom, txtDateTo;
    ListView viewReports;
    DatePickerDialog.OnDateSetListener fromDateListener,toDateListener;
    Calendar calendar;
    ServerHelper serverHelper;

    ArrayList<Report> arrReport;
    ReportAdapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initView();
        initObject();
        initEvent();
    }

    private void initView() {
        txtDateFrom = findViewById(R.id.txt_date_from);
        txtDateTo = findViewById(R.id.txt_date_to);
        viewReports = findViewById(R.id.listview_report);

        arrReport = new ArrayList<>();
        reportAdapter = new ReportAdapter(getApplicationContext(),arrReport);
        viewReports.setAdapter(reportAdapter);
    }

    private void initObject() {
        calendar = Calendar.getInstance();
        serverHelper = new ServerHelper();
    }

    private void initEvent() {
        serverHelper.getReportFromServer(getApplicationContext(),arrReport,reportAdapter);

        txtDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReportActivity.this,android.R.style.Theme_DeviceDefault_Dialog,fromDateListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txtDateFrom.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        txtDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReportActivity.this,android.R.style.Theme_DeviceDefault_Dialog,toDateListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        toDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txtDateTo.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
    }
}
