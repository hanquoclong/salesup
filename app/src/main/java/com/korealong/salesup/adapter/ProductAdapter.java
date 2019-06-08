package com.korealong.salesup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Product> arrProduct;

    public ProductAdapter(Context context, ArrayList<Product> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }

    @Override
    public int getCount() {
        return arrProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtNameProduct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_popup,null);
            viewHolder.txtNameProduct = convertView.findViewById(R.id.txt_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product products = (Product) getItem(position);
        if (arrProduct.get(position).saleID != 0) {
            viewHolder.txtNameProduct.setTextColor(Color.RED);
        }
        viewHolder.txtNameProduct.setText(products.nameProduct);
        viewHolder.txtNameProduct.setMaxLines(3);
        viewHolder.txtNameProduct.setEllipsize(TextUtils.TruncateAt.END);
        return convertView;
    }
}
