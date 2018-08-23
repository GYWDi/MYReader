package com.example.guoyanwen.my;

import android.content.Context;
import android.content.Intent;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


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
        /************************************************/
        String str = intent.getStringExtra("id");

        /******************************************************/
        //初始化章节信息
        initData();

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
        list = new ArrayList<String>();
        for(int i=0;i<10;i++){
            list.add("第"+i+"章");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.chapter,list);
        listView.setAdapter(arrayAdapter);
        txt = new char[1024];
        try {
            InputStream is = getResources().openRawResource(R.raw.test);
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            br.read(txt,0,1024);
            String temp = new String(txt);
            tv.setText(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }



}
