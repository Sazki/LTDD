package com.example.a23da040_nguyendinhphuongnam.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maytinh")
data class MayTinh(
    @PrimaryKey val maMay: String,
    val tenMay: String,
    val loaiMay: String,
    val soLuong: Int,
    val donGia: Double
) {
    val thanhTien: Double
        get() = soLuong * donGia
}
