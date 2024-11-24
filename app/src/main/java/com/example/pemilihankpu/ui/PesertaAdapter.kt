package com.example.pemilihankpu.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pemilihankpu.R
import com.example.pemilihankpu.database.Peserta
import com.example.pemilihankpu.database.PesertaDao
import com.example.pemilihankpu.database.PesertaRoomDatabase
import com.example.pemilihankpu.databinding.ItemPesertaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Files.delete


class PesertaAdapter(
    private val context: Context,
    private val listPeserta: MutableList<Peserta> // Gunakan MutableList agar bisa dimodifikasi
) : RecyclerView.Adapter<PesertaAdapter.PesertaViewHolder>() {

    private val db = PesertaRoomDatabase.getDatabase(context) // Inisialisasi Room Database
    private val pesertaDao = db.pesertaDao() // Akses DAO

    inner class PesertaViewHolder(val binding: ItemPesertaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesertaViewHolder {
        val binding = ItemPesertaBinding.inflate(LayoutInflater.from(context), parent, false)
        return PesertaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PesertaViewHolder, position: Int) {
        val peserta = listPeserta[position]

        // Mengakses elemen menggunakan binding
        holder.binding.editUsername.text = peserta.username

        // Menghapus data peserta
        holder.binding.deleteParticipant.setOnClickListener {
            deleteItem(peserta, position)
        }

        // Navigasi ke Detail Activity
        holder.binding.hideParticipant.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EXTRA_ID", peserta.id) // Kirim ID peserta ke DetailActivity
            }
            context.startActivity(intent)
        }

        // Mengakses editParticipant menggunakan binding
        holder.binding.editParticipant.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java).apply {
                putExtra("EXTRA_ID", peserta.id) // Kirim ID peserta ke EditActivity
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listPeserta.size

    private fun deleteItem(peserta: Peserta, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            // Menghapus data peserta dari database
            pesertaDao.delete(peserta)
            withContext(Dispatchers.Main) {
                // Menghapus data peserta dari list dan memperbarui RecyclerView
                listPeserta.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}



