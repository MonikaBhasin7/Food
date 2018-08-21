package com.techsdm.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techsdm.food.Common.Common;
import com.techsdm.food.Model.User;

import io.paperdb.Paper;

public class LogIn extends AppCompatActivity {

    com.rey.material.widget.CheckBox remember;
    String MobilePattern = "[0-9]{10}";
    TextInputLayout phone_number;
    TextInputLayout password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference table_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseDatabase=FirebaseDatabase.getInstance();
        table_user=firebaseDatabase.getReference("user");

        phone_number=(TextInputLayout)findViewById(R.id.phone_number);
        password=(TextInputLayout)findViewById(R.id.password);
        remember=findViewById(R.id.remember);

        Paper.init(this);
    }

    private boolean validate_phone_number()
    {
        String phone_number_input=phone_number.getEditText().getText().toString().trim();
        if(phone_number_input.isEmpty())
        {
            phone_number.setError("Field can't be empty");
            return false;
        }
        else if(phone_number_input.length()!=10)
        {
            phone_number.setError("Length should be 10");
            return false;
        }
        else if(!phone_number_input.matches(MobilePattern))
        {
            phone_number.setError("Only be Numbers");
            return false;
        }
        else
        {
            phone_number.setError(null);
            return true;
        }
    }
    private boolean validate_password()
    {
        String password_input=password.getEditText().getText().toString().trim();
        if(password_input.isEmpty())
        {
            password.setError("Field can't be empty");
            return false;
        }
        else
        {
            password.setError(null);
            return true;
        }
    }

    public void login(View view)
    {
        if(!validate_phone_number() | !validate_password())
        {
            return;
        }
        if(remember.isChecked())
        {
            Paper.book().write(Common.USER_KEY,phone_number.getEditText().getText().toString().trim());
            Paper.book().write(Common.PWD_KEY,password.getEditText().getText().toString().trim());
        }
        final ProgressDialog progressDialog=new ProgressDialog(LogIn.this);
        progressDialog.setMessage("Logining");
        progressDialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(phone_number.getEditText().getText().toString()).exists())
                {
                    progressDialog.dismiss();
                    User user=dataSnapshot.child(phone_number.getEditText().getText().toString()).getValue(User.class);
                    user.setPhone(phone_number.getEditText().getText().toString());

                    if(user.getPassword().equals(password.getEditText().getText().toString()))
                    {
                        progressDialog.dismiss();
                        Intent homeIntent=new Intent(getApplicationContext(),After.class);
                        //Bundle bundle=new Bundle();
                        //bundle.putString("logphone",phone_number.getText().toString().trim());
                        //homeIntent.putExtras(bundle);
                        startActivity(homeIntent);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"User is Correct Password is not Correct",Toast.LENGTH_SHORT).show();

                    }
                }
                else if(!dataSnapshot.child(phone_number.getEditText().getText().toString()).exists())
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"User not Exist",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void tosignup(View view)
    {
        Intent intent=new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }
}
