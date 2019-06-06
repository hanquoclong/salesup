package com.korealong.salesup.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.model.Salon;

import java.util.ArrayList;

public class SalonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Salon> arrSalon;

    public SalonAdapter(Context context, ArrayList<Salon> arrSalon) {
        this.context = context;
        this.arrSalon = arrSalon;
    }

    public class ViewHolder {
        public TextView txtNameSalon;
    }

    @Override
    public int getCount() {
        return arrSalon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSalon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_popup,null);
            viewHolder.txtNameSalon = convertView.findViewById(R.id.txt_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Salon salon = (Salon) getItem(position);
        viewHolder.txtNameSalon.setText(salon.salonname);
        viewHolder.txtNameSalon.setEllipsize(TextUtils.TruncateAt.END);
        return convertView;
    }


}
