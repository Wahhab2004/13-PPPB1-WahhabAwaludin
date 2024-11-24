package com.example.pemilihankpu.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemilihankpu.database.Peserta
import com.example.pemilihankpu.database.PesertaDao
import com.example.pemilihankpu.database.PesertaRoomDatabase
import com.example.pemilihankpu.databinding.ActivityEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var pesertaDao: PesertaDao // DAO untuk Room
    private lateinit var executorService: ExecutorService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Room Database
        executorService = Executors.newSingleThreadExecutor()
        val db = PesertaRoomDatabase.getDatabase(this)
        pesertaDao = db.pesertaDao()

        // Ambil ID dari Intent
        val pesertaId = intent.getIntExtra("EXTRA_ID", -1)
        if (pesertaId != -1) {
            fetchDetailPeserta(pesertaId) // Ambil data berdasarkan ID
        }

        with(binding) {
            btnSimpan.setOnClickListener {
                val username = editUsername.text.toString()
                val nik = editNIK.text.toString()
                val alamat = editAlamat.text.toString()
                val selectedId = binding.genderGroup.checkedRadioButtonId

                if (selectedId == -1) {
                    Toast.makeText(this@EditActivity, "Pilih gender terlebih dahulu", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val selectedGender = selectedRadioButton.text.toString()

                if (pesertaId != -1) {
                    update(
                        Peserta(
                            id = pesertaId, // Pastikan ID disertakan
                            username = username,
                            NIK = nik,
                            alamat = alamat,
                            gender = selectedGender
                        )
                    )

                    Toast.makeText(this@EditActivity, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@EditActivity, MainActivity::class.java))
                    finish() // Tutup Activity Edit
                } else {
                    Toast.makeText(this@EditActivity, "ID Peserta tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun fetchDetailPeserta(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val peserta = pesertaDao.getPesertaById(id)
            withContext(Dispatchers.Main) {
                if (peserta != null) {
                    // Tampilkan data di halaman detail
                    binding.editUsername.setText(peserta.username)
                    binding.editNIK.setText(peserta.NIK)
                    binding.editAlamat.setText(peserta.alamat)
                }
            }
        }
    }

    private fun update(peserta: Peserta) {
        executorService.execute { pesertaDao.update(peserta) }
    }


}
