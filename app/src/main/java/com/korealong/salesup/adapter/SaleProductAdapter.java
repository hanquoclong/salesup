package com.korealong.salesup.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.model.SaleProduct;
import com.korealong.salesup.model.Salon;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SaleProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SaleProduct> arrSale;

    public SaleProductAdapter(Context context, ArrayList<SaleProduct> arrSale) {
        this.context = context;
        this.arrSale = arrSale;
    }

    public class ViewHolder {
        public TextView txtNameType, txtSubProduct, txtSubPrice;
    }
    @Override
    public int getCount() {
        return arrSale.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSale.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_sale_product,null);
            viewHolder.txtNameType = convertView.findViewById(R.id.txt_name_type);
            viewHolder.txtSubPrice = convertView.findViewById(R.id.txt_sub_price);
            viewHolder.txtSubProduct = convertView.findViewById(R.id.txt_sub_product);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SaleProduct saleProduct = (SaleProduct) getItem(position);
        viewHolder.txtNameType.setText(saleProduct.typeName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtSubPrice.setText(decimalFormat.format(saleProduct.saleProductPrice));
        viewHolder.txtSubProduct.setText(saleProduct.productName);
        return convertView;
    }
}
