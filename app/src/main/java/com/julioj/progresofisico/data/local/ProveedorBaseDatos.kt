package com.julioj.progresofisico.data.local

import android.content.Context
import androidx.room.Room

object ProveedorBaseDatos {

    @Volatile
    private var instancia: BaseDatosProgreso? = null

    fun obtenerBaseDatos(context: Context): BaseDatosProgreso {

        return instancia ?: synchronized(this) {

            val nuevaInstancia = Room.databaseBuilder(
                context.applicationContext,
                BaseDatosProgreso::class.java,
                "base_datos_progreso"
            )
                .fallbackToDestructiveMigration()
                .build()

            instancia = nuevaInstancia

            nuevaInstancia
        }
    }
}