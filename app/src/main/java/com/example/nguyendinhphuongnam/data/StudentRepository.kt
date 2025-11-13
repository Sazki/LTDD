package com.example.nguyendinhphuongnam.data

import androidx.lifecycle.LiveData
import com.example.nguyendinhphuongnam.model.SinhVien

class StudentRepository(private val studentDao: StudentDao) {

    val allStudents: LiveData<List<SinhVien>> = studentDao.getAll()

    suspend fun insert(student: SinhVien) {
        studentDao.insert(student)
    }

    suspend fun update(student: SinhVien) {
        studentDao.update(student)
    }

    suspend fun delete(student: SinhVien) {
        studentDao.delete(student)
    }
}