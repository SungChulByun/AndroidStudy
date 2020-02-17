package com.example.viewpager_practice_byun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.viewpager_practice_byun.CustomData.customData;
import com.example.viewpager_practice_byun.CustomData.dataBlog;
import com.example.viewpager_practice_byun.CustomData.dataHistory;
import com.example.viewpager_practice_byun.CustomData.dataImage;
import com.example.viewpager_practice_byun.CustomData.dataNews;
import com.example.viewpager_practice_byun.ImagePage.ImageResult;
import com.example.viewpager_practice_byun.SearchType.Blog_Result;
import com.example.viewpager_practice_byun.SearchType.Image_Result;
import com.example.viewpager_practice_byun.SearchType.News_Result;
import com.example.viewpager_practice_byun.SearchType.Profile_Result;

import java.net.URI;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "asdf";

    private ChatAdapter mAdapter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ImageView searchButton;
    private EditText searchText;
    private TextView mainTitle;
    private CheckBox checkBox_Image;
    private CheckBox checkBox_Blog;
    private CheckBox checkBox_News;

    private final int TYPE_IMAGE = 9999;
    private final int TYPE_BLOG = 9998;
    private final int TYPE_NEWS = 9997;

    private InputMethodManager imm;

    private API_Interface apiInterface;
    private String accessToken;

    private ImageView userProfile;
    private String userProfile_Link;
    private TextView userNickname;
    private String userNickname_String;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        apiInterface = API_Client.getClient().create(API_Interface.class);
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(myListener);
        searchText = findViewById(R.id.search_text);
        mainTitle = findViewById(R.id.main_name);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        checkBox_Blog = findViewById(R.id.type_blog);
        checkBox_Image = findViewById(R.id.type_image);
        checkBox_News = findViewById(R.id.type_news);

        userProfile = findViewById(R.id.main_image);
        userNickname = findViewById(R.id.main_name);

        init();


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
                    addData(new dataHistory(text, userNickname_String, userProfile_Link));
                    if(checkBox_News.isChecked()) mainSearchNews(text);
                    if(checkBox_Blog.isChecked()) mainSearchBlog(text);
                    if(checkBox_Image.isChecked()) mainSearchImage(text);

                    searchText.setText("");
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

    private ChatAdapter.myOnItemClickListener adapterListener = new ChatAdapter.myOnItemClickListener() {
        @Override
        public void OnItemClick(int position) {
            int viewType = mAdapter.getItemViewType(position);
            if (viewType == TYPE_IMAGE) {
                //Todo GridRecyclerView
                Intent imageIntent = new Intent(mContext, ImageResult.class);
                imageIntent.putExtra("name", mAdapter.getItem(position).getName());
                imageIntent.putStringArrayListExtra("list", ((dataImage) mAdapter.getItem(position)).getImageList());
                startActivity(imageIntent);
            }
            else {
                Intent bIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mAdapter.getItem(position).getLink()));
                startActivity(bIntent);
            }
        }
    };

    public void mainSearchImage(final String text){
        String stype = "image.json";

        Call<Image_Result> call = apiInterface.search_Image(stype, text, 99);
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

                dataImage dimage = new dataImage(idetail.link, text, iList);

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
        Call<News_Result> call = apiInterface.search_News(stype, text,1);
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

        Call<Blog_Result> call = apiInterface.search_Blog(stype, text, 1);
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

    public void setUserProfile(){
        Call<Profile_Result> profile = apiInterface.getProfile("nid", "me", "Bearer "+accessToken);
        profile.enqueue(new Callback<Profile_Result>() {
            @Override
            public void onResponse(Call<Profile_Result> call, Response<Profile_Result> response) {
                Profile_Result result = response.body();
                Profile_Result.Response userData = result.response;
                userProfile_Link = userData.profile_image;
                Glide.with(userProfile).load(userProfile_Link).into(userProfile);

                userNickname_String = userData.nickname.trim();
                userNickname.setText(userNickname_String);
            }

            @Override
            public void onFailure(Call<Profile_Result> call, Throwable t) {

            }
        });
    }



    public void addData(customData data){
        mAdapter.addItem(data);
        mAdapter.notifyItemInserted(mAdapter.getItemCount()-1);

    }

    public String convertHTML(String text){
        return String.valueOf(new SpannableStringBuilder(Html.fromHtml(text)));
    }


    private void init(){

        mContext = this;
        mAdapter = new ChatAdapter(mContext);
        mRecyclerView = findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setmyOnItemClickListener(adapterListener);
        accessToken = getIntent().getStringExtra("token");
        setUserProfile();
    }

}
