package com.app.akademikapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.akademikapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        // 1. Aksi ketika tombol Portal Mahasiswa diklik
        binding.btnPortalMahasiswa.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MahasiswaFragment())
                .addToBackStack(null) // Menyimpan riwayat halaman agar bisa ditekan tombol 'Kembali'
                .commit()
        }

        // 2. Aksi ketika tombol Portal Admin diklik
        binding.btnPortalAdmin.setOnClickListener {
            Toast.makeText(requireContext(), "Portal Admin belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}