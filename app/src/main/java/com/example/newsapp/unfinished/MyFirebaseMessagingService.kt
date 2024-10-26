package com.example.newsapp.unfinished

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.newsapp.R
import com.example.newsapp.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MyFirebaseMsgService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Log thông tin tin nhắn
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Kiểm tra nếu tin nhắn có phần notification payload
        remoteMessage.notification?.let {
            val title = it.title ?: "Thông báo"
            val body = it.body ?: "Bạn có một thông báo mới"
            sendNotification(title, body)
        }

        // Kiểm tra nếu tin nhắn có phần data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d(TAG, "Sending token to server: $token")
        // Thực hiện logic gửi token đến server của bạn (nếu cần)
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        // Lấy ID của kênh thông báo
        val channelId = getString(R.string.default_notification_channel_id)

        // Xây dựng thông báo
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_forward_to_inbox_24) // Đổi icon phù hợp với ứng dụng của bạn
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)

        // Tạo kênh thông báo cho Android 8.0 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Kênh thông báo mặc định"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        // Kiểm tra nếu thiết bị chạy Android 13 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(0, notificationBuilder.build())
            } else {
                // Nếu không có quyền, gọi đến MainActivity để yêu cầu quyền
                val permissionIntent = Intent(this, MainActivity::class.java)
                permissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(permissionIntent)
            }
        } else {
            // Nếu là Android dưới 13, hiển thị thông báo mà không cần kiểm tra quyền
            notificationManager.notify(0, notificationBuilder.build())
        }
    }
    private fun saveNotificationToSharedPreferences(notification: String) {
        val sharedPreferences = getSharedPreferences("notifications", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Lấy danh sách thông báo hiện tại dưới dạng một chuỗi JSON
        val notificationsListJson = sharedPreferences.getString("notifications_list", "[]")

        // Chuyển chuỗi JSON thành một MutableList
        val notificationsList = mutableListOf<String>().apply {
            addAll(Gson().fromJson(notificationsListJson, Array<String>::class.java).toList())
        }

        // Thêm thông báo mới vào danh sách
        notificationsList.add(notification)

        // Lưu lại danh sách thông báo dưới dạng chuỗi JSON
        val updatedNotificationsJson = Gson().toJson(notificationsList)
        editor.putString("notifications_list", updatedNotificationsJson)
        editor.apply()

        // Log để kiểm tra việc lưu trữ thông báo
        Log.d("FCM", "Updated Notifications List after adding: $notificationsList")
    }




}
