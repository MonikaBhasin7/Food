package com.techsdm.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techsdm.food.Common.Common;
import com.techsdm.food.Model.User;

import io.paperdb.Paper;


public class SplashScreen extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference table_user;
    RelativeLayout layout;
    Animation splash_screen_animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseDatabase= FirebaseDatabase.getInstance();
        table_user=firebaseDatabase.getReference("user");
        Paper.init(this);
        layout=(RelativeLayout)findViewById(R.id.layout);
        splash_screen_animation= AnimationUtils.loadAnimation(this,R.anim.splash_screen_anim);
        layout.startAnimation(splash_screen_animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    String number=Paper.book().read(Common.USER_KEY);
                    String password=Paper.book().read(Common.PWD_KEY);

                    if(number !=null && password!=null)
                    {
                        if(!number.isEmpty() && !password.isEmpty())
                        {
                            login_splash(number,password);
                        }
                        else
                        {
                            Intent intent=new Intent(getApplicationContext(),LogIn.class);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        Intent intent=new Intent(getApplicationContext(),LogIn.class);
                        startActivity(intent);
                    }


            }
        },2500);

}

    public void login_splash(final String number, final String password)
    {
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(number).exists())
                {

                    User user=dataSnapshot.child(number).getValue(User.class);
                    user.setPhone(number);

                    if(user.getPassword().equals(password))
                    {
                        Intent homeIntent=new Intent(getApplicationContext(),After.class);
                        //Bundle bundle=new Bundle();
                        //bundle.putString("logphone",phone_number.getText().toString().trim());
                        //homeIntent.putExtras(bundle);
                        startActivity(homeIntent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"User is Correct Password is not Correct",Toast.LENGTH_SHORT).show();

                    }
                }
                else if(!dataSnapshot.child(number).exists())
                {
                    Toast.makeText(getApplicationContext(),"User not Exist",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
