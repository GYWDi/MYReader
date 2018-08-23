package com.example.guoyanwen.my;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book_detail extends AppCompatActivity {
    private ListView listView;
    private Map<String,String> map;
    private List<Map<String,String>> list;
    private TextView tv;
    private Button read;
    private Button collection;
    private int book_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        tv = findViewById(R.id.summary);
        listView = findViewById(R.id.book_detail);
        read = findViewById(R.id.read);
        collection = findViewById(R.id.collection);



        setListener();
        book_detail();
    }

    private void setListener(){
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_detail.this, Read_book.class);
                intent.putExtra("id",book_id);
                startActivity(intent);
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserInfo.userid  && bookid;  发送请求
            }
        });
    }
    private void initData(){
        Book book = new Book();
        tv.setText("暂无");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url=HttpUtilsHttpURLConnection.BASE_URL+"/ReturnMybookList";
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid",UserInfo.userid);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url,params);
                Message msg = new Message();
                msg.what=0x13;
                Bundle data=new Bundle();
                data.putString("result",result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            Handler hander = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what==0x13){
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json
                        try {
                            JSONObject json= new JSONObject(key);
                            String id = (String)json.get("userid");
                            String result = (String) json.get("result");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }).start();
        list = new ArrayList<Map<String,String>>();
        for(int i=0;i<label.length;i++){
            map = new HashMap<String,String>();
            map.put("label",label[i]);
            map.put("value",value[i]);
            list.add(map);
        }
        String [] from = {"label","value"};
        int[] to ={R.id.title,R.id.content};
        SimpleAdapter adapter = new SimpleAdapter(Book_detail.this,list, R.layout.user,from,to);
        listView.setAdapter(adapter);
    }
    private void book_detail(){
        //initData();
    }
}
