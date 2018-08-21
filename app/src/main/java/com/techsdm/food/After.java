package com.techsdm.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techsdm.food.Adapter.CategoryAdapter;
import com.techsdm.food.Model.CategoryItem;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class After extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Animation animation1;
    CategoryAdapter mAdapter;
    List<CategoryItem> categoryList=new ArrayList<CategoryItem>();
    DatabaseReference dbCategory;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Category");
        setSupportActionBar(toolbar);

        Paper.init(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(getApplicationContext(),Cart.class);
               startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.after, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(getApplicationContext(),Cart.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_item_one) {
            Intent intent=new Intent(getApplicationContext(),After.class);
            startActivity(intent);
        } else if (id == R.id.nav_item_two) {
            Intent intent=new Intent(getApplicationContext(),Cart.class);
            startActivity(intent);
        } else if (id == R.id.nav_item_three) {

            Paper.book().destroy();

            Intent intent=new Intent(getApplicationContext(),LogIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryList.clear();
        final ProgressDialog progressDialog=new ProgressDialog(After.this);
        progressDialog.setMessage("Loading Categories");
        progressDialog.show();
        dbCategory= FirebaseDatabase.getInstance().getReference("Category");
        progressDialog.cancel();
        dbCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CategoryItem categoryItem=new CategoryItem();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    categoryItem=postSnapshot.getValue(CategoryItem.class);
                    categoryList.add(categoryItem);
                }
                recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
                LayoutAnimationController animation1 = AnimationUtils.loadLayoutAnimation(getApplicationContext(),R.anim.layout_animation_fall_down);
                recyclerView.setLayoutAnimation(animation1);
                mAdapter = new CategoryAdapter(categoryList);
                RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        CategoryItem categoryItem = categoryList.get(position);
                        //Toast.makeText(getApplicationContext(), cricketer.getCname() + " is selected!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),FoodList.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("name",categoryItem.getName().trim());
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
}
