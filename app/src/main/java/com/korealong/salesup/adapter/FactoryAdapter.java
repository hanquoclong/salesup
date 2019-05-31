package com.korealong.salesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.korealong.salesup.MainActivity;
import com.korealong.salesup.R;
import com.korealong.salesup.helper.ItemClickListener;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Factorys;
import com.korealong.salesup.model.Products;

import java.util.ArrayList;


public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ItemHolder> {

    Context context;
    ArrayList<Factorys> arrFac;

    public FactoryAdapter(Context context, ArrayList<Factorys> arrFac) {
        this.context = context;
        this.arrFac = arrFac;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem_factory,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        Factorys factorys = arrFac.get(i);
        itemHolder.txtfac.setText(factorys.getNameFactory());

        itemHolder.txtfac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+arrFac.get(i).nameFactory, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrFac.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        public TextView txtfac;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            txtfac = itemView.findViewById(R.id.txtFac);
        }
    }
}
