package com.example.pemilihankpu.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemilihankpu.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLogin.setOnClickListener {
                val username = editUsername.text.toString()
                val password = editPassword.text.toString()

                // Cek apakah input kosong
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Mohon isi semua data",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Cek apakah username dan password valid
                if (isValidUsernamePassword()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Username atau password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // Fungsi untuk validasi username dan password
    private fun isValidUsernamePassword(): Boolean {
        val username = "wahhab"
        val password = "aja"
        val inputUsername = binding.editUsername.text.toString()
        val inputPassword = binding.editPassword.text.toString()
        return username == inputUsername && password == inputPassword
    }
}
