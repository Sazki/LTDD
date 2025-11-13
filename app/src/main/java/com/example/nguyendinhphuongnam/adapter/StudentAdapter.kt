package com.example.nguyendinhphuongnam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nguyendinhphuongnam.databinding.ItemStudentBinding
import com.example.nguyendinhphuongnam.model.SinhVien

class StudentAdapter(
    private val students: MutableList<SinhVien>,
    private val onItemClicked: (SinhVien) -> Unit,
    private val onDeleteClicked: (SinhVien) -> Unit,
    private val onUpdateClicked: (SinhVien) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(student: SinhVien) {
            binding.tvTenSV.text = "Tên SV: ${student.tenSV}"
            binding.tvLop.text = "Lớp: ${student.lop}"
            binding.tvDiemTB.text = "Điểm TB: ${student.diemTB}"

            binding.root.setOnClickListener {
                onItemClicked(student)
            }

            binding.btnEdit.setOnClickListener {
                onUpdateClicked(student)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClicked(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount(): Int = students.size

    fun setData(newStudents: List<SinhVien>) {
        students.clear()
        students.addAll(newStudents)
        notifyDataSetChanged()
    }
}