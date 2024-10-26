package com.example.newsapp.video

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VideoApiClient {
    private const val BASE_URL = "https://api.pexels.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: VideoApiService by lazy {
        retrofit.create(VideoApiService::class.java)
    }
}
