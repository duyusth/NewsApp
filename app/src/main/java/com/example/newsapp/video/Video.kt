package com.example.newsapp.video

data class Video(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val image: String,
    val duration: Int,
    val video_files: List<VideoFile>
)
