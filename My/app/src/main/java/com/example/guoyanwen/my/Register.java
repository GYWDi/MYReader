package com.example.guoyanwen.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText registername;
    private EditText registerpass;
    private Button registersubmit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        init();
        registersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url=HttpUtilsHttpURLConnection.BASE_URL+"/register";
                        Map<String, String> params = new HashMap<String, String>();
                        String name=registername.getText().toString();
                        String password=registerpass.getText().toString();
                        params.put("name",name);
                        params.put("pass",password);

                        String result = HttpUtilsHttpURLConnection.getContextByHttp(url,params);

                        Message msg = new Message();
                        msg.what=0x12;
                        Bundle data=new Bundle();
                        data.putString("result",result);
                        msg.setData(data);
                        hander.sendMessage(msg);
                    }

                    Handler hander = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what==0x12){
                                Bundle data = msg.getData();
                                String key = data.getString("result");//得到json返回的json
//                                   Toast.makeText(MainActivity.this,key,Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject json= new JSONObject(key);
                                    String result = (String) json.get("result");
                                    if ("success".equals(result)){
                                        Toast.makeText(Register.this,"注册成功",Toast.LENGTH_LONG).show();
                                        Intent intent =new Intent(Register.this,Login.class);
                                        startActivity(intent);
                                    }else if("error".equals(result)){
                                        Toast.makeText(Register.this,"注册失败",Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }).start();
            }
        });
    }
    private void init(){
        this.registername = findViewById(R.id.register_nametext);
        this.registerpass = findViewById(R.id.register_passtext);
        this.registersubmit = findViewById(R.id.register_submit);
    }
}
