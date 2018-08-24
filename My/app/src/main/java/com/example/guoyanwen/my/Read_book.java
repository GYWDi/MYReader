package com.example.guoyanwen.my;

import android.content.Intent;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.TypedValue;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Read_book extends AppCompatActivity{
    private TextView tv;
    private char[] txt;
    private List<String> list;
    BufferedReader br;
    InputStreamReader isr;
    private RadioButton plus;
    private RadioButton sub;
    private int font_size;
    private SeekBar light_setting;
    private ListView listView;
    private RadioButton defaultfont;
    private RadioButton song;
    private RadioButton xing;
    private Typeface typeFace;
    private String bookid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_book);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        tv = findViewById(R.id.text);
        plus = findViewById(R.id.plus);
        sub = findViewById(R.id.sub);
        listView=findViewById(R.id.left_listview);
        light_setting = findViewById(R.id.light_setting);
        light_setting.setMax(255);
        defaultfont = findViewById(R.id.defaultfont);
        song = findViewById(R.id.song);
        xing = findViewById(R.id.xing);
        Intent intent = getIntent();
        bookid = intent.getStringExtra("id");
        //初始化章节信息
        initData();

        /************************************************/

        readChapter(bookid,1);

        /******************************************************/

        try {
            light_setting.setProgress(Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS));
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        font_size = 36;
        setListener();
        typeFace =Typeface.createFromAsset(getAssets(),"msyh.ttf");
        tv.setTypeface(typeFace);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,font_size);
    }


    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/ReturnChapterNumber";
                Map<String, String> params = new HashMap<String, String>();
                params.put("bookid",bookid);
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x30;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x30) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json
                        JSONArray jsonArray = JSON.parseArray(key);
                        int chapter = 0;
                        int size = jsonArray.size();
                        for (int i = 0; i < size; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String temp = jsonObject.getString("sum");
                            if(temp.equals("")){
                                chapter = 0;
                            }else{
                                chapter = Integer.parseInt(temp);
                            }
                        }
                        list = new ArrayList<String>();
                        for(int i=1;i<=chapter;i++){
                            list.add("第"+i+"章");
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Read_book.this,R.layout.chapter,list);
                        listView.setAdapter(arrayAdapter);
                    }
                }
            };
        }).start();

    }

    private void setListener(){
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font_size = font_size + 5;
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,font_size);
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font_size = font_size - 5;
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,font_size);
            }
        });

        light_setting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Window window = getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                if (progress == -1) {
                    lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                } else {
                    lp.screenBrightness = (progress <= 0 ? 1 : progress) / 255f;
                }
                window.setAttributes(lp);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        defaultfont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeFace =Typeface.createFromAsset(getAssets(),"msyh.ttf");
                tv.setTypeface(typeFace);
            }
        });
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeFace =Typeface.createFromAsset(getAssets(),"song.ttf");
                tv.setTypeface(typeFace);
            }
        });
        xing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeFace =Typeface.createFromAsset(getAssets(),"xing.ttf");
                tv.setTypeface(typeFace);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readChapter(bookid,position+1);
            }
        });
    }
    private void readChapter(final String bookid, final int chapter){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HttpUtilsHttpURLConnection.BASE_URL + "/ReturnBookContext";
                Map<String, String> params = new HashMap<String, String>();
                params.put("bookid",bookid);
                params.put("chapterno",String.valueOf(chapter));
                String result = HttpUtilsHttpURLConnection.getContextByHttp(url, params);
                Message msg = new Message();
                msg.what = 0x18;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                hander.sendMessage(msg);
            }

            Handler hander = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x18) {
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json

                        JSONArray jsonArray = JSON.parseArray(key);
                        int size = jsonArray.size();
                        for (int i = 0; i < size; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            tv.setText(jsonObject.getString("chaptercontent"));
                        }
                    }
                }
            };
        }).start();
    }



}
