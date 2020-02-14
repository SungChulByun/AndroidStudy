package com.example.viewpager_practice_byun;

import com.example.viewpager_practice_byun.SearchType.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Interface {
    String clientid = "nnfdZXpFFzGmrG5JGWMt";
    String secret = "8B3emomXbq";
    String appName = "ByunSungChul_ViewPager";


    @Headers({
            "X-Naver-Client-Id: nnfdZXpFFzGmrG5JGWMt",
            "X-Naver-Client-Secret: 8B3emomXbq"
    })
    @GET("/v1/search/{type}")
    Call<Image_Result> search_Image(@Path("type") String stype , @Query("query") String text);

    @Headers({
            "X-Naver-Client-Id: nnfdZXpFFzGmrG5JGWMt",
            "X-Naver-Client-Secret: 8B3emomXbq"
    })
    @GET("/v1/search/{type}")
    Call<Blog_Result> search_Blog(@Path("type") String stype , @Query("query") String text);


    @Headers({
            "X-Naver-Client-Id: nnfdZXpFFzGmrG5JGWMt",
            "X-Naver-Client-Secret: 8B3emomXbq"
    })
    @GET("/v1/search/{type}")
    Call<News_Result> search_News(@Path("type") String stype , @Query("query") String text);
}
