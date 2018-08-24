package com.example.guoyanwen.my;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Book {
    public static String[] bookname;
    public static String[] bookid;
    public static String[] kind;
    public static int sum;
    public static int inoc[];
    public static int count1;
    public static int count2;
    public Book(){
        count1 = 0;
        count2 = 0;
        sum = 0;
        initData();
    }
    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/GetBookKingList";
                Map<String, String> params = new HashMap<String, String>();
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x17;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x17) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json

                        JSONArray jsonArray = JSON.parseArray(key);
                        int size = jsonArray.size();
                        sum = size;
                        bookname = new String[size];
                        kind= new String[size];
                        bookid = new String[size];
                        inoc = new int[size];
                        for (int i = 0; i < size; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            bookname[i] = jsonObject.getString("bookname");
                            bookid[i] = jsonObject.getString("id");
                            kind[i] = jsonObject.getString("kind");
                            if(kind[i].equals("1")){
                                count1++;
                            }else if(kind[i].equals("2")){
                                count2++;
                            }
                            inoc[i] = R.mipmap.radio;
                        }
                    }
                }
            };
        }).start();

    }
}
