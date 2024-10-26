package com.example.newsapp.video

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VideoApiService {
    @GET("videos/popular")
    fun getPopularVideos(
        @Header("Authorization") apiKey: String,
        @Query("per_page") perPage: Int
    ): Call<VideoResponse>
}
