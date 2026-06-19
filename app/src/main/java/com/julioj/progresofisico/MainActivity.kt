package com.julioj.progresofisico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.julioj.progresofisico.data.local.ProveedorBaseDatos
import com.julioj.progresofisico.data.repository.EjercicioRepository
import com.julioj.progresofisico.ui.theme.ProgresoFisicoTheme
import com.julioj.progresofisico.ui.viewmodel.InicioViewModelFactory
import com.julioj.progresofisico.ui.viewmodel.EjercicioViewModelFactory
import com.julioj.progresofisico.data.repository.RegistroEntrenamientoRepository
import com.julioj.progresofisico.ui.viewmodel.EntrenamientoViewModelFactory
import com.julioj.progresofisico.data.repository.ActividadRepository
import com.julioj.progresofisico.ui.viewmodel.ActividadViewModelFactory
import com.julioj.progresofisico.navigation.NavegacionApp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.julioj.progresofisico.data.repository.ProgresoUsuarioRepository
import com.julioj.progresofisico.ui.viewmodel.ProgresoUsuarioViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val baseDatos = ProveedorBaseDatos.obtenerBaseDatos(this)

        val ejercicioRepository = EjercicioRepository(
            baseDatos.ejercicioDao()
        )

        val actividadRepository = ActividadRepository(
            baseDatos.actividadDiariaDao()
        )

        val registroRepository = RegistroEntrenamientoRepository(
            baseDatos.registroEntrenamientoDao()
        )

        val progresoUsuarioRepository = ProgresoUsuarioRepository(
            baseDatos.progresoUsuarioDao()
        )

        val ejercicioViewModelFactory = EjercicioViewModelFactory(
            ejercicioRepository
        )

        val actividadViewModelFactory = ActividadViewModelFactory(
            actividadRepository
        )

        val entrenamientoViewModelFactory = EntrenamientoViewModelFactory(
            ejercicioRepository = ejercicioRepository,
            registroRepository = registroRepository
        )

        val progresoUsuarioViewModelFactory = ProgresoUsuarioViewModelFactory(
            progresoUsuarioRepository
        )

        setContent {
            ProgresoFisicoTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavegacionApp(
                        ejercicioViewModelFactory = ejercicioViewModelFactory,
                        entrenamientoViewModelFactory = entrenamientoViewModelFactory,
                        actividadViewModelFactory = actividadViewModelFactory,
                        progresoUsuarioViewModelFactory = progresoUsuarioViewModelFactory
                    )
                }
            }
        }
    }
}