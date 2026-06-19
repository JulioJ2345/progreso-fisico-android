package com.julioj.progresofisico.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julioj.progresofisico.data.model.Ejercicio
import com.julioj.progresofisico.data.repository.EjercicioRepository
import kotlinx.coroutines.launch

class InicioViewModel(
    private val ejercicioRepository: EjercicioRepository
) : ViewModel() {

    val mensaje = mutableStateOf("Sin ejercicios guardados todavía.")

    fun insertarEjercicioDePrueba() {
        viewModelScope.launch {

            val ejercicio = Ejercicio(
                nombre = "Press banca",
                grupoMuscular = "Pecho",
                seriesObjetivo = 3,
                repeticionesMinimas = 8,
                repeticionesMaximas = 12,
                incrementoPeso = 2.5f
            )

            ejercicioRepository.insertarEjercicio(ejercicio)

            val ejercicios = ejercicioRepository.obtenerEjercicios()

            mensaje.value = "Ejercicios guardados: ${ejercicios.size}"
        }
    }
}