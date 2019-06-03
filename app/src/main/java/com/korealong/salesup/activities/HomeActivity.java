package com.korealong.salesup.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.korealong.salesup.R;
import com.korealong.salesup.adapter.FactoryAdapter;
import com.korealong.salesup.adapter.ProductAdapter;
import com.korealong.salesup.adapter.SaleProductAdapter;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.model.Factory;
import com.korealong.salesup.model.Product;
import com.korealong.salesup.model.SaleProduct;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbarHome;
    public static ListView viewProducts;
    RecyclerView viewFactories,viewSaleProducts;
    ProgressBar progress_loadmore;

    ArrayList<Factory> arrFactory;
    FactoryAdapter factoryAdapter;

    ArrayList<SaleProduct> arrSaleProduct;
    SaleProductAdapter saleProductAdapter;

    ArrayList<Product> arrProduct;
    ProductAdapter productAdapter;

    ServerHelper serverHelper;
    mHandler mHandler;
    int pagination= 1;
    boolean isLoading = false;
    public static boolean noMoreProduct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initObject();
        initData();
        initEvent();
    }

    private void initView() {
        toolbarHome = findViewById(R.id.toolbar_home);
        viewProducts = findViewById(R.id.listview_products);
        viewFactories = findViewById(R.id.recyclerview_factories);
        viewSaleProducts = findViewById(R.id.recyclerview_saleproducts);
        progress_loadmore = findViewById(R.id.progress_loadmore);

        LinearLayoutManager llmFac = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        viewFactories.setLayoutManager(llmFac);
        arrFactory = new ArrayList<>();
        factoryAdapter = new FactoryAdapter(this,arrFactory);
        viewFactories.setAdapter(factoryAdapter);

        LinearLayoutManager llmSale = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        viewSaleProducts.setLayoutManager(llmSale);
        arrSaleProduct = new ArrayList<>();
        saleProductAdapter = new SaleProductAdapter(this,arrSaleProduct);
        viewSaleProducts.setAdapter(saleProductAdapter);


        arrProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(this,arrProduct);
        viewProducts.setAdapter(productAdapter);
    }
    private void initObject() {
        serverHelper = new ServerHelper();
        mHandler = new mHandler();
    }
    private void initData() {
        serverHelper.getFactoryFromServer(this,arrFactory,factoryAdapter);
        serverHelper.getSaleProductFromServer(this,arrSaleProduct,saleProductAdapter);
        serverHelper.getAllProductFromServer(getApplicationContext(),pagination,arrProduct,productAdapter,viewProducts,progress_loadmore);
    }
    private void initEvent() {
        viewProducts.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("not scroll: ",""+firstVisibleItem+"----"+visibleItemCount+"----"+totalItemCount);
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0 && !isLoading && !noMoreProduct)
                {
                    Log.d("on scroll: ",""+firstVisibleItem+"----"+visibleItemCount+"----"+totalItemCount);
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.run();
                }
            }
        });
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    progress_loadmore.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    serverHelper.getAllProductFromServer(getApplicationContext(),++pagination,arrProduct,productAdapter,viewProducts,progress_loadmore);
                    isLoading = false;
                    break;
            }
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
