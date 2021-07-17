package com.example.smartandhra;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class AdminLogin extends AppCompatActivity {
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }


    public void login(View view) {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        if(email.getText().toString().equals("admin@gmail.com")
                && password.getText().toString().equals("admin")){
            //correcct password
            Toast.makeText(this, "Login Succesfull",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,DisplayActivity.class));
        } else {
            Toast.makeText(this, "Invalid email or password",
                    Toast.LENGTH_SHORT).show();
            //wrong password
        }
    }
}