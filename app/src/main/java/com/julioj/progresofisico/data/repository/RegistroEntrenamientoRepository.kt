package com.julioj.progresofisico.data.repository

import com.julioj.progresofisico.data.local.dao.RegistroEntrenamientoDao
import com.julioj.progresofisico.data.model.RegistroEntrenamiento

class RegistroEntrenamientoRepository(
    private val registroDao: RegistroEntrenamientoDao
) {

    suspend fun insertarRegistro(
        registro: RegistroEntrenamiento
    ) {
        registroDao.insertarRegistro(registro)
    }

    suspend fun obtenerTodosLosRegistros(): List<RegistroEntrenamiento> {
        return registroDao.obtenerTodosLosRegistros()
    }

    suspend fun obtenerRegistrosPorEjercicio(
        idEjercicio: Int
    ): List<RegistroEntrenamiento> {
        return registroDao.obtenerRegistrosPorEjercicio(idEjercicio)
    }

    suspend fun obtenerUltimoRegistroPorEjercicio(
        idEjercicio: Int
    ): RegistroEntrenamiento? {
        return registroDao.obtenerUltimoRegistroPorEjercicio(idEjercicio)
    }

    suspend fun eliminarRegistro(
        registro: RegistroEntrenamiento
    ) {
        registroDao.eliminarRegistro(registro)
    }

    suspend fun actualizarRegistro(
        registro: RegistroEntrenamiento
    ) {
        registroDao.actualizarRegistro(registro)
    }
}