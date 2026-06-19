package com.julioj.progresofisico.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.julioj.progresofisico.data.model.ActividadDiaria

@Dao
interface ActividadDiariaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarActividad(actividad: ActividadDiaria)

    @Query(
        "SELECT * FROM actividades_diarias WHERE fecha = :fecha"
    )
    suspend fun obtenerActividadPorFecha(
        fecha: String
    ): ActividadDiaria?

    @Query(
        "SELECT * FROM actividades_diarias ORDER BY fecha DESC"
    )
    suspend fun obtenerTodasLasActividades(): List<ActividadDiaria>

    @Delete
    suspend fun eliminarActividad(
        actividad: ActividadDiaria
    )
}