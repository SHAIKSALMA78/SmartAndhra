package com.example.smartandhra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

    }
    public void adminlogin(View view) {
        startActivity(new Intent(HomePage.this,AdminLogin.class));

    }

    public void register(View view) {
        startActivity(new Intent(this,RegisterProblem.class));
    }
}