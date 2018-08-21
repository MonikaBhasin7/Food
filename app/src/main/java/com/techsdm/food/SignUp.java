package com.techsdm.food;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techsdm.food.Model.User;

public class SignUp extends AppCompatActivity {

    int check_counter=0;
    String MobilePattern = "[0-9]{10}";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference table_user;
    TextInputLayout phone_number;
    TextInputLayout name;
    TextInputLayout password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        phone_number=(TextInputLayout) findViewById(R.id.phone_number);
        name=(TextInputLayout) findViewById(R.id.name);
        password=(TextInputLayout) findViewById(R.id.password);

        firebaseDatabase=FirebaseDatabase.getInstance();
        table_user=firebaseDatabase.getReference("user");

        check_counter=0;
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

    private boolean validate_username()
    {
        String username_input=name.getEditText().getText().toString().trim();
        if(username_input.length()==0)
        {
            name.setError("Field can't be empty");
            return false;
        }
        if(username_input.length()>15)
        {
            name.setError("Username too Long");
            return false;
        }
        else
        {
            name.setError(null);
            return true;
        }
    }

    public void signup(View view)
    {
        if(!validate_phone_number() | !validate_password() | !validate_username())
        {
            return;
        }
        final ProgressDialog progressDialog=new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Signing Up");
        progressDialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(check_counter==0)
                {
                    if(dataSnapshot.child(phone_number.getEditText().getText().toString()).exists())
                    {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Phone Number Already Exists",Toast.LENGTH_SHORT).show();


                    }

                    else
                    {
                        progressDialog.dismiss();

                        User user=new User(name.getEditText().getText().toString(),password.getEditText().getText().toString());
                        table_user.child(phone_number.getEditText().getText().toString()).setValue(user);
                        Toast.makeText(getApplicationContext(),"Sign Up Success",Toast.LENGTH_SHORT).show();
                        check_counter=check_counter+1;
                        finish();

                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
