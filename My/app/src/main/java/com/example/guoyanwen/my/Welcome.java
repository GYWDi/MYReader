package com.example.guoyanwen.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {
    private Button loginbt;
    private Button registerbt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        loginbt = findViewById(R.id.welcome_loginbutton);
        registerbt = findViewById(R.id.welcome_registerbutton);
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Welcome.this,Login.class);
                startActivity(intent);
            }
        });
        registerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Welcome.this,Register.class);
                startActivity(intent);
            }
        });
    }
}
