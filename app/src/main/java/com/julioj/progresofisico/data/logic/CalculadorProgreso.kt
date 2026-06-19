package com.julioj.progresofisico.data.logic

import com.julioj.progresofisico.data.model.Ejercicio
import com.julioj.progresofisico.data.model.IndicadorProgreso
import com.julioj.progresofisico.data.model.RegistroEntrenamiento

object CalculadorProgreso {

    fun calcularIndicador(
        ejercicio: Ejercicio,
        registro: RegistroEntrenamiento
    ): IndicadorProgreso {

        val repeticiones = registro.repeticionesPorSerie

        val todasLleganAlMaximo = repeticiones.all { repeticion ->
            repeticion >= ejercicio.repeticionesMaximas
        }

        val todasLleganAlMinimo = repeticiones.all { repeticion ->
            repeticion >= ejercicio.repeticionesMinimas
        }

        val seriesPorDebajoDelMinimo = repeticiones.count { repeticion ->
            repeticion < ejercicio.repeticionesMinimas
        }

        val mitadDeSeries = repeticiones.size / 2f

        return when {
            todasLleganAlMaximo -> IndicadorProgreso.SUBIR_PESO

            todasLleganAlMinimo -> IndicadorProgreso.SUBIR_REPETICIONES

            seriesPorDebajoDelMinimo >= mitadDeSeries -> IndicadorProgreso.PESO_DEMASIADO_ALTO

            else -> IndicadorProgreso.MANTENER_PESO
        }
    }
}