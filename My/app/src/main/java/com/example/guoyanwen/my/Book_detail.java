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


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

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
    private String[] label;
    private String[] value;
    private String bookid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        tv = findViewById(R.id.summary);
        listView = findViewById(R.id.book_detail);
        read = findViewById(R.id.read);
        collection = findViewById(R.id.collection);

        Intent intent = getIntent();
        bookid = intent.getStringExtra("id");
        setListener();
        book_detail();
    }
    private void book_detail(){
        initData();
    }
    private void setListener(){
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_detail.this, Read_book.class);
                intent.putExtra("id",bookid);
                startActivity(intent);
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCollection();
            }
        });
    }
    private void initData(){
        tv.setText("暂无");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/GetBook";
                Map<String, String> params = new HashMap<String, String>();
                params.put("bookid", bookid);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x14;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);

                hander.sendMessage(msg);
            }

            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x14) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json
                        JSONArray jsonArray = JSON.parseArray(key);
                        int size = jsonArray.size();



                        for (int i = 0; i < size; i++) {
                            list = new ArrayList<Map<String,String>>();

                            Map map = new HashMap<String,String>();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            map.put("title","书名");
                            map.put("content",jsonObject.getString("bookname"));
                            list.add(map);
                            map = new HashMap<String,String>();
                            map.put("title","ISBN");
                            map.put("content",jsonObject.getString("ISBN"));
                            list.add(map);

                            map = new HashMap<String,String>();
                            map.put("title","出版社");
                            map.put("content",jsonObject.getString("publish"));
                            list.add(map);
                            map = new HashMap<String,String>();
                            map.put("title","作者");
                            map.put("content",jsonObject.getString("writer"));
                            list.add(map);


                            String[] from={"title","content"};
                            int[] to ={R.id.title,R.id.content};
                            SimpleAdapter adapter = new SimpleAdapter(Book_detail.this,list, R.layout.user,from,to);
                            listView.setAdapter(adapter);
                        }
                    }
                }
            };
        }).start();
    }
    private void setCollection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/NewCollection";
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid",UserInfo.userid);
                params.put("bookid",bookid);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x20;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x20) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json
                        try {
                            org.json.JSONObject json= new org.json.JSONObject(key);
                            String id = (String)json.get("userid");
                            String result = (String) json.get("result");
                            if ("success".equals(result)){
                                Toast.makeText(Book_detail.this,"添加成功",Toast.LENGTH_LONG).show();
                            }else if("error".equals(result)){
                                Toast.makeText(Book_detail.this,"已添加无需再次添加",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
        }).start();

    }
}
