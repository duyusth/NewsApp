package com.example.newsapp.unfinished

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.google.gson.Gson

class NotificationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter
    private var notifications: MutableList<String> = mutableListOf()

    // BroadcastReceiver để lắng nghe thông báo mới
    // BroadcastReceiver để lắng nghe thông báo mới
    private val notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("FCM", "Broadcast received: NEW_NOTIFICATION")
            loadNotifications() // Tải lại thông báo khi có thông báo mới
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        // Thiết lập RecyclerView
        recyclerView = view.findViewById(R.id.notificationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Thiết lập adapter cho RecyclerView
        adapter = NotificationAdapter(notifications)
        recyclerView.adapter = adapter
        Log.d("FCM", "RecyclerView Adapter set with ${notifications.size} notifications")

        // Tải danh sách thông báo từ SharedPreferences và cập nhật giao diện
        loadNotifications()

        return view
    }

    override fun onStart() {
        super.onStart()
        // Đăng ký BroadcastReceiver với kiểm tra phiên bản API
        val intentFilter = IntentFilter("com.example.newsapp.NEW_NOTIFICATION")

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // API 26 trở lên (Android 8.0+), sử dụng cờ RECEIVER_NOT_EXPORTED
            requireContext().registerReceiver(notificationReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            // API thấp hơn 26, không sử dụng cờ
            requireContext().registerReceiver(notificationReceiver, intentFilter)
        }
    }



    override fun onStop() {
        super.onStop()
        // Hủy đăng ký BroadcastReceiver khi không cần thiết nữa
        requireContext().unregisterReceiver(notificationReceiver)
    }

    private fun loadNotifications() {
        // Lấy danh sách thông báo từ SharedPreferences dưới dạng chuỗi JSON
        val sharedPreferences = requireContext().getSharedPreferences("notifications", Context.MODE_PRIVATE)
        val notificationsListJson = sharedPreferences.getString("notifications_list", "[]")

        // Chuyển chuỗi JSON thành danh sách MutableList
        notifications.clear()
        notifications.addAll(Gson().fromJson(notificationsListJson, Array<String>::class.java).toList())

        // Log để kiểm tra dữ liệu được tải
        Log.d("FCM", "Loaded Notifications List: $notifications")

        // Thông báo cho adapter rằng dữ liệu đã thay đổi
        adapter.notifyDataSetChanged()
    }



}
