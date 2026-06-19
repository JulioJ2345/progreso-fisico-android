package com.julioj.progresofisico.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progreso_usuario")
data class ProgresoUsuario(
    @PrimaryKey
    val id: Int = 1,
    val nivel: Int = 1,
    val experienciaActual: Int = 0,
    val experienciaTotal: Int = 0
)