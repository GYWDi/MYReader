package com.example.guoyanwen.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment2 extends Fragment {
    private GridView gridView;
    private List<Map<String,Object>> list;
    private Map<String,Object> map;
    private String[] bookName;
    private int[] inoc;
    private String[] ids;
    private int size;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,null);
        gridView=view.findViewById(R.id.gridview);
        fragment2();
        return view;
    }


    private void fragment2(){
        initData();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Read_book.class);
                intent.putExtra("id",ids[position]);
                intent.putExtra("chapter","1");
                startActivity(intent);
            }
        });
    }
    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/ReturnMybookList";
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", UserInfo.userid);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x13;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x13) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json
                        Toast.makeText(getActivity(), "key " + key, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = JSON.parseArray(key);
                        size = jsonArray.size();
                        bookName = new String[size];
                        inoc = new int[size];
                        ids = new String[size];
                        Toast.makeText(getActivity(), "size " + size, Toast.LENGTH_SHORT).show();
                        list = new ArrayList<Map<String, Object>>();
                        for (int i = 0; i < size; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            bookName[i] = jsonObject.getString("bookname");
                            ids[i] = jsonObject.getString("id");
                            inoc[i] = R.mipmap.radio;
                            map = new HashMap<String,Object>();
                            map.put("img",inoc[i]);
                            map.put("text",bookName[i]);
                            list.add(map);
                        }
                        String [] from = {"img","text"};
                        int[] to ={R.id.img,R.id.text};
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(),list, R.layout.book,from,to);
                        gridView.setAdapter(adapter);

                    }
                }
            };
        }).start();

     }
}

