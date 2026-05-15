package com.tecsup.data.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey


@Entity

data class Usuario(
    @PrimaryKey(autoGenerate = true)  val  id: Int = 0,
    @ColumnInfo(name = "nombre_suario") val nombreUsuario: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "nombre_completo") val nombreCompleto: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "edad") val edad: Int,
    @ColumnInfo(name = "fecha_registro") val fechaRegistro: String

)