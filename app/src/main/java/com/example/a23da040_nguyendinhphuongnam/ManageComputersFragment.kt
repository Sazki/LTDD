package com.example.a23da040_nguyendinhphuongnam

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a23da040_nguyendinhphuongnam.adapter.ComputersAdapter
import com.example.a23da040_nguyendinhphuongnam.databinding.DialogComputerBinding
import com.example.a23da040_nguyendinhphuongnam.databinding.FragmentManageComputersBinding
import com.example.a23da040_nguyendinhphuongnam.model.MayTinh
import com.example.a23da040_nguyendinhphuongnam.viewmodel.ComputersViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView

class ManageComputersFragment : Fragment() {
    private var _binding: FragmentManageComputersBinding? = null
    private val binding get() = _binding!!

    private lateinit var computersAdapter: ComputersAdapter
    private val viewModel: ComputersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageComputersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvComputers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.fabAddComputer.hide() // Ẩn FAB khi cuộn xuống
                } else if (dy < 0) {
                    binding.fabAddComputer.show() // Hiện FAB khi cuộn lên
                }
            }
        })

        computersAdapter = ComputersAdapter(
            computers = mutableListOf(),
            onItemClicked = { computer ->
                Toast.makeText(requireContext(), "Chọn: ${computer.tenMay}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClicked = { computer ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Xóa máy tính")
                    .setMessage("Bạn có chắc muốn xóa ${computer.tenMay}?")
                    .setPositiveButton("Xóa") { _, _ ->
                        viewModel.delete(computer)
                        Snackbar.make(binding.root, "Đã xóa: ${computer.tenMay}", Snackbar.LENGTH_LONG)
                            .setAction("Hoàn tác") {
                                viewModel.insert(computer) // Khôi phục nếu hoàn tác
                            }
                            .setAnchorView(binding.fabAddComputer)
                            .show()
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
            },
            onUpdateClicked = { computer ->
                showComputerDialog(computer, isUpdate = true)
            }
        )

        binding.rvComputers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = computersAdapter
        }

        binding.fabAddComputer.setOnClickListener {
            it.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(100)
                .withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(100)
                }

            showComputerDialog(null, isUpdate = false)
        }

        viewModel.allComputers.observe(viewLifecycleOwner) { computerList ->
            computersAdapter.setData(computerList)
        }
    }

    private fun showComputerDialog(computer: MayTinh?, isUpdate: Boolean) {
        val dialogBinding = DialogComputerBinding.inflate(LayoutInflater.from(requireContext()))

        if (isUpdate && computer != null) {
            dialogBinding.etMaMayTinh.setText(computer.maMay)
            dialogBinding.etMaMayTinh.isEnabled = false
            dialogBinding.etTenMayTinh.setText(computer.tenMay)
            dialogBinding.etLoaiMayTinh.setText(computer.loaiMay)
            dialogBinding.etSoLuong.setText(computer.soLuong.toString())
            dialogBinding.etDonGia.setText(computer.donGia.toString())
        }

        val dialogTitle = if (isUpdate) "Cập nhật máy tính" else "Thêm máy tính"
        AlertDialog.Builder(requireContext())
            .setTitle(dialogTitle)
            .setView(dialogBinding.root)
            .setPositiveButton(if (isUpdate) "Cập nhật" else "Thêm") { dialog, _ ->
                val maMay = dialogBinding.etMaMayTinh.text.toString().trim()
                val tenMay = dialogBinding.etTenMayTinh.text.toString().trim()
                val loaiMay = dialogBinding.etLoaiMayTinh.text.toString().trim()
                val soLuong = dialogBinding.etSoLuong.text.toString().toIntOrNull() ?: 0
                val donGia = dialogBinding.etDonGia.text.toString().toDoubleOrNull() ?: 0.0

                if (maMay.isEmpty() || tenMay.isEmpty() || loaiMay.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                } else {
                    if (isUpdate && computer != null) {
                        val updatedComputer = computer.copy(
                            tenMay = tenMay,
                            loaiMay = loaiMay,
                            soLuong = soLuong,
                            donGia = donGia
                        )
                        viewModel.update(updatedComputer)
                        Snackbar.make(binding.root, "Đã cập nhật: ${updatedComputer.tenMay}", Snackbar.LENGTH_LONG)
                            .setAnchorView(binding.fabAddComputer)
                            .show()
                    } else {
                        val newComputer = MayTinh(maMay, tenMay, loaiMay, soLuong, donGia)
                        viewModel.insert(newComputer)
                        Snackbar.make(binding.root, "Đã thêm: ${newComputer.tenMay}", Snackbar.LENGTH_LONG)
                            .setAnchorView(binding.fabAddComputer)
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
