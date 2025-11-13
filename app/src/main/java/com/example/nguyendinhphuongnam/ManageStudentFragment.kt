package com.example.nguyendinhphuongnam

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nguyendinhphuongnam.adapter.StudentAdapter
import com.example.nguyendinhphuongnam.databinding.DialogStudentBinding
import com.example.nguyendinhphuongnam.databinding.FragmentManageStudentsBinding
import com.example.nguyendinhphuongnam.model.SinhVien
import com.example.nguyendinhphuongnam.viewmodel.StudentViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView

class ManageStudentsFragment : Fragment() {
    private var _binding: FragmentManageStudentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentAdapter: StudentAdapter
    private val viewModel: StudentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStudents.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.fabAddStudent.hide() // Ẩn FAB khi cuộn xuống
                } else if (dy < 0) {
                    binding.fabAddStudent.show() // Hiện FAB khi cuộn lên
                }
            }
        })

        studentAdapter = StudentAdapter(
            students = mutableListOf(),
            onItemClicked = { student ->
                Toast.makeText(requireContext(), "Chọn: ${student.tenSV}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClicked = { student ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Xóa sinh viên")
                    .setMessage("Bạn có chắc muốn xóa ${student.tenSV}?")
                    .setPositiveButton("Xóa") { _, _ ->
                        viewModel.delete(student)
                        Snackbar.make(binding.root, "Đã xóa: ${student.tenSV}", Snackbar.LENGTH_LONG)
                            .setAction("Hoàn tác") {
                                viewModel.insert(student) // Khôi phục nếu hoàn tác
                            }
                            .setAnchorView(binding.fabAddStudent)
                            .show()
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
            },
            onUpdateClicked = { student ->
                showStudentDialog(student, isUpdate = true)
            }
        )

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentAdapter
        }

        binding.fabAddStudent.setOnClickListener {
            it.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(100)
                .withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(100)
                }

            showStudentDialog(null, isUpdate = false)
        }

        viewModel.allStudents.observe(viewLifecycleOwner) { studentList ->
            studentAdapter.setData(studentList)
        }
    }

    private fun showStudentDialog(student: SinhVien?, isUpdate: Boolean) {
        val dialogBinding = DialogStudentBinding.inflate(LayoutInflater.from(requireContext()))

        if (isUpdate && student != null) {
            dialogBinding.etMaSV.setText(student.maSV)
            dialogBinding.etMaSV.isEnabled = false
            dialogBinding.etTenSV.setText(student.tenSV)
            dialogBinding.etLop.setText(student.lop)
            dialogBinding.etDiemTB.setText(student.diemTB.toString())
        }

        val dialogTitle = if (isUpdate) "Cập nhật sinh viên" else "Thêm sinh viên"
        AlertDialog.Builder(requireContext())
            .setTitle(dialogTitle)
            .setView(dialogBinding.root)
            .setPositiveButton(if (isUpdate) "Cập nhật" else "Thêm") { dialog, _ ->
                val maSV = dialogBinding.etMaSV.text.toString().trim()
                val tenSV = dialogBinding.etTenSV.text.toString().trim()
                val lop = dialogBinding.etLop.text.toString().trim()
                val diemTB = dialogBinding.etDiemTB.text.toString().toDoubleOrNull() ?: 0.0

                if (maSV.isEmpty() || tenSV.isEmpty() || lop.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                } else {
                    if (isUpdate && student != null) {
                        val updatedStudent = student.copy(
                            tenSV = tenSV,
                            lop = lop,
                            diemTB = diemTB
                        )
                        viewModel.update(updatedStudent)
                        Snackbar.make(binding.root, "Đã cập nhật: ${updatedStudent.tenSV}", Snackbar.LENGTH_LONG)
                            .setAnchorView(binding.fabAddStudent)
                            .show()
                    } else {
                        val newStudent = SinhVien(maSV, tenSV, lop, diemTB)
                        viewModel.insert(newStudent)
                        Snackbar.make(binding.root, "Đã thêm: ${newStudent.tenSV}", Snackbar.LENGTH_LONG)
                            .setAnchorView(binding.fabAddStudent)
                            .show()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Hủy") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}