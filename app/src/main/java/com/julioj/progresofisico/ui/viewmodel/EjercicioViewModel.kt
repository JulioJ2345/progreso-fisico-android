package com.julioj.progresofisico.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julioj.progresofisico.data.model.Ejercicio
import com.julioj.progresofisico.data.repository.EjercicioRepository
import kotlinx.coroutines.launch

class EjercicioViewModel(
    private val ejercicioRepository: EjercicioRepository
) : ViewModel() {

    val ejercicios = mutableStateOf<List<Ejercicio>>(emptyList())
    val mensajeOperacion = mutableStateOf("")

    fun cargarEjercicios() {
        viewModelScope.launch {
            ejercicios.value = ejercicioRepository.obtenerEjercicios()
        }
    }

    fun insertarEjercicio(
        nombre: String,
        grupoMuscular: String,
        seriesObjetivo: Int,
        repeticionesMinimas: Int,
        repeticionesMaximas: Int,
        incrementoPeso: Float
    ) {
        viewModelScope.launch {

            val ejerciciosActuales = ejercicioRepository.obtenerEjercicios()

            val ejercicioDuplicado = ejerciciosActuales.any { ejercicio ->
                ejercicio.nombre.equals(nombre, ignoreCase = true)
            }

            if (ejercicioDuplicado) {
                mensajeOperacion.value = "Ya existe un ejercicio con ese nombre."
                return@launch
            }

            val ejercicio = Ejercicio(
                nombre = nombre,
                grupoMuscular = grupoMuscular,
                seriesObjetivo = seriesObjetivo,
                repeticionesMinimas = repeticionesMinimas,
                repeticionesMaximas = repeticionesMaximas,
                incrementoPeso = incrementoPeso
            )

            ejercicioRepository.insertarEjercicio(ejercicio)

            cargarEjercicios()
            mensajeOperacion.value = "Ejercicio guardado correctamente."
        }
    }

    fun eliminarEjercicio(
        ejercicio: Ejercicio
    ) {
        viewModelScope.launch {
            ejercicioRepository.eliminarEjercicio(ejercicio)
            mensajeOperacion.value = "Ejercicio eliminado correctamente."
            cargarEjercicios()
        }
    }

    fun actualizarEjercicio(
        ejercicio: Ejercicio
    ) {
        viewModelScope.launch {
            ejercicioRepository.actualizarEjercicio(ejercicio)
            mensajeOperacion.value = "Ejercicio actualizado correctamente."
            cargarEjercicios()
        }
    }
}