package com.example.guoyanwen.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private int lastFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(R.id.navigation_home);
                    return true;
                case R.id.navigation_shelf:
                    switchFragment(R.id.navigation_shelf);
                    return true;
//                case R.id.navigation_topic:
//                    switchFragment(R.id.navigation_topic);
//                    return true;
                case R.id.navigation_myInfo:
                    switchFragment(R.id.navigation_myInfo);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //利用Book构造函数初始化主页书籍信息
     //   Book book = new Book();

        //从数据库中获得userid
        Intent intent = getIntent();
        String userid = intent.getStringExtra("userid");
        UserInfo.userid=userid;
        fragmentManager=getSupportFragmentManager();
        lastFragment = R.id.navigation_home;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation = findViewById(R.id.navigation);
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.layout_content, new Fragment1());
        transaction.commit();
        navigation.setItemHorizontalTranslationEnabled(false);
        navigation.setLabelVisibilityMode(1);
    }
    private void switchFragment(int frag){
        if(frag==lastFragment) {
            return;
        }else{
            switch (frag) {
                case R.id.navigation_home:
                    transaction.replace(R.id.layout_content, new Fragment1());
                    transaction.commit();
                    lastFragment = R.id.navigation_home;
                    return;
                case R.id.navigation_shelf:
                    transaction.replace(R.id.layout_content, new Fragment2());
                    transaction.commit();
                    lastFragment =R.id.navigation_shelf;
                    return;
//                case R.id.navigation_topic:
//                    transaction.replace(R.id.layout_content, new Fragment3());
//                    transaction.commit();
//                    lastFragment =R.id.navigation_topic;
//                    return;
                case R.id.navigation_myInfo:
                    transaction.replace(R.id.layout_content, new Fragment4());
                    transaction.commit();
                    lastFragment =R.id.navigation_myInfo;
                    return;
            }

        }
    }
}
