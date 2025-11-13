package com.example.nguyendinhphuongnam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.nguyendinhphuongnam.ManageStudentsFragment
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_perfect_number -> replaceFragment(PerfectNumberFragment()) // Kiểm tra số hoàn hảo
                R.id.nav_solve_equation -> replaceFragment(SolveEquationFragment()) // Giải phương trình bậc 1
                R.id.nav_manage_students -> replaceFragment(ManageStudentsFragment()) // Quản lý máy tính
            }
            true
        }
        bottomNav.selectedItemId = R.id.nav_perfect_number // Mặc định hiển thị trang đầu tiên
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
