package com.julioj.progresofisico.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julioj.progresofisico.data.logic.CalculadorProgreso
import com.julioj.progresofisico.data.logic.FormateadorIndicador
import com.julioj.progresofisico.data.model.Ejercicio
import com.julioj.progresofisico.data.model.RegistroEntrenamiento
import com.julioj.progresofisico.data.repository.EjercicioRepository
import com.julioj.progresofisico.data.repository.RegistroEntrenamientoRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EntrenamientoViewModel(
    private val ejercicioRepository: EjercicioRepository,
    private val registroRepository: RegistroEntrenamientoRepository
) : ViewModel() {

    val ejercicios = mutableStateOf<List<Ejercicio>>(emptyList())
    val mensajeResultado = mutableStateOf("")
    val ultimoRegistro = mutableStateOf<RegistroEntrenamiento?>(null)
    val historialRegistros = mutableStateOf<List<RegistroEntrenamiento>>(emptyList())
    val historialEjercicioSeleccionado = mutableStateOf<List<RegistroEntrenamiento>>(emptyList())

    fun cargarEjercicios() {
        viewModelScope.launch {
            ejercicios.value = ejercicioRepository.obtenerEjercicios()
        }
    }

    fun registrarEntrenamiento(
        ejercicio: Ejercicio,
        peso: Float,
        repeticionesPorSerie: List<Int>,
        experienciaOtorgada: Int
    ) {
        viewModelScope.launch {

            val registro = RegistroEntrenamiento(
                idEjercicio = ejercicio.id,
                fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                peso = peso,
                repeticionesPorSerie = repeticionesPorSerie,
                experienciaOtorgada = experienciaOtorgada
            )

            registroRepository.insertarRegistro(registro)
            cargarHistorial()

            val indicador = CalculadorProgreso.calcularIndicador(
                ejercicio = ejercicio,
                registro = registro
            )

            mensajeResultado.value = FormateadorIndicador.obtenerMensaje(indicador)
        }
    }

    fun eliminarRegistro(
        registro: RegistroEntrenamiento
    ) {
        viewModelScope.launch {
            registroRepository.eliminarRegistro(registro)

            mensajeResultado.value = "Entrenamiento eliminado correctamente."

            cargarHistorial()

            cargarHistorialPorEjercicio(registro.idEjercicio)

            cargarUltimoRegistro(registro.idEjercicio)
        }
    }

    fun cargarUltimoRegistro(
        idEjercicio: Int
    ) {
        viewModelScope.launch {
            ultimoRegistro.value = registroRepository.obtenerUltimoRegistroPorEjercicio(
                idEjercicio
            )
        }
    }

    fun cargarHistorial() {
        viewModelScope.launch {
            historialRegistros.value = registroRepository.obtenerTodosLosRegistros()
        }
    }

    fun cargarHistorialPorEjercicio(
        idEjercicio: Int
    ) {
        viewModelScope.launch {
            historialEjercicioSeleccionado.value = registroRepository.obtenerRegistrosPorEjercicio(
                idEjercicio
            )
        }
    }

    fun limpiarMensajeResultado() {
        mensajeResultado.value = ""
    }
}