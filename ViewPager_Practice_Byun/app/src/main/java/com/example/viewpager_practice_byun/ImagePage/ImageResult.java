package com.example.viewpager_practice_byun.ImagePage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpager_practice_byun.ImagePager;
import com.example.viewpager_practice_byun.R;

import java.util.ArrayList;

public class ImageResult extends AppCompatActivity {

    private TextView title;
    private Context mContext;
    private ImageAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Intent intent;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);

        init();

    }

    private ImageAdapter.OnGridItemClickListener myGridListener = new ImageAdapter.OnGridItemClickListener() {
        @Override
        public void OnGridItemClick(int position) {
            Intent nintent = new Intent(ImageResult.this, ImagePager.class);
            nintent.putStringArrayListExtra("list", list);
            nintent.putExtra("position", position);
            nintent.putExtra("name",intent.getStringExtra("name"));
            startActivity(nintent);
        }
    };

    private void init(){
        mContext = this;
        mAdapter = new ImageAdapter(mContext);
        mRecyclerView = findViewById(R.id.result_image_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(mAdapter);
        title = findViewById(R.id.result_title);
        intent = getIntent();
        title.setText(intent.getStringExtra("name"));
        list = intent.getStringArrayListExtra("list");
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnGridItemClickListenr(myGridListener);
    }
}
