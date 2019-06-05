package com.korealong.salesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import com.korealong.salesup.R;
import com.korealong.salesup.activities.HomeActivity;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Factory;
import com.korealong.salesup.model.Product;

import java.util.ArrayList;


public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ItemHolder> {

    private ServerHelper serverHelper = new ServerHelper();

    private Context context;
    private ArrayList<Factory> arrFactory;

    public FactoryAdapter(Context context, ArrayList<Factory> arrFac) {
        this.context = context;
        this.arrFactory = arrFac;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem_factory,null);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        Factory factorys = arrFactory.get(i);
        final ArrayList<Product> arrProduct = new ArrayList<>();
        itemHolder.txtfac.setText(factorys.nameFactory);
        itemHolder.txtfac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrProduct.clear();
                int factoryID = arrFactory.get(i).idFactory;
                //TODO: Ask aTrinh load more product
                serverHelper.getProductByFactoryFromServer(context,arrProduct,1,factoryID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrFactory.size();
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
