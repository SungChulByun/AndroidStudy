package com.example.viewpager_practice_byun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private ViewAdapter myAdapter;
    private TextView left, middle, right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        left = findViewById(R.id.search_type);
        middle = findViewById(R.id.search_text);
        right = findViewById(R.id.search_start);



    }
    private void init(){
        pager = findViewById(R.id.pager);
        myAdapter = new ViewAdapter(getLayoutInflater());
        pager.setAdapter(myAdapter);
    }

}
