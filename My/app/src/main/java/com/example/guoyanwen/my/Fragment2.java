package com.example.guoyanwen.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment2 extends Fragment {
    private GridView gridView;
    private List<Map<String,Object>> list;
    private Map<String,Object> map;
    private List<Book> bookList;
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
        String [] from = {"img","text"};
        int[] to ={R.id.img,R.id.text};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),list, R.layout.book,from,to);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Read_book.class);
                intent.putExtra("id",list.get(position).get("text").toString());
                startActivity(intent);
            }
        });
    }
    private void initData(){
        bookList = new ArrayList<Book>();
        int[] inoc={
                R.mipmap.radio,R.mipmap.radio,R.mipmap.radio,
                R.mipmap.radio,R.mipmap.radio,R.mipmap.radio,
                R.mipmap.radio,R.mipmap.radio
        };
        String[] label={
                "1","2","3","4","5","6","7","8"
        };
        list = new ArrayList<Map<String, Object>>();

        for(int i=0;i<label.length;i++){
            map = new HashMap<String,Object>();
            map.put("img",inoc[i]);
            map.put("text",label[i]);
            list.add(map);
        }

    }
}
