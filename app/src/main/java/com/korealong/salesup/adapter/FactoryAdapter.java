package com.korealong.salesup.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.korealong.salesup.R;
import com.korealong.salesup.model.Factory;

import java.util.ArrayList;


public class FactoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Factory> arrFactory;

    public FactoryAdapter(Context context, ArrayList<Factory> arrFac) {
        this.context = context;
        this.arrFactory = arrFac;
    }

    public class ViewHolder {
        public TextView txtNameFactory;
    }

    @Override
    public int getCount() {
        return arrFactory.size();
    }

    @Override
    public Object getItem(int position) {
        return arrFactory.get(position);
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
            viewHolder.txtNameFactory = convertView.findViewById(R.id.txt_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Factory factory = (Factory) getItem(position);
        viewHolder.txtNameFactory.setText(factory.nameFactory);
        viewHolder.txtNameFactory.setEllipsize(TextUtils.TruncateAt.END);
        return convertView;
    }
}
