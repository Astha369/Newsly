package com.example.newsly;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<NewsModel> getAllMews(@Url String url);

    @GET
    Call<NewsModel> getNewsByCategory(@Url String url);



}
