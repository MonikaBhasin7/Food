package com.techsdm.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.techsdm.food.Model.FoodItem;
import com.techsdm.food.Model.OrderItem;

public class FoodDetail extends AppCompatActivity{

    FloatingActionButton btnCart;
    FoodItem foodItem1;
    TextView food_description;
    ImageView img_food;
    CollapsingToolbarLayout toolbar;
    TextView food_name;
    TextView food_price;
    ElegantNumberButton number_button;
    String photoLink;
    String data,dataPrevious;
    DatabaseReference foodDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        img_food=(ImageView)findViewById(R.id.img_food);
       toolbar=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        food_name=(TextView)findViewById(R.id.food_name);
        food_price=(TextView)findViewById(R.id.food_price);
        number_button=(ElegantNumberButton)findViewById(R.id.number_button);
        food_description=(TextView)findViewById(R.id.food_description);

        Bundle bundle=getIntent().getExtras();
        data=bundle.getString("name");
        dataPrevious=bundle.getString("namePrevious").trim();
        photoLink=bundle.getString("photo").trim();
        toolbar.setTitle(data.toUpperCase());
        data=data.replace(" ","-");
        data=data.trim();
        toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        foodDetail=FirebaseDatabase.getInstance().getReference("FoodDetails").child(dataPrevious);
        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Database dbs=new Database(getBaseContext());
                OrderItem orderItem=new OrderItem(foodItem1.getName(),number_button.getNumber(),foodItem1.getCost(),"0");
                dbs.addToCart(orderItem);

                Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        foodDetail.child(data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodItem1 = new FoodItem();
                foodItem1 = dataSnapshot.getValue(FoodItem.class);
                food_name.setText(foodItem1.getName());
                food_price.setText(foodItem1.getCost());
                food_description.setText(foodItem1.getDescription());

                Picasso.get()
                        .load(photoLink)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(img_food, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),FoodList.class);
        Bundle bundle=new Bundle();
        bundle.putString("backdata",dataPrevious);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
