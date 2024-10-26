package com.example.newsapp.news

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val Base_URL = "https://newsapi.org/v2/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit{
        if(retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit!!
    }
}