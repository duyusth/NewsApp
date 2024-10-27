package com.example.newsapp.news

//Lớp Article chứa các thuộc tính cần thiết để mô tả một bài báo, bao gồm nguồn, tiêu đề,
// mô tả, URL bài báo và URL hình ảnh (nếu có). Lớp này có thể được sử dụng để nhận dữ liệu từ
// API và hiển thị trong ứng dụng của bạn.

data class Article(
    val source: Source,
    val title: String,
    val description:String?,//Dấu ? cho biết có thể giá trị này là null
    val url:String,
    val urlToImage: String?,
)


// Json trả về có thể có nhiều thuộc tính nhưng cần gì thì xài cái đó

//{
//    -"source": {
//    "id": null,
//    "name": "Investor's Business Daily"
//},
//    "author": "Investor's Business Daily",
//    "title": "Dow Jones Futures Rise, Oil Dives; Tesla, Nvidia In Buy Zones With Huge Earnings Due",
//    "description": "Apple, Meta and other tech titans lead an onslaught of news.",
//    "url": "https://www.investors.com/market-trend/stock-market-today/dow-jones-futures-tesla-nvidia-lead-nasdaq-high-apple-meta-microsoft-earnings/",
//    "urlToImage": "https://www.investors.com/wp-content/uploads/2024/09/Stock-MagnifyGlass-AIgen-Adobe.jpg",
//    "publishedAt": "2024-10-27T22:43:27Z",
//    "content": "Dow Jones futures rose modestly Sunday night, along with S&amp;P 500 futures and Nasdaq futures. Crude oil plunged after Israel's \"precise\" airstrikes on Iran.\r\nApple (AAPL), Microsoft (MSFT), Google… [+7453 chars]"
//},