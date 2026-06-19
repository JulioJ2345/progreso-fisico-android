package com.julioj.progresofisico.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.julioj.progresofisico.ui.ejercicios.PantallaEjercicios
import com.julioj.progresofisico.ui.entrenamiento.PantallaEntrenamiento
import com.julioj.progresofisico.ui.inicio.PantallaInicio
import com.julioj.progresofisico.ui.viewmodel.EjercicioViewModel
import com.julioj.progresofisico.ui.viewmodel.EjercicioViewModelFactory
import com.julioj.progresofisico.ui.viewmodel.EntrenamientoViewModel
import com.julioj.progresofisico.ui.viewmodel.EntrenamientoViewModelFactory
import com.julioj.progresofisico.ui.actividad.PantallaActividad
import com.julioj.progresofisico.ui.viewmodel.ActividadViewModel
import com.julioj.progresofisico.ui.viewmodel.ActividadViewModelFactory
import com.julioj.progresofisico.ui.viewmodel.ProgresoUsuarioViewModel
import com.julioj.progresofisico.ui.viewmodel.ProgresoUsuarioViewModelFactory

@Composable
fun NavegacionApp(
    ejercicioViewModelFactory: EjercicioViewModelFactory,
    entrenamientoViewModelFactory: EntrenamientoViewModelFactory,
    actividadViewModelFactory: ActividadViewModelFactory,
    progresoUsuarioViewModelFactory: ProgresoUsuarioViewModelFactory
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Pantallas.INICIO.name
    ) {

        composable(Pantallas.INICIO.name) {

            val progresoUsuarioViewModel: ProgresoUsuarioViewModel = viewModel(
                factory = progresoUsuarioViewModelFactory
            )

            PantallaInicio(
                progresoUsuarioViewModel = progresoUsuarioViewModel,
                navegarAEjercicios = {
                    navController.navigate(Pantallas.EJERCICIOS.name)
                },
                navegarAEntrenamiento = {
                    navController.navigate(Pantallas.ENTRENAMIENTO.name)
                },
                navegarAActividad = {
                    navController.navigate(Pantallas.ACTIVIDAD.name)
                }
            )
        }

        composable(Pantallas.EJERCICIOS.name) {

            val ejercicioViewModel: EjercicioViewModel = viewModel(
                factory = ejercicioViewModelFactory
            )

            PantallaEjercicios(
                ejercicioViewModel = ejercicioViewModel,
                volver = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantallas.ENTRENAMIENTO.name) {

            val entrenamientoViewModel: EntrenamientoViewModel = viewModel(
                factory = entrenamientoViewModelFactory
            )

            val progresoUsuarioViewModel: ProgresoUsuarioViewModel = viewModel(
                factory = progresoUsuarioViewModelFactory
            )

            PantallaEntrenamiento(
                entrenamientoViewModel = entrenamientoViewModel,
                progresoUsuarioViewModel = progresoUsuarioViewModel,
                volver = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantallas.ACTIVIDAD.name) {

            val actividadViewModel: ActividadViewModel = viewModel(
                factory = actividadViewModelFactory
            )

            val progresoUsuarioViewModel: ProgresoUsuarioViewModel = viewModel(
                factory = progresoUsuarioViewModelFactory
            )

            PantallaActividad(
                actividadViewModel = actividadViewModel,
                progresoUsuarioViewModel = progresoUsuarioViewModel,
                volver = {
                    navController.popBackStack()
                }
            )
        }
    }
}