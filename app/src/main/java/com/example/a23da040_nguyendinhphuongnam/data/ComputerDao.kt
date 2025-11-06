package com.example.a23da040_nguyendinhphuongnam.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.a23da040_nguyendinhphuongnam.model.MayTinh

@Dao
interface ComputerDao {
    @Query("SELECT * FROM maytinh")
    fun getAll(): LiveData<List<MayTinh>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(computer: MayTinh): Long

    @Update
    suspend fun update(computer: MayTinh): Int

    @Delete
    suspend fun delete(computer: MayTinh): Int
}
