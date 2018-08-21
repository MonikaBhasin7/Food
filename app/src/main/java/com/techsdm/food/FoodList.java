package com.techsdm.food;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techsdm.food.Adapter.FoodListAdapter;
import com.techsdm.food.Model.CategoryItem;
import com.techsdm.food.Model.FoodListItem;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    Animation animation1;
    Toolbar toolbar;
    String data1=null;
    FoodListAdapter mAdapter;
    String data;
    DatabaseReference dbFoodList;
    RecyclerView recyclerView;
    List<FoodListItem> foodList=new ArrayList<FoodListItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);





        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Food List");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle=getIntent().getExtras();
        data=bundle.getString("name");
        data1=bundle.getString("backdata");
        if(data1!=null)
        {
            data=data1;
        }
        data=data.trim();
    }

    @Override
    protected void onStart() {
        super.onStart();
        foodList.clear();
        dbFoodList= FirebaseDatabase.getInstance().getReference("Food").child(data);
        dbFoodList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FoodListItem foodListItem= new FoodListItem();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    foodListItem=postSnapshot.getValue(FoodListItem.class);
                    foodList.add(foodListItem);
                }
                recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
                LayoutAnimationController animation1 = AnimationUtils.loadLayoutAnimation(getApplicationContext(),R.anim.layout_animation_fall_down);
                recyclerView.setLayoutAnimation(animation1);
                mAdapter = new FoodListAdapter(foodList);
                recyclerView.setHasFixedSize(true);
                /*RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());*/
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(mAdapter);

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        FoodListItem foodListItem = foodList.get(position);
                        //Toast.makeText(getApplicationContext(), cricketer.getCname() + " is selected!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),FoodDetail.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("name",foodListItem.getName().trim());
                        bundle.putString("namePrevious",data);
                        bundle.putString("photo",foodListItem.getImageLink());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),After.class);
        Bundle bundle=new Bundle();
        bundle.putString("test","1");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.food_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent=new Intent(getApplicationContext(),Cart.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
