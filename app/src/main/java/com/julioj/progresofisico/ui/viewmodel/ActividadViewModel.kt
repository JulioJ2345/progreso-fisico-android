package com.julioj.progresofisico.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julioj.progresofisico.data.model.ActividadDiaria
import com.julioj.progresofisico.data.repository.ActividadRepository
import com.julioj.progresofisico.data.logic.CalculadorRachas
import kotlinx.coroutines.launch
import java.time.LocalDate

class ActividadViewModel(
    private val actividadRepository: ActividadRepository
) : ViewModel() {

    val actividadHoy = mutableStateOf<ActividadDiaria?>(null)
    val historialActividades = mutableStateOf<List<ActividadDiaria>>(emptyList())
    val rachaActual = mutableStateOf(0)
    val mejorRacha = mutableStateOf(0)
    val mensajeOperacion = mutableStateOf("")

    fun cargarDatos() {
        viewModelScope.launch {
            val fechaHoy = LocalDate.now().toString()

            actividadHoy.value = actividadRepository.obtenerActividadPorFecha(fechaHoy)
            historialActividades.value = actividadRepository.obtenerTodasLasActividades()
            rachaActual.value = CalculadorRachas.calcularRachaActual(historialActividades.value)
            mejorRacha.value = CalculadorRachas.calcularMejorRacha(historialActividades.value)
        }
    }

    fun guardarActividad(
        pasos: Int,
        kilometros: Float,
        objetivoPasos: Int,
        experienciaOtorgada: Int
    ) {
        viewModelScope.launch {
            val actividad = ActividadDiaria(
                fecha = LocalDate.now().toString(),
                pasos = pasos,
                kilometros = kilometros,
                objetivoPasos = objetivoPasos,
                objetivoCumplido = pasos >= objetivoPasos,
                experienciaOtorgada = experienciaOtorgada
            )

            actividadRepository.guardarOActualizarActividad(actividad)

            mensajeOperacion.value = "Actividad guardada correctamente."

            cargarDatos()
        }
    }

    fun eliminarActividad(
        actividad: ActividadDiaria
    ) {
        viewModelScope.launch {
            actividadRepository.eliminarActividad(actividad)

            mensajeOperacion.value = "Actividad eliminada correctamente."

            cargarDatos()
        }
    }
}