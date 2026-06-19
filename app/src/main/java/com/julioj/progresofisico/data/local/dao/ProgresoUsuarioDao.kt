package com.julioj.progresofisico.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.julioj.progresofisico.data.model.ProgresoUsuario

@Dao
interface ProgresoUsuarioDao {

    @Query("SELECT * FROM progreso_usuario WHERE id = 1 LIMIT 1")
    suspend fun obtenerProgreso(): ProgresoUsuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarProgreso(progresoUsuario: ProgresoUsuario)
}