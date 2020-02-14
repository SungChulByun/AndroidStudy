package com.example.viewpager_practice_byun;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Search extends AsyncTask<String, Void, Search.Testservice>{
    private final String TAG = "sungchul";

    private final String clientid = "nnfdZXpFFzGmrG5JGWMt";
    private final String secret = "B3emomXbq";
    private final String appName = "ByunSungChul_ViewPager";


    private static final String BASE_URL = "https://reqres.in/";

    private String test;

    public Search(String asdf) {
        this.test = asdf;
    }

    @Override
    protected Search.Testservice doInBackground(String... strings) {
        return getApiService();
    }

    @Override
    protected void onPostExecute(Testservice testservice) {
        try {
            Log.d(TAG, "onPostExecute: "+testservice.getTest().execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onPostExecute(testservice);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Testservice testservice) {
        super.onCancelled(testservice);
    }

    public interface Testservice{
        @GET("/api/user/2")
        Call<Object> getTest();
    }

    public static Testservice getApiService(){
        return getInstance(BASE_URL).create(Testservice.class);
    }

    public static Testservice getApiService(String url){
        return getInstance(url).create(Testservice.class);
    }

    public static Retrofit getInstance(String url) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
