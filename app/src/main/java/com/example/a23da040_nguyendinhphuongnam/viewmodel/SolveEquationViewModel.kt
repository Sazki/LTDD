package com.example.a23da040_nguyendinhphuongnam.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SolveEquationViewModel(private val state: SavedStateHandle) : ViewModel() {

    val result = state.getLiveData<String>("result", "Chưa có kết quả")

    fun solveEquation(a: Double, b: Double) {
        viewModelScope.launch {
            delay(1000L) // Giả lập quá trình xử lý

            val solution = when {
                a == 0.0 && b == 0.0 -> "Phương trình vô số nghiệm"
                a == 0.0 -> "Phương trình vô nghiệm"
                else -> "Nghiệm: x = ${-b / a}"
            }

            state["result"] = solution
        }
    }
}
