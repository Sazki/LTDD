package com.example.nguyendinhphuongnam.model



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sinhvien")
data class Lop(
    @PrimaryKey val maSV: String,
    val tenLop: String
)
