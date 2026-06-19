package com.julioj.progresofisico.data.logic

import com.julioj.progresofisico.data.model.ActividadDiaria
import java.time.LocalDate

object CalculadorRachas {

    fun calcularRachaActual(
        actividades: List<ActividadDiaria>
    ): Int {
        val actividadesPorFecha = actividades.associateBy { actividad ->
            LocalDate.parse(actividad.fecha)
        }

        var racha = 0
        var fechaActual = LocalDate.now()

        while (true) {
            val actividad = actividadesPorFecha[fechaActual]

            if (actividad != null && actividad.objetivoCumplido) {
                racha++
                fechaActual = fechaActual.minusDays(1)
            } else {
                break
            }
        }

        return racha
    }

    fun calcularMejorRacha(
        actividades: List<ActividadDiaria>
    ): Int {
        val actividadesOrdenadas = actividades
            .sortedBy { actividad ->
                LocalDate.parse(actividad.fecha)
            }

        var mejorRacha = 0
        var rachaActual = 0

        actividadesOrdenadas.forEach { actividad ->
            if (actividad.objetivoCumplido) {
                rachaActual++

                if (rachaActual > mejorRacha) {
                    mejorRacha = rachaActual
                }
            } else {
                rachaActual = 0
            }
        }

        return mejorRacha
    }
}