package com.techsdm.food.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.techsdm.food.FoodList;
import com.techsdm.food.Model.CategoryItem;
import com.techsdm.food.Model.FoodListItem;
import com.techsdm.food.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika Bhasin on 07-08-2018.
 */

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {


    List<FoodListItem> foodList=new ArrayList<FoodListItem>();

    public FoodListAdapter(List<FoodListItem> foodList) {
        this.foodList = foodList;
    }

    @Override
    public FoodListAdapter.FoodListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.foodlist_item_layout,parent,false);
        return new FoodListAdapter.FoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodListAdapter.FoodListViewHolder holder, int position) {
        final FoodListItem foodListItem=foodList.get(position);

        Picasso.get()
                .load(foodListItem.getImageLink())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(foodListItem.getImageLink())
                                .error(R.drawable.ic_broken_image_black_24dp)
                                .into(holder.image, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e("error","Wallpaper not Fetched");
                                    }
                                });
                    }
                });
        holder.name.setText(foodListItem.getName());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public FoodListViewHolder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.name);
            image=(ImageView)itemView.findViewById(R.id.image);
        }
    }
}
