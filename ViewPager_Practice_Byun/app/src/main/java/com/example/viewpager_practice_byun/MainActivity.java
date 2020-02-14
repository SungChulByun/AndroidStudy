package com.example.viewpager_practice_byun;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.viewpager_practice_byun.CustomData.customData;
import com.example.viewpager_practice_byun.CustomData.dataBlog;
import com.example.viewpager_practice_byun.CustomData.dataImage;
import com.example.viewpager_practice_byun.CustomData.dataNews;
import com.example.viewpager_practice_byun.SearchType.Blog_Result;
import com.example.viewpager_practice_byun.SearchType.Image_Result;
import com.example.viewpager_practice_byun.SearchType.News_Result;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "asdf";

    private ChatAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ImageView searchButton;
    private EditText searchText;
    private TextView mainTitle;

    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;

    private InputMethodManager imm;

    private API_Interface apiInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        apiInterface = API_Client.getClient().create(API_Interface.class);
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(myListener);
        searchText = findViewById(R.id.search_text);
        mainTitle = findViewById(R.id.main_name);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if(text.length()>0){
                    searchButton.setImageResource(R.drawable.search_button_green);
                }
                else{
                    searchButton.setImageResource(R.drawable.search_button_normal);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search_button:
                    String text = searchText.getText().toString();
                    mainSearchNews(text);
                    mainSearchBlog(text);
                    hideKeyboard();
                    moveRecyclerView(mAdapter.getItemCount()-1);
                    break;
            }
        }
    };

    public void moveRecyclerView(int position){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRecyclerView.scrollToPosition(position);

    }
    public void hideKeyboard(){
        imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
    }


    public void mainSearchImage(String text){
        String stype = "image.json";

        Call<Image_Result> call = apiInterface.search_Image(stype, text);
        call.enqueue(new Callback<Image_Result>() {
            @Override
            public void onResponse(Call<Image_Result> call, Response<Image_Result> response) {

                Image_Result resource = response.body();
                String lastbuild = resource.lastBuildDate;
                Integer total = resource.total;
                Integer start = resource.start;
                Integer display = resource.display;
                List<Image_Result.imageDetail> iList = resource.items;
                Image_Result.imageDetail idetail = iList.get(0);

                dataImage dimage = new dataImage(idetail.link);
                addData(dimage);
            }
            @Override
            public void onFailure(Call<Image_Result> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void mainSearchNews(String text){
        String stype = "news.json";

        Call<News_Result> call = apiInterface.search_News(stype, text);
        call.enqueue(new Callback<News_Result>() {
            @Override
            public void onResponse(Call<News_Result> call, Response<News_Result> response) {

                News_Result resource = response.body();
                String lastbuild = resource.lastBuildDate;
                Integer total = resource.total;
                Integer start = resource.start;
                Integer display = resource.display;
                List<News_Result.newsDetail> nList = resource.items;
                News_Result.newsDetail ndetail = nList.get(0);

                dataNews dnews = new dataNews(convertHTML(ndetail.title), convertHTML(ndetail.description), convertHTML(ndetail.pubDate), ndetail.link);
                addData(dnews);
            }
            @Override
            public void onFailure(Call<News_Result> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void mainSearchBlog(String text){
        String stype = "blog.json";

        Call<Blog_Result> call = apiInterface.search_Blog(stype, text);
        call.enqueue(new Callback<Blog_Result>() {
            @Override
            public void onResponse(Call<Blog_Result> call, Response<Blog_Result> response) {

                Blog_Result resource = response.body();
                String lastbuild = resource.lastBuildDate;
                Integer total = resource.total;
                Integer start = resource.start;
                Integer display = resource.display;
                List<Blog_Result.blogDetail> bList = resource.items;
                Blog_Result.blogDetail bdetail = bList.get(0);

                dataBlog dblog = new dataBlog(convertHTML(bdetail.title), convertHTML(bdetail.description), convertHTML(bdetail.bloggername), bdetail.link);
                addData(dblog);
            }
            @Override
            public void onFailure(Call<Blog_Result> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void addData(customData data){
        mAdapter.addItem(data);
        mAdapter.notifyItemInserted(mAdapter.getItemCount()-1);

    }

    public String convertHTML(String text){
        CharSequence sequence = Html.fromHtml(text);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        return String.valueOf(strBuilder);
    }



    private void init(){
        mContext = this;
        mAdapter = new ChatAdapter(mContext);
        mRecyclerView = findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

}
