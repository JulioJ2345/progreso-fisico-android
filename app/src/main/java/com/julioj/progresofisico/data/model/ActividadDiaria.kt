package com.julioj.progresofisico.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actividades_diarias")
data class ActividadDiaria(
    @PrimaryKey val fecha: String,
    val pasos: Int,
    val kilometros: Float,
    val objetivoPasos: Int,
    val objetivoCumplido: Boolean,
    val experienciaOtorgada: Int = 0
)