package com.example.newsapp.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClipsFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ClipsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_clips, container, false)
        viewPager = view.findViewById(R.id.viewPager)

        // Gọi API qua VideoApiClient
        VideoApiClient.apiService.getPopularVideos(
            "wbDMdvqW1vrrmwzmQ6AwBgzsz79inUo0AzAnbj2COG7NvoW6si4tePwQ", 10
        ).enqueue(object : Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if (response.isSuccessful) {
                    val videos = response.body()?.videos ?: emptyList()
                    setupViewPager(videos)
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                // Xử lý lỗi API
            }
        })

        return view
    }

    private fun setupViewPager(videos: List<Video>) {
        adapter = ClipsAdapter(videos)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                adapter.stopCurrentPlayingVideo() // Dừng video cũ
                adapter.playVideoAtPosition(position) // Phát video mới
            }
        })
    }
}
