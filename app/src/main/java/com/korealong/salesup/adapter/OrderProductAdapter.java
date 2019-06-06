package com.korealong.salesup.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.model.OrderProduct;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<OrderProduct> arrOrderProduct;

    public OrderProductAdapter(Context context, ArrayList<OrderProduct> arrOrderProduct) {
        this.context = context;
        this.arrOrderProduct = arrOrderProduct;
    }

    public class ViewHolder {
        public TextView txtNo,txtNameOrderProduct, txtPrice;
        public EditText edtNumber;
        public ImageButton btnDeleteOrderProduct;
    }
    @Override
    public int getCount() {
        return arrOrderProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrOrderProduct.get(position);
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
            convertView = inflater.inflate(R.layout.layout_item_order,null);
            viewHolder.txtNo = convertView.findViewById(R.id.txt_no);
            viewHolder.txtNameOrderProduct = convertView.findViewById(R.id.txt_name_order_product);
            viewHolder.txtPrice = convertView.findViewById(R.id.txt_order_product_price);
            viewHolder.edtNumber = convertView.findViewById(R.id.edt_number_order_product);
            viewHolder.btnDeleteOrderProduct = convertView.findViewById(R.id.btn_delete_order_product);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OrderProduct orderProduct = (OrderProduct) getItem(position);
        viewHolder.txtNo.setText(String.valueOf(++position));
        viewHolder.txtNameOrderProduct.setText(orderProduct.productName);
        viewHolder.txtNameOrderProduct.setEllipsize(TextUtils.TruncateAt.END);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(decimalFormat.format(orderProduct.productPrice));
        viewHolder.edtNumber.setText("1");
        return convertView;
    }
}
