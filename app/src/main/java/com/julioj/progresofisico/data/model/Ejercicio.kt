package com.julioj.progresofisico.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ejercicios")
data class Ejercicio(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val grupoMuscular: String,
    val seriesObjetivo: Int,
    val repeticionesMinimas: Int,
    val repeticionesMaximas: Int,
    val incrementoPeso: Float
)