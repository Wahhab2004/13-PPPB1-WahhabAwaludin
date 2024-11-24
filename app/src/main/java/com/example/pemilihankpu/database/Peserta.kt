package com.example.pemilihankpu.database


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "peserta")
data class Peserta (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int=0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "NIK")
    val NIK: String,
    @ColumnInfo(name = "alamat")
    val alamat: String,
    @ColumnInfo(name = "gender")
    val gender: String

)