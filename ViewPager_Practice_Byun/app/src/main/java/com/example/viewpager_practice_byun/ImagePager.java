package com.example.viewpager_practice_byun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ImagePager extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewAdapter viewAdapter;
    private TextView pager_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);

        init();
    }

    private void init(){
        ArrayList<String> list = getIntent().getStringArrayListExtra("list");

        viewAdapter = new ViewAdapter(LayoutInflater.from(this), list);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewAdapter);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0), true);
        pager_title = findViewById(R.id.pager_title);
        pager_title.setText(getIntent().getStringExtra("name"));

    }
}
