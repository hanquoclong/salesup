package com.korealong.salesup.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.korealong.salesup.R;
import com.korealong.salesup.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Product> arrProduct;
    private String imgString;

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

    public class ViewHolder{
        public TextView txtNameProduct;
        public ImageView imgProduct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_listitem_product,null);
            viewHolder.imgProduct = convertView.findViewById(R.id.imgProduct);
            viewHolder.txtNameProduct = convertView.findViewById(R.id.txtNameProduct);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product products = (Product) getItem(position);
        viewHolder.txtNameProduct.setText(products.nameProduct);
        viewHolder.txtNameProduct.setMaxLines(1);
        viewHolder.txtNameProduct.setEllipsize(TextUtils.TruncateAt.END);
        Picasso.with(context).load(products.img)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(viewHolder.imgProduct);
        return convertView;
    }
}
