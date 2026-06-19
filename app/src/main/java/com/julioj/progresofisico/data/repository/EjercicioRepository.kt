package com.julioj.progresofisico.data.repository

import com.julioj.progresofisico.data.local.dao.EjercicioDao
import com.julioj.progresofisico.data.model.Ejercicio

class EjercicioRepository(
    private val ejercicioDao: EjercicioDao
) {

    suspend fun insertarEjercicio(
        ejercicio: Ejercicio
    ) {
        ejercicioDao.insertarEjercicio(ejercicio)
    }

    suspend fun obtenerEjercicios(): List<Ejercicio> {
        return ejercicioDao.obtenerEjercicios()
    }

    suspend fun eliminarEjercicio(
        ejercicio: Ejercicio
    ) {
        ejercicioDao.eliminarEjercicio(ejercicio)
    }

    suspend fun actualizarEjercicio(
        ejercicio: Ejercicio
    ) {
        ejercicioDao.actualizarEjercicio(ejercicio)
    }

    suspend fun obtenerEjercicioPorId(
        idEjercicio: Int
    ): Ejercicio? {
        return ejercicioDao.obtenerEjercicioPorId(idEjercicio)
    }

    suspend fun obtenerEjerciciosPorGrupo(
        grupo: String
    ): List<Ejercicio> {
        return ejercicioDao.obtenerEjerciciosPorGrupo(grupo)
    }

    suspend fun buscarEjercicios(
        texto: String
    ): List<Ejercicio> {
        return ejercicioDao.buscarEjercicios(texto)
    }
}