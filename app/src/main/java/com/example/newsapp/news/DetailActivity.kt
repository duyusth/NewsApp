package com.example.newsapp.news

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nạp giao diện của Activity từ file XML "activity_detail"
        setContentView(R.layout.activity_detail)

        val webView: WebView = findViewById(R.id.webView)

        // Nhận dữ liệu từ Intent (lấy URL bài báo được truyền từ HomeFragment hoặc nơi khác)
        val url = intent.getStringExtra("url")

        // Cấu hình WebView để mở URL bên trong WebView thay vì trình duyệt mặc định
        webView.webViewClient = WebViewClient()

        // Kích hoạt JavaScript (nếu trang web cần JavaScript để hiển thị)
        webView.settings.javaScriptEnabled = true

        // Kiểm tra nếu URL không null, tải URL vào WebView
        if (url != null) {
            webView.loadUrl(url)
        }
    }
}

