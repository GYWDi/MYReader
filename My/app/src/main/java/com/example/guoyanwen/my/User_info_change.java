package com.example.guoyanwen.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_info_change extends AppCompatActivity {
    private TextView tv;
    private Intent intent;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_change);
        tv = findViewById(R.id.test);
        button = findViewById(R.id.test_button);
        intent = getIntent();
        String name = intent.getStringExtra("name");
        tv.setText(name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "带参数返回");
                setResult(2, intent);
                finish();
            }
        });
    }
}
