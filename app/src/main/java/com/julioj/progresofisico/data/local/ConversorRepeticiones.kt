package com.julioj.progresofisico.data.local

import androidx.room.TypeConverter

class ConversorRepeticiones {

    @TypeConverter
    fun desdeLista(lista: List<Int>): String {
        return lista.joinToString(",")
    }

    @TypeConverter
    fun haciaLista(texto: String): List<Int> {
        if (texto.isBlank()) {
            return emptyList()
        }

        return texto.split(",").map { valor ->
            valor.toInt()
        }
    }
}