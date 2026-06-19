package com.julioj.progresofisico.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.julioj.progresofisico.data.local.dao.ActividadDiariaDao
import com.julioj.progresofisico.data.local.dao.EjercicioDao
import com.julioj.progresofisico.data.local.dao.RegistroEntrenamientoDao
import com.julioj.progresofisico.data.local.dao.ProgresoUsuarioDao
import com.julioj.progresofisico.data.model.ActividadDiaria
import com.julioj.progresofisico.data.model.Ejercicio
import com.julioj.progresofisico.data.model.RegistroEntrenamiento
import com.julioj.progresofisico.data.model.ProgresoUsuario

@Database(
    entities = [
        ActividadDiaria::class,
        Ejercicio::class,
        RegistroEntrenamiento::class,
        ProgresoUsuario::class
    ],
    version = 4
)
@TypeConverters(ConversorRepeticiones::class)
abstract class BaseDatosProgreso : RoomDatabase() {

    abstract fun actividadDiariaDao(): ActividadDiariaDao

    abstract fun ejercicioDao(): EjercicioDao

    abstract fun registroEntrenamientoDao(): RegistroEntrenamientoDao

    abstract fun progresoUsuarioDao(): ProgresoUsuarioDao
}