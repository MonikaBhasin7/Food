package com.techsdm.food.Adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.techsdm.food.Model.CategoryItem;
import com.techsdm.food.Model.OrderItem;
import com.techsdm.food.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Monika Bhasin on 08-08-2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Locale locale;
    private List<OrderItem> orderList;

    public CartAdapter(List<OrderItem> orderList) {
        this.orderList = orderList;
    }

    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cart_item_layout,parent,false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.CartViewHolder holder, int position) {

        final OrderItem orderItem=orderList.get(position);

        holder.product_name.setText(orderItem.getProductName());
        TextDrawable textDrawable=TextDrawable.builder().buildRect(""+orderItem.getQuantity(), Color.RED);
        holder.cart_item_count.setImageDrawable(textDrawable);
        //locale= ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
        //Toast.makeText(this,"Country"+locale.getDisplayCountry(),Toast.LENGTH_SHORT).show();
        //NumberFormat fmt=NumberFormat.getCurrencyInstance(Locale.forLanguageTag(locale.getCountry()));
        //int pricey=(Integer.parseInt(orderItem.getPrice()));
        holder.price.setText(orderItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        ImageView cart_item_count;
        TextView price;
        public CartViewHolder(View itemView) {
            super(itemView);

            product_name=(TextView)itemView.findViewById(R.id.product_name);
            cart_item_count=(ImageView) itemView.findViewById(R.id.cart_item_count);
            price=(TextView)itemView.findViewById(R.id.price);
        }
    }
}
