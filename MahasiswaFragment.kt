package com.app.akademikapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.akademikapp.databinding.FragmentMahasiswaBinding

class MahasiswaFragment : Fragment(R.layout.fragment_mahasiswa) {

    private var _binding: FragmentMahasiswaBinding? = null
    private val binding get() = _binding!!

    private val menuList = listOf(
        MenuAkademik("Profil Mahasiswa", "Identitas dan data akademik dasar mahasiswa.", android.R.drawable.ic_menu_myplaces),
        MenuAkademik("Jadwal Kuliah", "Daftar jadwal perkuliahan yang sedang aktif.", android.R.drawable.ic_menu_agenda),
        MenuAkademik("Nilai Academic", "Hasil studi dan nilai setiap mata kuliah.", android.R.drawable.ic_menu_info_details),
        MenuAkademik("KRS", "Kelola mata kuliah yang diambil semester ini.", android.R.drawable.ic_menu_edit),
        MenuAkademik("Presensi", "Riwayat kehadiran kegiatan perkuliahan.", android.R.drawable.ic_menu_recent_history),
        MenuAkademik("Tagihan", "Rincian pembayaran dan status tagihan akademik.", android.R.drawable.ic_menu_view)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMahasiswaBinding.bind(view)

        setupLayoutModeSelector()
        applyLayoutMode(MenuLayoutMode.LIST)
        setupActions()
    }

    private fun setupLayoutModeSelector() {
        binding.rgLayoutMode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbList -> applyLayoutMode(MenuLayoutMode.LIST)
                R.id.rbGrid -> applyLayoutMode(MenuLayoutMode.GRID)
                R.id.rbCard -> applyLayoutMode(MenuLayoutMode.CARD)
            }
        }
    }

    private fun applyLayoutMode(mode: MenuLayoutMode) {
        val context = requireContext()
        val adapter = when (mode) {
            MenuLayoutMode.LIST -> {
                binding.rvMenuAkademik.layoutManager = LinearLayoutManager(context)
                MenuAkademikCardAdapter(menuList, isListMode = true) { item -> showToast(item.title) }
            }
            MenuLayoutMode.GRID -> {
                binding.rvMenuAkademik.layoutManager = GridLayoutManager(context, 2)
                MenuAkademikGridAdapter(menuList) { item -> showToast(item.title) }
            }
            MenuLayoutMode.CARD -> {
                binding.rvMenuAkademik.layoutManager = LinearLayoutManager(context)
                MenuAkademikCardAdapter(menuList, isListMode = false) { item -> showToast(item.title) }
            }
        }
        binding.rvMenuAkademik.adapter = adapter
    }

    // Aksi tombol kembali terhubung langsung dengan ID btnKembali di XML
    private fun setupActions() {
        binding.btnKembali.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun showToast(title: String) {
        Toast.makeText(requireContext(), "Membuka $title", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}