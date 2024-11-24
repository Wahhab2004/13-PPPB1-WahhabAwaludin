package com.example.pemilihankpu.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pemilihankpu.database.PesertaDao
import com.example.pemilihankpu.database.PesertaRoomDatabase
import com.example.pemilihankpu.databinding.DetailPesertaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: DetailPesertaBinding
    private lateinit var pesertaDao: PesertaDao // DAO untuk Room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailPesertaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Room Database
        val db = PesertaRoomDatabase.getDatabase(this)
        pesertaDao = db.pesertaDao()

        // Ambil ID dari Intent
        val pesertaId = intent.getIntExtra("EXTRA_ID", -1)
        if (pesertaId != -1) {
            fetchDetailPeserta(pesertaId) // Ambil data berdasarkan ID
        }
    }

    private fun fetchDetailPeserta(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val peserta = pesertaDao.getPesertaById(id)
            withContext(Dispatchers.Main) {
                if (peserta != null) {
                    // Tampilkan data di halaman detail
                    binding.editUsername.text = peserta.username
                    binding.editNIK.text = peserta.NIK
                    binding.editGender.text = peserta.gender
                    binding.editAlamat.text = peserta.alamat
                }
            }
        }
    }
}
