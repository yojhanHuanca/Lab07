package com.tecsup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tecsup.data.entity.Usuario


@Dao
interface UsuarioDao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(usuario: Usuario): Long

    @Query("SELECT * FROM Usuario WHERE nombre_usuario = :user AND password = :pass LIMIT 1")
    suspend fun buscarPorCredenciales(user: String, pass: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Usuario?

    @Query("SELECT * FROM Usuario WHERE nombre_usuario = :nombreUsuario LIMIT 1")
    suspend fun buscarPorNombreUsuario(nombreUsuario: String): Usuario?

    @Query("SELECT COUNT(*) FROM Rutina WHERE usuario_id = :id")
    suspend fun contarRutinas(id: Int): Int

    @Query("SELECT SUM(peso_kg * series * repeticiones) FROM Rutina WHERE usuario_id = :id")
    suspend fun sumarVolumenTotal(id: Int): Double?

}