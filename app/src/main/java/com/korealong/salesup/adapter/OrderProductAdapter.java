package com.korealong.salesup.adapter;

import android.content.Context;
import android.support.v4.math.MathUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.korealong.salesup.R;
import com.korealong.salesup.activities.CreateOrderActivity;
import com.korealong.salesup.model.OrderProduct;

import java.util.ArrayList;

public class OrderProductAdapter extends BaseAdapter {

    public static int number=1;
    int oldprice = 0;
    private int totalNew = 0;
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

        final ViewHolder viewHolder;
        if (convertView == null){
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
        final OrderProduct orderProduct = (OrderProduct) getItem(position);
        viewHolder.txtNo.setText(String.valueOf(++position));
        viewHolder.txtNameOrderProduct.setText(orderProduct.productName);
        viewHolder.txtNameOrderProduct.setEllipsize(TextUtils.TruncateAt.END);
        //DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(String.valueOf(orderProduct.productPrice));
        viewHolder.edtNumber.setText("1");

        viewHolder.edtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                number = Integer.parseInt(s.toString());
                oldprice = orderProduct.productPrice;
                viewHolder.txtPrice.setText(String.valueOf(number * orderProduct.productPrice));
                int totalOld = CreateOrderActivity.totalamount;
                totalNew = totalOld + Integer.parseInt(viewHolder.txtPrice.getText().toString()) - oldprice;
                Log.d( "olddata: ","oldprice: "+oldprice);
                Log.d("afterTextChanged: ", "number new: "+s.toString()+" total new: "+totalNew);
                CreateOrderActivity.txtTotalAmount.setText(String.valueOf(totalNew));

            }
        });

        final int finalPosition = position;
        viewHolder.btnDeleteOrderProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrOrderProduct.remove(finalPosition-1);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}