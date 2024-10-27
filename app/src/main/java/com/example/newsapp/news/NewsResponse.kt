package com.example.newsapp.news

//Lớp NewsResponse là một data class trong Kotlin, và nó có nhiệm vụ chứa phản
//hồi (response) từ API khi bạn yêu cầu lấy dữ liệu tin tức. Đây là lớp mô hình (model) đại diện
//cho cấu trúc dữ liệu của phản hồi mà API trả về.

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

// status đại diện cho trạng thái của phản hồi từ API, thường sẽ là "ok" nếu yêu cầu API thành công
// totalResults:  tổng số kết quả (số bài báo) mà API có thể trả về.
// articles: List<Article>  một danh sách các bài báo, nơi mỗi phần tử của danh sách là một đối tượng Article.