package com.example.newsapp.news

// Ví dụ trong Json có 1 phần trả về kiểu này
//"source": {
//    "id": null,
//    "name": "Investor's Business Daily"
//}

// Source chứa các thuộc tính cần thiết bao gồm nguồn bài báo
data class Source(
    val id: String?,
    val name: String
)
