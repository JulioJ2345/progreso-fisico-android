package com.julioj.progresofisico.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julioj.progresofisico.data.model.ProgresoUsuario
import com.julioj.progresofisico.data.repository.ProgresoUsuarioRepository
import kotlinx.coroutines.launch

class ProgresoUsuarioViewModel(
    private val progresoUsuarioRepository: ProgresoUsuarioRepository
) : ViewModel() {

    val progresoUsuario = mutableStateOf(ProgresoUsuario())

    fun calcularExperienciaNecesaria(nivel: Int): Int {
        return 800 + (nivel * nivel * 35)
    }

    fun calcularExperienciaPorPasos(pasos: Int): Int {
        return when {
            pasos <= 3000 -> {
                pasos / 100
            }

            pasos <= 8000 -> {
                30 + ((pasos - 3000) / 75)
            }

            else -> {
                96 + ((pasos - 8000) / 200)
            }
        }
    }

    fun calcularExperienciaPorKilometros(kilometros: Float): Int {
        return (kilometros * 8).toInt()
    }

    fun calcularExperienciaPorActividad(
        pasos: Int,
        kilometros: Float
    ): Int {
        val experienciaPasos = calcularExperienciaPorPasos(pasos)
        val experienciaKilometros = calcularExperienciaPorKilometros(kilometros)

        val bonusObjetivo = if (pasos >= 8000) {
            100
        } else {
            0
        }

        return experienciaPasos + experienciaKilometros + bonusObjetivo
    }

    fun calcularExperienciaPorVolumen(
        peso: Float,
        repeticionesTotales: Int
    ): Int {
        val volumen = peso * repeticionesTotales

        return when {
            volumen <= 1000f -> {
                (volumen / 20f).toInt()
            }

            volumen <= 3000f -> {
                50 + ((volumen - 1000f) / 35f).toInt()
            }

            else -> {
                107 + ((volumen - 3000f) / 60f).toInt()
            }
        }
    }

    fun calcularExperienciaPorEntrenamiento(
        peso: Float,
        repeticionesPorSerie: List<Int>,
        grupoMuscular: String
    ): Int {
        val repeticionesTotales = repeticionesPorSerie.sum()

        val experienciaBase = 120
        val experienciaVolumen = calcularExperienciaPorVolumen(
            peso = peso,
            repeticionesTotales = repeticionesTotales
        )

        val experienciaGrupoMuscular = when (grupoMuscular.lowercase()) {
            "pierna", "piernas", "espalda", "pecho" -> 40
            "hombro", "hombros" -> 30
            "biceps", "bíceps", "triceps", "tríceps", "abdominales", "abdomen" -> 25
            else -> 20
        }

        return experienciaBase + experienciaVolumen + experienciaGrupoMuscular
    }

    fun cargarProgreso() {
        viewModelScope.launch {
            progresoUsuario.value = progresoUsuarioRepository.obtenerOCrearProgreso()
        }
    }

    fun ganarExperiencia(experienciaGanada: Int) {
        viewModelScope.launch {
            var progresoActual = progresoUsuarioRepository.obtenerOCrearProgreso()

            var nivel = progresoActual.nivel
            var experienciaActual = progresoActual.experienciaActual + experienciaGanada
            var experienciaTotal = progresoActual.experienciaTotal + experienciaGanada

            if (experienciaTotal < 0) {
                experienciaTotal = 0
            }

            while (
                experienciaActual < 0 &&
                nivel > 1
            ) {
                nivel--

                experienciaActual += calcularExperienciaNecesaria(nivel)
            }

            if (nivel <= 1 && experienciaActual < 0) {
                nivel = 1
                experienciaActual = 0
            }

            while (
                nivel < 100 &&
                experienciaActual >= calcularExperienciaNecesaria(nivel)
            ) {
                experienciaActual -= calcularExperienciaNecesaria(nivel)
                nivel++
            }

            if (nivel >= 100) {
                nivel = 100
                experienciaActual = 0
            }

            progresoActual = progresoActual.copy(
                nivel = nivel,
                experienciaActual = experienciaActual,
                experienciaTotal = experienciaTotal
            )

            progresoUsuarioRepository.guardarProgreso(progresoActual)
            progresoUsuario.value = progresoActual
        }
    }
}