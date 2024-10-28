package com.example.newsapp.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.newsapp.R

class GameFragment : Fragment() {

    private lateinit var gameWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        gameWebView = view.findViewById(R.id.gameWebView)

        // Cấu hình WebView
        val webSettings = gameWebView.settings
        webSettings.javaScriptEnabled = true // Kích hoạt JavaScript nếu trang web yêu cầu

        // Tải URL trò chơi
        gameWebView.loadUrl("https://html5.gamedistribution.com/563a3f252c6e4ef8a7fa4a59b9f47c5c/?gd_sdk_referrer_url=https://www.example.com/games/{game-path}")

        // Đảm bảo điều hướng xảy ra bên trong WebView
        gameWebView.webViewClient = WebViewClient()
        webSettings.loadWithOverviewMode = true // Điều chỉnh trang web sao cho vừa với màn hình
        webSettings.useWideViewPort = true // Cho phép WebView mở rộng theo chiều rộng của trang
        webSettings.setSupportZoom(true) // Hỗ trợ tính năng zoom cho WebView
        webSettings.builtInZoomControls = true // Hiển thị nút điều khiển zoom (phóng to/thu nhỏ)

        return view
    }
}
