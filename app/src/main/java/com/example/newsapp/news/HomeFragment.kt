package com.example.newsapp.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    // Khai báo SwipeRefreshLayout để hỗ trợ tính năng kéo để làm mới
    // Adapter để kết nối dữ liệu bài báo với RecyclerView
    // Khai báo RecyclerView để hiển thị danh sách bài báo
    // Danh sách bài báo, khởi tạo ban đầu là danh sách rỗng
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private var articles: List<Article> = listOf()

    // Tạo và nạp layout cho Fragment từ XML
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // Thiết lập các thành phần giao diện sau khi view đã được tạo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        swipeRefreshLayout.setOnRefreshListener {
            fetchNews()
        }

        // Gọi hàm fetchNews() để tải dữ liệu ngay khi Fragment được tạo
        fetchNews()
    }

    // Hàm gọi API để lấy danh sách bài báo từ server
    private fun fetchNews() {
        // Hiển thị vòng xoay tải dữ liệu khi bắt đầu làm mới
        swipeRefreshLayout.isRefreshing = true
        // Tạo đối tượng Retrofit để gọi API
        val apiService = ApiClient.getClient().create(NewsApiService::class.java)
        // Gọi API để lấy các bài báo hàng đầu với API key và quốc gia "us"
        val call = apiService.getTopHeadlines("d2b1e0d9b6794535ab91504cd3b6dcb4", "us")

        // Thực thi cuộc gọi API bất đồng bộ
        // Xử lý khi nhận được phản hồi từ API

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // Cập nhật danh sách bài báo từ phản hồi
                    articles = response.body()!!.articles
                    // Tạo adapter mới với danh sách bài báo và gán cho RecyclerView
                    newsAdapter = NewsAdapter(requireContext(), articles)
                    recyclerView.adapter = newsAdapter
                } else {
                    Toast.makeText(context, "Failed to load news", Toast.LENGTH_SHORT).show()
                }
                swipeRefreshLayout.isRefreshing = false
            }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}


// 1. Khai báo lớp HomeFragment:
// swipeRefreshLayout: Đối tượng này hỗ trợ tính năng kéo để làm mới dữ liệu (Swipe to refresh).
// onCreateView: Khởi tạo giao diện cho Fragment.
// 3. onViewCreated: Khởi tạo các thành phần giao diện và cài đặt sự kiện.
// swipeRefreshLayout: Được gán với SwipeRefreshLayout từ giao diện XML (R.id.swipeRefreshLayout), và được sử dụng để hiển thị vòng tròn tải dữ liệu khi làm mới.
// recyclerView: Liên kết với RecyclerView trong giao diện (R.id.recyclerView), sau đó thiết lập layout theo dạng danh sách dọc với LinearLayoutManager.
// Sự kiện SwipeRefreshLayout: Khi người dùng kéo xuống để làm mới, sự kiện này sẽ được kích hoạt và gọi hàm fetchNews() để tải dữ liệu mới.
// Gọi fetchNews(): Lần đầu tiên khi Fragment được khởi tạo, phương thức này sẽ được gọi để tải dữ liệu ngay lập tức.
// 4.fetchNews(): Phương thức để gọi API và lấy danh sách bài báo.
// ApiClient.getClient(): Tạo một instance của Retrofit để gọi API.
// create(NewsApiService::class.java): Tạo một instance của NewsApiService, trong đó định nghĩa các phương thức gọi API.
// getTopHeadlines: Gọi API với apiKey và country (ví dụ: "us" cho Mỹ) để lấy danh sách các bài báo từ NewsAPI.
// swipeRefreshLayout.isRefreshing = false: Sau khi hoàn tất tải dữ liệu (thành công hoặc thất bại), tắt vòng quay của SwipeRefresh để cho người dùng biết rằng quá trình làm mới đã hoàn tất.