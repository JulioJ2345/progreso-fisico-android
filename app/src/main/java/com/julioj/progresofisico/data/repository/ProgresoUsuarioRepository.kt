package com.julioj.progresofisico.data.repository

import com.julioj.progresofisico.data.local.dao.ProgresoUsuarioDao
import com.julioj.progresofisico.data.model.ProgresoUsuario

class ProgresoUsuarioRepository(
    private val progresoUsuarioDao: ProgresoUsuarioDao
) {

    suspend fun obtenerOCrearProgreso(): ProgresoUsuario {
        val progresoExistente = progresoUsuarioDao.obtenerProgreso()

        if (progresoExistente != null) {
            return progresoExistente
        }

        val progresoInicial = ProgresoUsuario()

        progresoUsuarioDao.guardarProgreso(progresoInicial)

        return progresoInicial
    }

    suspend fun guardarProgreso(progresoUsuario: ProgresoUsuario) {
        progresoUsuarioDao.guardarProgreso(progresoUsuario)
    }
}