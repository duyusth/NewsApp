package com.example.newsapp.news

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
