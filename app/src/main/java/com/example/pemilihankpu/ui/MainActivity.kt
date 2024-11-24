package com.example.pemilihankpu.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemilihankpu.database.Peserta
import com.example.pemilihankpu.database.PesertaDao
import com.example.pemilihankpu.database.PesertaRoomDatabase
import com.example.pemilihankpu.databinding.ActivityMainBinding
import com.example.pemilihankpu.databinding.ItemPesertaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private lateinit var mPesertaDao: PesertaDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = PesertaRoomDatabase.getDatabase(this)
        mPesertaDao = db!!.pesertaDao()!!
        prefManager = PrefManager.getInstance(this)

        with(binding) {
            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }

            btnTambaData.setOnClickListener {
                startActivity(Intent(this@MainActivity, TambahActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getAllPeserta()
    }

    private fun getAllPeserta() {
        mPesertaDao.getAllPeserta().observe(this) { pesertaList ->
            val adapter = PesertaAdapter(this, pesertaList.toMutableList())
            binding.recyclerDataPeserta.adapter = adapter
        }
    }

}
