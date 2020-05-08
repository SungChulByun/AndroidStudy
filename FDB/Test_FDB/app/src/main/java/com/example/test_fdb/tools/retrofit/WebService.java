package com.example.test_fdb.tools;

import static com.example.test_fdb.Test_FDB.*;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {
	public static final String url = "https://fcm.googleapis.com/fcm/";

	public static Retrofit retrofit = getClient();

	static Retrofit getClient() {
		retrofit = new Retrofit.Builder()
			.baseUrl(url)
			.addConverterFactory(GsonConverterFactory.create())
			.client(stethoInterceptingClient)
			.build();

		return retrofit;
	}
}
