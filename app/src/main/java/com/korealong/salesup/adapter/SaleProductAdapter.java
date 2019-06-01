package com.korealong.salesup.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.korealong.salesup.R;
import com.korealong.salesup.model.SaleProduct;

import java.util.ArrayList;

public class SaleProductAdapter extends RecyclerView.Adapter<SaleProductAdapter.ItemHolder> {


    Context context;
    ArrayList<SaleProduct> arrSale;

    public SaleProductAdapter(Context context, ArrayList<SaleProduct> arrSale) {
        this.context = context;
        this.arrSale = arrSale;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem_saleproduct,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SaleProduct saleProduct = arrSale.get(i);
        String imgString = saleProduct.getImg();
        byte[] decoded = Base64.decode(imgString,Base64.DEFAULT);
        itemHolder.imgSale.setImageBitmap(BitmapFactory.decodeByteArray(decoded,0,decoded.length));

    }

    @Override
    public int getItemCount() {
        return arrSale.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgSale;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgSale = itemView.findViewById(R.id.imgSale);
        }
    }
}
