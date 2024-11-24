package com.example.pemilihankpu.ui

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemilihankpu.database.Peserta
import com.example.pemilihankpu.database.PesertaDao
import com.example.pemilihankpu.database.PesertaRoomDatabase
import com.example.pemilihankpu.databinding.ActivityCreateBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TambahActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var executorService: ExecutorService
    private lateinit var mPesertaDao: PesertaDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        executorService = Executors.newSingleThreadExecutor()
        val db = PesertaRoomDatabase.getDatabase(this)
        mPesertaDao = db.pesertaDao()

        with(binding) setOnClickListener@{


            btnSimpan.setOnClickListener(View.OnClickListener {
                val username = binding.editUsername.text.toString()
                val nik = binding.editNIK.text.toString()
                val alamat = binding.editAlamat.text.toString()


                val selectedId = binding.genderGroup.checkedRadioButtonId
                if (selectedId == -1) {
                    Toast.makeText(this@TambahActivity, "Pilih gender terlebih dahulu", Toast.LENGTH_SHORT).show()
//                    return@OnClickListener
                }
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val selectedGender = selectedRadioButton.text.toString()


                insert(
                    Peserta(
                        username = username,
                        NIK = nik,
                        gender = selectedGender,
                        alamat = alamat
                    )
                )

                startActivity(Intent(this@TambahActivity, MainActivity::class.java))
                    setEmptyField()


            })

        }

        }


    private fun insert(peserta: Peserta) {
        executorService.execute { mPesertaDao.insert(peserta) }
    }

    private fun setEmptyField() {
        with(binding){

            editUsername.text.clear()
            editNIK.text.clear()
            editAlamat.text.clear()
            genderGroup.clearCheck()
        }
    }
    }
