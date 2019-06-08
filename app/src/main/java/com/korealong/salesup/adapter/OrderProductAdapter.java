package com.korealong.salesup.adapter;

import android.content.Context;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderProductAdapter extends BaseAdapter {

    public static int number=1;
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(decimalFormat.format(orderProduct.productPrice));
        viewHolder.edtNumber.setText("1");
        final int oldNumber = Integer.parseInt(viewHolder.edtNumber.getText().toString());

        number = Integer.parseInt(viewHolder.edtNumber.getText().toString());
        final int totalamount = CreateOrderActivity.totalamount;
        final int unitprice = orderProduct.productPrice * number;
        Log.d("getView: ",""+unitprice);
        final int amount = totalamount - unitprice;
        Log.d( "getView: ", ""+totalamount + " ---"+unitprice +"----"+amount);
        viewHolder.edtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() == ""){

                }
                number = Integer.parseInt(s.toString());
                int lastPrice = (unitprice * number / oldNumber) + amount;
                int oldprice = ((number-oldNumber)*unitprice)+unitprice;
                CreateOrderActivity.txtTotalAmount.setText(String.valueOf(lastPrice+(lastPrice- oldprice)));
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
