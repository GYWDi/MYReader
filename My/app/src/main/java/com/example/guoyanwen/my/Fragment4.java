package com.example.guoyanwen.my;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.support.v4.app.Fragment;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment4 extends Fragment {
    private View view;
    private List<Map<String,String>> list;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment4,null);
        listView = view.findViewById(R.id.userlist);
        setListener();
        fragment4();

        return view;
    }

    private void fragment4(){
        initData();
    }
    private void setListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChangeInfo.class);
                intent.putExtra("name",list.get(0).get("content"));
                intent.putExtra("age",list.get(1).get("content"));
                startActivityForResult(intent,position);
            }
        });
    }
    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/GetUserInfo";
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid",UserInfo.userid);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x40;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            @SuppressLint("HandlerLeak")
            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x40) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json

                        JSONArray jsonArray = JSON.parseArray(key);
                        int size = jsonArray.size();

                        for (int i = 0; i < size; i++) {
                            list = new ArrayList<Map<String,String>>();

                            Map map = new HashMap<String,String>();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            map.put("title","姓名");
                            map.put("content",jsonObject.getString("name"));
                            list.add(map);
                            map = new HashMap<String,String>();
                            map.put("title","年龄");
                            map.put("content",jsonObject.getString("age"));
                            list.add(map);

                            String[] from={"title","content"};
                            int[] to ={R.id.title,R.id.content};
                            SimpleAdapter adapter = new SimpleAdapter(getActivity(),list, R.layout.user,from,to);
                            listView.setAdapter(adapter);
                        }
                    }
                }
            };
        }).start();
    }
       @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(resultCode == 1) {
                String name = data.getStringExtra("name");
                list.get(0).put("content", name);
                String age = data.getStringExtra("age");
                list.get(1).put("content",age);
            }
        }

    }
}
