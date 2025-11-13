package com.example.nguyendinhphuongnam.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nguyendinhphuongnam.model.SinhVien

@Dao
interface StudentDao {
    @Query("SELECT * FROM sinhvien")
    fun getAll(): LiveData<List<SinhVien>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: SinhVien): Long

    @Update
    suspend fun update(student: SinhVien): Int

    @Delete
    suspend fun delete(student: SinhVien): Int
}