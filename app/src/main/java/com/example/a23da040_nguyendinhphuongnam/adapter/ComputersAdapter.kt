package com.example.a23da040_nguyendinhphuongnam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a23da040_nguyendinhphuongnam.databinding.ItemComputerBinding
import com.example.a23da040_nguyendinhphuongnam.model.MayTinh

class ComputersAdapter(
    private val computers: MutableList<MayTinh>,
    private val onItemClicked: (MayTinh) -> Unit,
    private val onDeleteClicked: (MayTinh) -> Unit,
    private val onUpdateClicked: (MayTinh) -> Unit
) : RecyclerView.Adapter<ComputersAdapter.ComputerViewHolder>() {

    inner class ComputerViewHolder(val binding: ItemComputerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(computer: MayTinh) {
            binding.tvTenMay.text = "Tên máy: ${computer.tenMay}"
            binding.tvLoaiMay.text = "Loại máy: ${computer.loaiMay}"
            binding.tvThanhTien.text = "Thành tiền: ${computer.thanhTien} VND"

            binding.root.setOnClickListener {
                onItemClicked(computer)
            }

            binding.btnEdit.setOnClickListener {
                onUpdateClicked(computer)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClicked(computer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComputerViewHolder {
        val binding = ItemComputerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComputerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComputerViewHolder, position: Int) {
        holder.bind(computers[position])
    }

    override fun getItemCount(): Int = computers.size

    fun setData(newComputers: List<MayTinh>) {
        computers.clear()
        computers.addAll(newComputers)
        notifyDataSetChanged()
    }
}
