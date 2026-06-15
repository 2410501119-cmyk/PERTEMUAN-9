package com.app.akademikapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.akademikapp.databinding.FragmentTambahMahasiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahMahasiswaFragment : Fragment(R.layout.fragment_tambah_mahasiswa) {

    private var _binding: FragmentTambahMahasiswaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTambahMahasiswaBinding.bind(view)

        // Tombol Kembali
        binding.btnKembaliTambahMahasiswa.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Tombol Simpan Data
        binding.btnSimpanMahasiswa.setOnClickListener {
            val nim = binding.edtNim.text.toString().trim()
            val nama = binding.edtNama.text.toString().trim()
            val prodi = binding.edtProdi.text.toString().trim()
            val semesterText = binding.edtSemester.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()

            // Validasi Input Kosong
            if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || semesterText.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val semester = semesterText.toIntOrNull() ?: 1
            val mhs = Mahasiswa(nim = nim, nama = nama, prodi = prodi, semester = semester, email = email)

            // Mengirim data ke Server Node.js menggunakan Retrofit
            RetrofitClient.instance.tambahMahasiswa(mhs).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Menampilkan Toast sukses sesuai gambar modul
                        Toast.makeText(requireContext(), "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.popBackStack() // Otomatis kembali ke halaman utama
                    } else {
                        Toast.makeText(requireContext(), "Gagal menyimpan data ke server", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Berjaga-jaga jika koneksi ke laptop terputus atau IP salah
                    Toast.makeText(requireContext(), "Eror Jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}