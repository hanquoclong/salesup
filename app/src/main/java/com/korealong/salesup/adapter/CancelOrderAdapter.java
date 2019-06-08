package com.korealong.salesup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Report;

import java.util.ArrayList;

public class CancelOrderAdapter extends BaseAdapter {

    private ServerHelper serverHelper = new ServerHelper();
    private Context context;
    private ArrayList<Report> arrReport;

    public CancelOrderAdapter(Context context, ArrayList<Report> arrReport) {
        this.context = context;
        this.arrReport = arrReport;
    }

    public class ViewHolder {
        public TextView txtProductName, txtDate, txtStatus;
        public Button btnReturns;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_cancel_report,null);
            viewHolder.txtProductName = convertView.findViewById(R.id.txt_cancel_report_name_product);
            viewHolder.txtDate = convertView.findViewById(R.id.txt_cancel_report_date);
            viewHolder.txtStatus = convertView.findViewById(R.id.txt_cancel_report_status);
            viewHolder.btnReturns = convertView.findViewById(R.id.btn_returns);
            viewHolder.btnReturns.setTag(position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Report report = (Report) getItem(position);
        viewHolder.txtProductName.setText(report.productName);
        viewHolder.txtDate.setText(report.date);
        viewHolder.txtStatus.setText(report.status);
        viewHolder.btnReturns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverHelper.deleteOrderFromServer(context,arrReport.get(position).reportID);
                int pos = (int) v.getTag();
                arrReport.remove(pos);
                CancelOrderAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
