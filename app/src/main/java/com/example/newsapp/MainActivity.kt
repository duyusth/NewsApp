package com.example.newsapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.login.LoginActivity
import com.example.newsapp.news.Article
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var  navController: NavController
    private val REQUEST_NOTIFICATION_PERMISSION = 101
    private val REQUEST_CALL_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Kiểm tra và yêu cầu quyền thông báo nếu cần
        checkNotificationPermission()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Thiết lập Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Thiết lập DrawerLayout và NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        // Hiển thị nút mở navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24) // Thay thế bằng icon menu của bạn

        // Xử lý sự kiện khi chọn các item trong Navigation Drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_tin_moi_nhat -> {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.popBackStack(R.id.navigation_home, false) // Điều hướng đến HomeFragment nếu đang ở fragment khác
                }
                R.id.nav_gui_y_kien -> {
                    //sendFeedbackEmail()
                    // gui y kien bang goi dien
                    dialPhoneNumber("5554")
                }
                // Các mục khác...
                R.id.nav_thoat -> {
                    signOutUser()
                }
                R.id.nav_lich_su -> {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.historyFragment)
                }
            }
            // Đóng Navigation Drawer sau khi chọn
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // Nếu đã có quyền, thực hiện cuộc gọi
            startActivity(callIntent)
        } else {
            // Nếu chưa có quyền, yêu cầu quyền từ người dùng
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
        }
    }

//    private fun sendFeedbackEmail() {
//        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
//            data = Uri.parse("mailto:")  // Chỉ định rằng chỉ ứng dụng email mới xử lý được intent này
//            putExtra(Intent.EXTRA_EMAIL, arrayOf("duynd.ba12-062@st.usth.edu.vn"))  // Địa chỉ email nhận
//            putExtra(Intent.EXTRA_SUBJECT, "Phản hồi từ người dùng ứng dụng NewsApp")  // Tiêu đề email
//            putExtra(Intent.EXTRA_TEXT, "Xin vui lòng nhập phản hồi của bạn tại đây...")  // Nội dung mặc định
//        }
//        // Kiểm tra xem có ứng dụng email nào để xử lý intent không
//        if (emailIntent.resolveActivity(packageManager) != null) {
//            startActivity(emailIntent)
//        } else {
//            Toast.makeText(this, "Không có ứng dụng email để gửi phản hồi", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun signOutUser() {
        // Dang xuat khoi firebase
        FirebaseAuth.getInstance().signOut()

        // Quay lai man hinh dang nhap
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // xoa tat ca activity truoc do
        startActivity(intent)
        finish()
    }

    // Xử lý sự kiện mở DrawerLayout khi nhấn nút Menu trên Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        // Đóng DrawerLayout nếu đang mở khi nhấn nút back
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()

        // Kiem tra nguoi dung da dang nhap
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null ){
            // Neu nguoi chx dang nhap thi chuyen toi man hinh dang nhap
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // mấy cái notification thì phải dùng firebase để bắn
    private fun checkNotificationPermission() {
        // Kiểm tra nếu thiết bị đang chạy Android 13 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                // Nếu chưa có quyền, yêu cầu người dùng cấp quyền
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, bạn có thể xử lý logic hiển thị thông báo ở đây
            } else {
                // Quyền bị từ chối, bạn có thể xử lý logic khi không có quyền ở đây
            }
        }
        when (requestCode) {
            REQUEST_CALL_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Quyền được cấp, tiếp tục thực hiện cuộc gọi
                    dialPhoneNumber("0915331999")
                } else {
                    // Quyền bị từ chối
                    Toast.makeText(
                        this,
                        "Quyền thực hiện cuộc gọi đã bị từ chối",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun saveArticleToHistory(article: Article) {
        val sharedPrefs = getSharedPreferences("AppHistory", MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        // Lấy danh sách lịch sử hiện tại
        val historySet = sharedPrefs.getStringSet("history", mutableSetOf()) ?: mutableSetOf()

        // Thêm bài báo vào danh sách (sử dụng URL hoặc tiêu đề để lưu)
        historySet.add("${article.title}|${article.url}") // Tách tiêu đề và URL bằng dấu |

        // Lưu lại lịch sử vào SharedPreferences
        editor.putStringSet("history", historySet)
        editor.apply()
    }



}