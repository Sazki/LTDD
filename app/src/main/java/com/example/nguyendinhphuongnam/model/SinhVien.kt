package com.example.nguyendinhphuongnam.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sinhvien")
data class SinhVien(
    @PrimaryKey val maSV: String,
    val tenSV: String,
    val lop: String,
    val diemTB: Double
)
