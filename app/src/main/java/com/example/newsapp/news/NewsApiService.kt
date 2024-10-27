package com.example.newsapp.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
    ): Call<NewsResponse>
}

// interface định nghĩa phương thức gọi API, mỗi phương thức tung ứng với một yêu cầu HTTP ( GET, POST, ...)

//@GET("top-headlines"):
//@GET là một annotation của Retrofit, chỉ định rằng phương thức này sẽ thực hiện một yêu cầu HTTP GET.
//"top-headlines" là phần endpoint của URL API, nối thêm vào Base_URL trong ApiClient để thành "https://newsapi.org/v2/top-headlines"

//fun getTopHeadlines(...):Phương thức này được thiết kế để gọi API và trả về các tin tức hàng đầu từ một quốc gia cụ thể, sử dụng API key để xác thực.

//@Query("apiKey") chỉ định rằng giá trị của tham số apiKey sẽ được thêm vào URL dưới dạng query parameter. Ví dụ, nếu apiKey là "your_api_key", thì URL sẽ có dạng ...top-headlines?apiKey=your_api_key.
//apiKey: String: Tham số apiKey là một chuỗi, yêu cầu từ phía API để xác thực người dùng.

//@Query("country") country: String:
//Tương tự như apiKey, tham số country cũng được thêm vào URL dưới dạng query parameter. Ví dụ, nếu country là "us", thì URL sẽ có dạng ...top-headlines?apiKey=your_api_key&country=us.
//country: String: Đây là tham số để chỉ định quốc gia từ đó bạn muốn lấy tin tức hàng đầu (ví dụ: "us" cho Hoa Kỳ, "vn" cho Việt Nam).

//Call<NewsResponse>:
//Phương thức getTopHeadlines trả về một đối tượng Call<NewsResponse>, trong đó:
//Call: Đây là một lớp của Retrofit, đại diện cho yêu cầu API. Nó cho phép bạn thực hiện yêu cầu một cách đồng bộ hoặc không đồng bộ (asynchronous).
//NewsResponse: Đây là kiểu dữ liệu phản hồi từ API, mà bạn đã định nghĩa trước đó. Nó chứa các thông tin như trạng thái phản hồi, tổng số kết quả, và danh sách các bài báo (List<Article>).