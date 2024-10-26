package com.example.newsapp.news

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val webView: WebView = findViewById(R.id.webView)
        // Nhận dữ liệu từ Intent
        val url = intent.getStringExtra("url")

        // Cau hình webview de mo url trong webview thay vi trinh duyet mac dinh
        webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true;
        // Tai url bai bao vao web view
        if(url != null){
            webView.loadUrl(url);
        }
    }
}
