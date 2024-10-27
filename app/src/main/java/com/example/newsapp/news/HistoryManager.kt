package com.example.newsapp.news

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("read_articles_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Lấy lịch sử đã lưu
    fun getHistory(): MutableList<Article> {
        val json = sharedPreferences.getString("history", null)
        val type = object : TypeToken<MutableList<Article>>() {}.type
        return if (json != null) gson.fromJson(json, type) else mutableListOf()
    }

    // Thêm một bài báo vào lịch sử
    fun addToHistory(article: Article) {
        val history = getHistory()
        // Kiểm tra xem bài báo đã tồn tại trong lịch sử chưa
        if (!history.any { it.url == article.url }) {
            history.add(0, article) // Thêm bài báo mới vào đầu danh sách
            saveHistory(history)
        }
    }

    // Lưu lịch sử vào SharedPreferences
    private fun saveHistory(history: MutableList<Article>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(history)
        editor.putString("history", json)
        editor.apply()
    }

    // Xóa lịch sử
    fun clearHistory() {
        sharedPreferences.edit().remove("history").apply()
    }
}
