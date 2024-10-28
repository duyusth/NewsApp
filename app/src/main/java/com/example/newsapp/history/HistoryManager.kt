package com.example.newsapp.history

import android.content.Context
import android.content.SharedPreferences
import com.example.newsapp.news.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// sharedPreferences: Được sử dụng để lưu trữ lịch sử. Dữ liệu được lưu dưới dạng chuỗi JSON trong SharedPreferences, với tên file là "read_articles_history".
// gson: Sử dụng thư viện Gson để chuyển đổi danh sách các bài báo (Article) từ đối tượng thành chuỗi JSON và ngược lại.
class HistoryManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("read_articles_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    //  Lấy dữ liệu lịch sử từ SharedPreferences và chuyển đổi nó từ JSON thành danh sách MutableList<Article>.
    //    sharedPreferences.getString("history", null): Lấy chuỗi JSON đã lưu với key là "history". Nếu không có lịch sử, trả về null.
    //    TypeToken<MutableList<Article>>() {}.type: Định nghĩa kiểu dữ liệu là danh sách các bài báo (MutableList<Article>).
    //    gson.fromJson(json, type): Chuyển chuỗi JSON thành danh sách các đối tượng Article.
    //    Nếu không có dữ liệu, trả về danh sách rỗng mutableListOf().

    fun getHistory(): MutableList<Article> {
        val json = sharedPreferences.getString("history", null)
        val type = object : TypeToken<MutableList<Article>>() {}.type
        return if (json != null) gson.fromJson(json, type) else mutableListOf()
    }

    // Mục đích: Thêm bài báo vào lịch sử, nhưng chỉ khi bài báo đó chưa tồn tại trong lịch sử.
    //    getHistory(): Lấy danh sách lịch sử hiện tại.
    //    history.any { it.url == article.url }: Kiểm tra xem bài báo đã có trong lịch sử chưa bằng cách so sánh URL của nó.
    //    Nếu bài báo chưa có trong lịch sử, thêm nó vào đầu danh sách (history.add(0, article)).
    //    Sau đó, gọi hàm saveHistory() để lưu lại lịch sử đã cập nhật.

    fun addToHistory(article: Article) {
        val history = getHistory()
        if (!history.any { it.url == article.url }) {
            history.add(0, article)
            saveHistory(history)
        }
    }

    //  Chuyển danh sách lịch sử thành chuỗi JSON và lưu vào SharedPreferences
    //    sharedPreferences.edit(): Mở một phiên chỉnh sửa SharedPreferences.
    //    gson.toJson(history): Chuyển danh sách lịch sử thành chuỗi JSON.
    //    editor.putString("history", json): Lưu chuỗi JSON vào SharedPreferences với key là "history".
    //    editor.apply(): Áp dụng thay đổi và lưu dữ liệu một cách không đồng bộ.
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
