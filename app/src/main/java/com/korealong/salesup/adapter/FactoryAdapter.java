package com.korealong.salesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.korealong.salesup.R;
import com.korealong.salesup.model.Factorys;

import java.util.ArrayList;

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder>{


    private static final String TAG = "FactoryAdapter";

    private ArrayList<Factorys> factorysArrayList;
    private Context context;

    public FactoryAdapter(ArrayList<Factorys> factorysArrayList, Context context) {
        this.factorysArrayList = factorysArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem_factory,null);
        
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Factorys factorys = factorysArrayList.get(i);
        viewHolder.btnFac.setText(factorys.getNameFactory());

    }

    @Override
    public int getItemCount() {
        return factorysArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public Button btnFac;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnFac = itemView.findViewById(R.id.btnFac);
        }
    }
}
