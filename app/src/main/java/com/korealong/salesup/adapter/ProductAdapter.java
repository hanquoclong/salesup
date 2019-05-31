package com.korealong.salesup.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.helper.ItemClickListener;
import com.korealong.salesup.model.Products;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter{



    Context context;
    ArrayList<Products> arrProduct;
    String imgString;


    public ProductAdapter(Context context, ArrayList<Products> arrProduct) {
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
        Products products = (Products) getItem(position);
        viewHolder.txtNameProduct.setText(products.getNameProduct());
        viewHolder.txtNameProduct.setMaxLines(2);
        viewHolder.txtNameProduct.setEllipsize(TextUtils.TruncateAt.END);
        imgString = arrProduct.get(position).getImg();
        byte[] decoded = Base64.decode(imgString,Base64.DEFAULT);
        viewHolder.imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(decoded,0,decoded.length));

        return convertView;
    }
}
