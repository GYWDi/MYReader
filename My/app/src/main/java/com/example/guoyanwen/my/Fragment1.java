package com.example.guoyanwen.my;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment1 extends Fragment {
    private  View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1,null);
        fragmentManager=getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.sub_home1, new Sub_home1());
        transaction.add(R.id.sub_home2,new Sub_home2());
        transaction.commit();
        return view;
    }
}
