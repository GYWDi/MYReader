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

public class Login extends AppCompatActivity {
    private Button loginsubmit;
    private EditText loginusername;
    private EditText loginpassword;
    private Button register;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        setListener();

    }
    public void init(){
        loginsubmit = findViewById(R.id.login_submit);
        loginusername = (EditText)findViewById(R.id.login_nametext);
        loginpassword = (EditText)findViewById(R.id.login_passtext);
        register = findViewById(R.id.register);
    }
    public void setListener(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        loginsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url=HttpUtilsHttpURLConnection.BASE_URL+"/login";
                        Map<String, String> params = new HashMap<String, String>();
                        String name=loginusername.getText().toString();
                        String password=loginpassword.getText().toString();
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
                                try {
                                    JSONObject json= new JSONObject(key);
                                    String id = (String)json.get("userid");
                                    String result = (String) json.get("result");
                                    if ("success".equals(result)){
                                        Toast.makeText(Login.this,"登录成功",Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(Login.this,MainActivity.class);
                                        intent.putExtra("userid",id);
                                        startActivity(intent);
                                    }else if("error".equals(result)){
                                        Toast.makeText(Login.this,"登录失败",Toast.LENGTH_LONG).show();
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
}
