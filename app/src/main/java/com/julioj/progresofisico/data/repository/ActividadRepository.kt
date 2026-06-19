package com.julioj.progresofisico.data.repository

import com.julioj.progresofisico.data.local.dao.ActividadDiariaDao
import com.julioj.progresofisico.data.model.ActividadDiaria

class ActividadRepository(
    private val actividadDao: ActividadDiariaDao
) {

    suspend fun guardarActividad(
        actividad: ActividadDiaria
    ) {
        actividadDao.guardarActividad(actividad)
    }

    suspend fun obtenerActividadPorFecha(
        fecha: String
    ): ActividadDiaria? {
        return actividadDao.obtenerActividadPorFecha(fecha)
    }

    suspend fun obtenerTodasLasActividades(): List<ActividadDiaria> {
        return actividadDao.obtenerTodasLasActividades()
    }

    suspend fun eliminarActividad(
        actividad: ActividadDiaria
    ) {
        actividadDao.eliminarActividad(actividad)
    }

    suspend fun guardarOActualizarActividad(
        actividad: ActividadDiaria
    ) {
        actividadDao.guardarActividad(actividad)
    }
}