package com.example.a23da040_nguyendinhphuongnam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.a23da040_nguyendinhphuongnam.data.AppDatabase
import com.example.a23da040_nguyendinhphuongnam.data.ComputersRepository
import com.example.a23da040_nguyendinhphuongnam.model.MayTinh
import kotlinx.coroutines.launch

class ComputersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ComputersRepository
    val allComputers: LiveData<List<MayTinh>>

    init {
        val computerDao = AppDatabase.getDatabase(application).computerDao()
        repository = ComputersRepository(computerDao)
        allComputers = repository.allComputers
    }

    fun insert(computer: MayTinh) = viewModelScope.launch {
        repository.insert(computer)
    }

    fun update(computer: MayTinh) = viewModelScope.launch {
        repository.update(computer)
    }

    fun delete(computer: MayTinh) = viewModelScope.launch {
        repository.delete(computer)
    }
}
