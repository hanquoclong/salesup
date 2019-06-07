package com.korealong.salesup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.model.Report;

import java.util.ArrayList;

public class ReportAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Report> arrReport;

    public ReportAdapter(Context context, ArrayList<Report> arrReport) {
        this.context = context;
        this.arrReport = arrReport;
    }

    public class ViewHolder {
        public TextView txtProductName, txtDate, txtStatus;
    }

    @Override
    public int getCount() {
        return arrReport.size();
    }

    @Override
    public Object getItem(int position) {
        return arrReport.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_report,null);
            viewHolder.txtProductName = convertView.findViewById(R.id.txt_report_name_product);
            viewHolder.txtDate = convertView.findViewById(R.id.txt_report_date);
            viewHolder.txtStatus = convertView.findViewById(R.id.txt_report_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Report report = (Report) getItem(position);
        viewHolder.txtProductName.setText(report.productName);
        viewHolder.txtDate.setText(report.date);
        viewHolder.txtStatus.setText(report.status);
        return convertView;
    }
}
