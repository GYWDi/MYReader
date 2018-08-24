package com.example.guoyanwen.my;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ChangeInfo extends AppCompatActivity {

    private EditText name;
    private EditText age;
    private Intent intent;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        name = findViewById(R.id.changename);
        age = findViewById(R.id.changeage);
        button = findViewById(R.id.test_button);
        intent = getIntent();
        String name_ = intent.getStringExtra("name");
        String age_ = intent.getStringExtra("age");
        name.setText(name_);
        age.setText(age_);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
                Intent intent = new Intent();
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("age",age.getText().toString());
                setResult(1, intent);
                finish();
            }
        });
    }
    private void modify(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/ModifyUserInfo";
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid",UserInfo.userid);
                params.put("name",name.getText().toString());
                params.put("age",age.getText().toString());
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x16;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x16) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json

                        JSONArray jsonArray = JSON.parseArray(key);
                        int size = jsonArray.size();
                    }
                }
            };
        }).start();

    }
}
