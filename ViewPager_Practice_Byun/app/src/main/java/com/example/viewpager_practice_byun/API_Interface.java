package com.example.viewpager_practice_byun;

import com.example.viewpager_practice_byun.SearchType.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Interface {
    String clientid = "xtRJzO1RX4ruym2PKeao";
    String secret = "Be7KOxAwMi";
    String appName = "ByunSungChul_ViewPager";


    @Headers({
            "X-Naver-Client-Id: xtRJzO1RX4ruym2PKeao",
            "X-Naver-Client-Secret: Be7KOxAwMi"
    })
    @GET("/v1/search/{type}")
    Call<Image_Result> search_Image(@Path("type") String stype , @Query("query") String text, @Query("display") int num);

    @Headers({
            "X-Naver-Client-Id: xtRJzO1RX4ruym2PKeao",
            "X-Naver-Client-Secret: Be7KOxAwMi"
    })
    @GET("/v1/search/{type}")
    Call<Blog_Result> search_Blog(@Path("type") String stype , @Query("query") String text, @Query("display") int num);


    @Headers({
            "X-Naver-Client-Id: xtRJzO1RX4ruym2PKeao",
            "X-Naver-Client-Secret: Be7KOxAwMi"
    })
    @GET("/v1/search/{type}")
    Call<News_Result> search_News(@Path("type") String stype , @Query("query") String text, @Query("display") int num);

    @GET("/v1/{service}/{type}")
    Call<Profile_Result> getProfile(
            @Path("service") String service, @Path("type") String type
            , @Header("Authorization") String headers);
}
