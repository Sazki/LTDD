package com.example.nguyendinhphuongnam

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo views
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        
        // Thiết lập toolbar
        setSupportActionBar(toolbar)
        
        // Thiết lập drawer toggle (icon hamburger)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        
        // Xử lý sự kiện click menu
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_perfect_number -> {
                    replaceFragment(PerfectNumberFragment())
                    toolbar.title = "Số hoàn hảo"
                }
                R.id.nav_solve_equation -> {
                    replaceFragment(SolveEquationFragment())
                    toolbar.title = "Giải phương trình"
                }
                R.id.nav_manage_students -> {
                    replaceFragment(ManageStudentsFragment())
                    toolbar.title = "Quản lý Sinh Viên"
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        
        // Hiển thị fragment mặc định
        if (savedInstanceState == null) {
            replaceFragment(PerfectNumberFragment())
            navigationView.setCheckedItem(R.id.nav_perfect_number)
            toolbar.title = "Số hoàn hảo"
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    
    // Xử lý nút back để đóng drawer thay vì thoát app
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
