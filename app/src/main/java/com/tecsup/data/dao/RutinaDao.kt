package com.tecsup.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tecsup.data.entity.Rutina


@Dao
interface RutinaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(rutina: Rutina): Long

    @Update
    suspend fun actualizar(rutina: Rutina)

    @Delete
    suspend fun eliminar(rutina: Rutina)

    @Query("SELECT * FROM Rutina WHERE usuario_id = :usuarioId ORDER BY fecha DESC")
    suspend fun listarPorUsuario(usuarioId: Int): List<Rutina>

    @Query("SELECT * FROM Rutina WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Rutina?
}