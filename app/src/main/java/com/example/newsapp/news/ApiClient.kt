package com.example.newsapp.news

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val Base_URL = "https://newsapi.org/v2/"
    private var retrofit: Retrofit? = null

    // Hàm này trả về một instance của Retrofit. Nó sẽ kiểm tra xem Retrofit đã được khởi tạo chưa
    fun getClient(): Retrofit{
        if(retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit!!
    }
}

// object là singleton object trong Kotlin, nghĩa là nó sẽ chỉ có một instance duy nhất trong suốt thời gian chạy của ứng dụng.
// ApiClient chịu trách nhiệm quản lý và cung cấp instance của Retrofit để thực hiện các yêu cầu API.

// Base_URL chứa URL gốc của API bạn sẽ sử dụng (trong trường hợp này là "https://newsapi.org/v2/"), bạn sẽ thêm các endpoint khác
// vào nó khi gọi API (ví dụ như "/top-headlines" hay "/everything")

// Biến lưu trữ instance của Retrofit. Biến này bắt đầu với giá trị null để kiểm tra xem Retrofit đã được khởi tạo hay chưa.
// Retrofit là thư viện được dùng để thực hiện các yêu cầu HTTP và ánh xạ phản hồi từ JSON sang các đối tượng Kotlin.

//Retrofit.Builder():   build và cấu hình cho retrofit là đc
//Retrofit.Builder(): Tạo một instance của Retrofit.
//.baseUrl(Base_URL): Thiết lập URL gốc cho các yêu cầu HTTP.
//.addConverterFactory(GsonConverterFactory.create()): Sử dụng GsonConverterFactory để chuyển đổi dữ liệu JSON từ phản hồi API thành các đối tượng Kotln.

//Dấu !! có nghĩa là bạn chắc chắn retrofit sẽ không null tại thời điểm này.