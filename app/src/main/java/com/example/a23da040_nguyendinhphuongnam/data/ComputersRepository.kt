package com.example.a23da040_nguyendinhphuongnam.data

import androidx.lifecycle.LiveData
import com.example.a23da040_nguyendinhphuongnam.model.MayTinh

class ComputersRepository(private val computerDao: ComputerDao) {

    val allComputers: LiveData<List<MayTinh>> = computerDao.getAll()

    suspend fun insert(computer: MayTinh) {
        computerDao.insert(computer)
    }

    suspend fun update(computer: MayTinh) {
        computerDao.update(computer)
    }

    suspend fun delete(computer: MayTinh) {
        computerDao.delete(computer)
    }
}
