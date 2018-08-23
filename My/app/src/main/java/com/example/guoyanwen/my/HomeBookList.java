package com.example.guoyanwen.my;

import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeBookList {
    private int[] bookid;

    public int[] getBookid() {
        return bookid;
    }

    public List initData(int kind){
        List<Map<String,Object>> list;
        //从数据库中得到相应种类的书

        bookid = new int[10];

        //书的封面
        int[] inoc={
                R.mipmap.radio,R.mipmap.radio,R.mipmap.radio,
                R.mipmap.radio,R.mipmap.radio,R.mipmap.radio,
                R.mipmap.radio,R.mipmap.radio,R.mipmap.radio,
                R.mipmap.radio,R.mipmap.radio,R.mipmap.radio
        };
        //书的名称
        String[] label={
                "1","2","3","4","5","6","7","8",
                "1","2","3","4"
        };
        //书的id
        for(int i=0;i<10;i++){
            bookid[i] = i;
        }
        Map<String,Object> map;
        list = new ArrayList<Map<String,Object>>();
        for(int i=0;i<label.length;i++){
            map = new HashMap<String,Object>();
            map.put("img",inoc[i]);
            map.put("text",label[i]);
            list.add(map);
        }
        return list;
    }

}
