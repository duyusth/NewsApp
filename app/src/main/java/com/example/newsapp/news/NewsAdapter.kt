package com.example.newsapp.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R

class NewsAdapter(private val context: Context, private var articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return NewsViewHolder(view);
    }

    override fun getItemCount(): Int = articles.size


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text = article.description
        holder.source.text = article.source.name

        // Tai hinh anh tu Url su dung gile
        if (article.urlToImage != null) {
            Glide.with(context).load(article.urlToImage).into(holder.imageView)
        } else {
            // Không làm gì nếu urlToImage là null
            holder.imageView.setImageResource(0) // Hoặc set một giá trị mặc định khác
        }


        // Xu ly khi bam vao item cua Recyclerview
        holder.itemView.setOnClickListener {
            // Tao intent de chuyen sang Detail Activity
            val intent = Intent(context, DetailActivity::class.java)
            // Truyen du lieu bai bao sang Detail Activity
            intent.putExtra("url", article.url)
            context.startActivity(intent)

        }
    }
    class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.newsTitle);
        val description: TextView = itemView.findViewById(R.id.newsDescription);
        val source: TextView = itemView.findViewById(R.id.newsSource);
        val imageView: ImageView = itemView.findViewById(R.id.newsImage)
    }
}