package com.julioj.progresofisico.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import com.julioj.progresofisico.data.model.Ejercicio

@Dao
interface EjercicioDao {

    @Insert
    suspend fun insertarEjercicio(ejercicio: Ejercicio)

    @Query("SELECT * FROM ejercicios ORDER BY nombre ASC")
    suspend fun obtenerEjercicios(): List<Ejercicio>

    @Delete
    suspend fun eliminarEjercicio(
        ejercicio: Ejercicio
    )

    @Update
    suspend fun actualizarEjercicio(
        ejercicio: Ejercicio
    )

    @Query("SELECT * FROM ejercicios WHERE id = :idEjercicio")
    suspend fun obtenerEjercicioPorId(
        idEjercicio: Int
    ): Ejercicio?

    @Query(
        "SELECT * FROM ejercicios WHERE grupoMuscular = :grupo ORDER BY nombre ASC"
    )
    suspend fun obtenerEjerciciosPorGrupo(
        grupo: String
    ): List<Ejercicio>

    @Query(
        "SELECT * FROM ejercicios WHERE nombre LIKE '%' || :texto || '%' ORDER BY nombre ASC"
    )
    suspend fun buscarEjercicios(
        texto: String
    ): List<Ejercicio>
}