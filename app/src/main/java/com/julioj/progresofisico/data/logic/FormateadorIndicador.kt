package com.julioj.progresofisico.data.logic

import com.julioj.progresofisico.data.model.IndicadorProgreso

object FormateadorIndicador {

    fun obtenerMensaje(indicador: IndicadorProgreso): String {

        return when (indicador) {

            IndicadorProgreso.SUBIR_REPETICIONES ->
                "Intenta hacer más repeticiones en la próxima sesión."

            IndicadorProgreso.MANTENER_PESO ->
                "Mantén el peso actual."

            IndicadorProgreso.SUBIR_PESO ->
                "Has alcanzado el máximo de repeticiones. Sube el peso."

            IndicadorProgreso.PESO_DEMASIADO_ALTO ->
                "Reduce el peso o intenta consolidar las repeticiones."
        }
    }
}