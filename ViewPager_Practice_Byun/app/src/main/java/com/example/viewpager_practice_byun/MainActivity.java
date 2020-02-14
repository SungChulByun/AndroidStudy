package com.example.viewpager_practice_byun;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.viewpager_practice_byun.SearchType.Blog_Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "asdf";

    private API_Interface apiInterface;
    private ViewPager pager;
    private ViewAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child, null);



        apiInterface = API_Client.getClient().create(API_Interface.class);

        String text = "naver";
        String stype = "blog.json";

        Call<Blog_Result> call = apiInterface.search_Image(stype, text);
        call.enqueue(new Callback<Blog_Result>() {
            @Override
            public void onResponse(Call<Blog_Result> call, Response<Blog_Result> response) {


                Log.d("TAG", response.code() + "");

                String displayResponse = "";

                Blog_Result resource = response.body();
                String lastbuild = resource.lastBuildDate;
                Integer total = resource.total;
                Integer start = resource.start;
                Integer display = resource.display;
                List<Blog_Result.blogDetail> bList = resource.items;

                displayResponse +=
                        "수정 날짜 : " + lastbuild + "\n" +
                                "Total : " + total + "\n" +
                                "Start : " + start + "\n" +
                                "Display : " + display + "\n" +
                                "Title : " + bList.get(0).title + "\n" +
                                "Description" + bList.get(0).description + "\n" +
                                "Blog Name : " + bList.get(0).bloggername;


            }

            @Override
            public void onFailure(Call<Blog_Result> call, Throwable t) {
                call.cancel();
            }
        });

    }


    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    };


    private void init(){
        pager = findViewById(R.id.pager);
        myAdapter = new ViewAdapter(getLayoutInflater());
        pager.setAdapter(myAdapter);
    }

}
