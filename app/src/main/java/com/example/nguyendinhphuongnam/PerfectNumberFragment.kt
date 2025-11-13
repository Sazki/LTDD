package com.example.nguyendinhphuongnam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nguyendinhphuongnam.databinding.FragmentPerfectNumberBinding
import com.example.nguyendinhphuongnam.viewmodel.PerfectNumberViewModel

class PerfectNumberFragment : Fragment() {

    private var _binding: FragmentPerfectNumberBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PerfectNumberViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfectNumberBinding.inflate(inflater, container, false)

        // Quan sát dữ liệu từ ViewModel
        viewModel.result.observe(viewLifecycleOwner) { message ->
            binding.tvResult.text = message
        }

        binding.btnCheckPerfect.setOnClickListener {
            val number = binding.edtNumber.text.toString().toIntOrNull()
            viewModel.checkPerfectNumber(number)  // Gọi ViewModel để kiểm tra số hoàn hảo
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
