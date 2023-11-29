package com.example.socialdisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

public class second_Screen extends AppCompatActivity {
    TabLayout tab;
    ViewPager ViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_layout);
        tab = findViewById(R.id.tab);
        ViewPager = findViewById(R.id.viewpager);
        ViewPager.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewPagerMessenger adapter = new ViewPagerMessenger(getSupportFragmentManager());
        ViewPager.setAdapter(adapter);
        tab.setupWithViewPager(ViewPager);



    }


}


