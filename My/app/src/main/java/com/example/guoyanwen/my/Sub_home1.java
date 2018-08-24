package com.example.guoyanwen.my;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sub_home1 extends Fragment {
    private GridView gridView;
    private List<Map<String,Object>> list;
    private String[] bookid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_home,null);
        gridView = view.findViewById(R.id.sub_home_gridview1);
        HomeBookList sub_home1 = new HomeBookList();
        list=null;
        list = sub_home1.initData(1);
        bookid = sub_home1.getBookid();
        sub_home1();
        return view;
    }
    private void sub_home1(){
        String [] from = {"img","text"};
        int[] to ={R.id.img,R.id.text};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),list, R.layout.book,from,to);
        gridView.setAdapter(adapter);
        resize();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Book_detail.class);
                intent.putExtra("id",bookid[position]);
                startActivity(intent);
            }
        });

    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public void resize(){
        gridView.setNumColumns(list.size());
        int itemWidth = dip2px(getActivity(), 100);
        // item之间的间隔
        int itemPaddingH = dip2px(getActivity(), 1);
        int gridviewWidth = list.size() * (itemWidth + itemPaddingH);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);

    }

}
