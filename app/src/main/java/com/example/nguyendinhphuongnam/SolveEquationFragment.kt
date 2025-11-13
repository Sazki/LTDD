package com.example.nguyendinhphuongnam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.nguyendinhphuongnam.viewmodel.SolveEquationViewModel

class SolveEquationFragment : Fragment() {

    private lateinit var edtA: EditText
    private lateinit var edtB: EditText
    private lateinit var btnSolve: Button
    private lateinit var tvResult: TextView

    private val viewModel: SolveEquationViewModel by viewModels {
        SavedStateViewModelFactory(requireActivity().application, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_solve_equation, container, false)

        edtA = view.findViewById(R.id.edt_a)
        edtB = view.findViewById(R.id.edt_b)
        btnSolve = view.findViewById(R.id.btn_solve)
        tvResult = view.findViewById(R.id.tv_result)

        btnSolve.setOnClickListener {
            val a = edtA.text.toString().toDoubleOrNull()
            val b = edtB.text.toString().toDoubleOrNull()

            if (a != null && b != null) {
                viewModel.solveEquation(a, b)
            } else {
                tvResult.text = "Vui lòng nhập hệ số hợp lệ."
            }
        }

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            tvResult.text = result
        })

        return view
    }
}
