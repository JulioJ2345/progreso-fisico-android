package com.julioj.progresofisico.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.julioj.progresofisico.data.model.RegistroEntrenamiento

@Dao
interface RegistroEntrenamientoDao {

    @Insert
    suspend fun insertarRegistro(
        registro: RegistroEntrenamiento
    )

    @Query(
        "SELECT * FROM registros_entrenamiento " +
                "ORDER BY substr(fecha, 7, 4) DESC, substr(fecha, 4, 2) DESC, substr(fecha, 1, 2) DESC"
    )
    suspend fun obtenerTodosLosRegistros(): List<RegistroEntrenamiento>

    @Query(
        "SELECT * FROM registros_entrenamiento WHERE idEjercicio = :idEjercicio " +
                "ORDER BY substr(fecha, 7, 4) DESC, substr(fecha, 4, 2) DESC, substr(fecha, 1, 2) DESC"
    )
    suspend fun obtenerRegistrosPorEjercicio(
        idEjercicio: Int
    ): List<RegistroEntrenamiento>

    @Query(
        "SELECT * FROM registros_entrenamiento WHERE idEjercicio = :idEjercicio " +
                "ORDER BY substr(fecha, 7, 4) DESC, substr(fecha, 4, 2) DESC, substr(fecha, 1, 2) DESC LIMIT 1"
    )
    suspend fun obtenerUltimoRegistroPorEjercicio(
        idEjercicio: Int
    ): RegistroEntrenamiento?

    @Delete
    suspend fun eliminarRegistro(
        registro: RegistroEntrenamiento
    )
}