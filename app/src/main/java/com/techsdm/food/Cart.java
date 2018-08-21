package com.techsdm.food;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.techsdm.food.Adapter.CartAdapter;
import com.techsdm.food.Adapter.CategoryAdapter;
import com.techsdm.food.Model.CategoryItem;
import com.techsdm.food.Model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    String totals;
    TextView order_money;
    CartAdapter mAdapter;
    List<OrderItem> orderList=new ArrayList<OrderItem>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);
        
        order_money=(TextView)findViewById(R.id.order_money);

        orderList=new Database(this).getCarts();

        int total=0;
        for(OrderItem orderItem:orderList)
        {
            total=total+(Integer.parseInt(orderItem.getPrice()))*(Integer.parseInt(orderItem.getQuantity()));
        }
        totals=String.valueOf(total);
        order_money.setText(totals);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new CartAdapter(orderList);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

       

    }

}
