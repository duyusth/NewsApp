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
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private var articles: List<Article> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext());
        swipeRefreshLayout.setOnRefreshListener {
            fetchNews()
        }
        fetchNews()
    }

    private fun fetchNews(){
        swipeRefreshLayout.isRefreshing = true;
        val apiService = ApiClient.getClient().create(NewsApiService::class.java)
        val call = apiService.getTopHeadlines("d2b1e0d9b6794535ab91504cd3b6dcb4", "us")

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(p0: Call<NewsResponse>, p1: Response<NewsResponse>) {
                if(p1.isSuccessful && p1.body() != null){
                    articles = p1.body()!!.articles
                    newsAdapter = NewsAdapter(requireContext(), articles)
                    recyclerView.adapter = newsAdapter
                } else{
                    Toast.makeText(requireContext(), "Failed to load news", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.isRefreshing = false;
            }

            override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {
                Toast.makeText(requireContext(), "Error: ${p1.message}", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }


}
