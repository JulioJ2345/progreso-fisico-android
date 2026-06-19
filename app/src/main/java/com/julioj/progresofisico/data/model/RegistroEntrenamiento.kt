package com.julioj.progresofisico.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registros_entrenamiento")
data class RegistroEntrenamiento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idEjercicio: Int,
    val fecha: String,
    val peso: Float,
    val repeticionesPorSerie: List<Int>,
    val experienciaOtorgada: Int = 0
)