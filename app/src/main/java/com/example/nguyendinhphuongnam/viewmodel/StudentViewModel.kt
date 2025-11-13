package com.example.nguyendinhphuongnam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.nguyendinhphuongnam.data.AppDatabase
import com.example.nguyendinhphuongnam.data.StudentRepository
import com.example.nguyendinhphuongnam.model.SinhVien
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository
    val allStudents: LiveData<List<SinhVien>>

    init {
        val studentDao = AppDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
        allStudents = repository.allStudents
    }

    fun insert(student: SinhVien) = viewModelScope.launch {
        repository.insert(student)
    }

    fun update(student: SinhVien) = viewModelScope.launch {
        repository.update(student)
    }

    fun delete(student: SinhVien) = viewModelScope.launch {
        repository.delete(student)
    }
}