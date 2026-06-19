package com.julioj.progresofisico.ui.actividad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.julioj.progresofisico.ui.viewmodel.ActividadViewModel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.julioj.progresofisico.data.model.ActividadDiaria
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import com.julioj.progresofisico.ui.theme.TextoOscuro
import com.julioj.progresofisico.ui.theme.TurquesaPrincipal
import com.julioj.progresofisico.ui.theme.TurquesaSecundario
import androidx.compose.ui.Alignment
import androidx.compose.material3.AlertDialog
import com.julioj.progresofisico.ui.viewmodel.ProgresoUsuarioViewModel
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaActividad(
    actividadViewModel: ActividadViewModel,
    progresoUsuarioViewModel: ProgresoUsuarioViewModel,
    volver: () -> Unit
) {
    LaunchedEffect(Unit) {
        actividadViewModel.cargarDatos()
    }

    val controladorTeclado = LocalSoftwareKeyboardController.current
    val gestorFoco = LocalFocusManager.current

    val pasos = remember { mutableStateOf("") }
    val kilometros = remember { mutableStateOf("") }
    val objetivoPasos = remember { mutableStateOf("8000") }
    val mensajeError = remember { mutableStateOf("") }
    val actividadPendienteEliminar = remember { mutableStateOf<ActividadDiaria?>(null) }
    val pestanaActiva = remember { mutableStateOf(PestanaActividad.REGISTRAR) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = TurquesaSecundario.copy(alpha = 0.15f)
            ) {
                NavigationBarItem(
                    selected = pestanaActiva.value == PestanaActividad.REGISTRAR,
                    onClick = {
                        pestanaActiva.value = PestanaActividad.REGISTRAR
                    },
                    icon = {},
                    label = {
                        Text(text = "Registrar")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TurquesaPrincipal,
                        selectedTextColor = TurquesaPrincipal,
                        indicatorColor = TurquesaSecundario.copy(alpha = 0.30f),
                        unselectedIconColor = Color(0xFF90A4AE),
                        unselectedTextColor = Color(0xFF90A4AE)
                    )
                )

                NavigationBarItem(
                    selected = pestanaActiva.value == PestanaActividad.HISTORIAL,
                    onClick = {
                        pestanaActiva.value = PestanaActividad.HISTORIAL
                    },
                    icon = {},
                    label = {
                        Text(text = "Historial")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TurquesaPrincipal,
                        selectedTextColor = TurquesaPrincipal,
                        indicatorColor = TurquesaSecundario.copy(alpha = 0.30f),
                        unselectedIconColor = Color(0xFF90A4AE),
                        unselectedTextColor = Color(0xFF90A4AE)
                    )
                )
            }
        }
    ) { paddingInterno ->

        Column(
            modifier = Modifier
                .padding(paddingInterno)
                .padding(24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {

            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(text = "Actividad diaria")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            volver()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            when (pestanaActiva.value) {
                PestanaActividad.REGISTRAR -> {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            value = objetivoPasos.value,
                            onValueChange = { objetivoPasos.value = it },
                            label = { Text(text = "Objetivo diario (pasos)") },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = pasos.value,
                            onValueChange = { pasos.value = it },
                            label = { Text(text = "Pasos realizados hoy") },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = kilometros.value,
                            onValueChange = { kilometros.value = it },
                            label = { Text(text = "Kilómetros recorridos") },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                val pasosConvertidos = pasos.value.toIntOrNull()
                                val kilometrosConvertidos = kilometros.value.toFloatOrNull()
                                val objetivoConvertido = objetivoPasos.value.toIntOrNull()

                                when {
                                    objetivoConvertido == null || objetivoConvertido <= 0 -> {
                                        mensajeError.value =
                                            "Introduce un objetivo de pasos válido."
                                    }

                                    pasosConvertidos == null || pasosConvertidos < 0 -> {
                                        mensajeError.value =
                                            "Introduce una cantidad de pasos válida."
                                    }

                                    kilometrosConvertidos == null || kilometrosConvertidos < 0 -> {
                                        mensajeError.value =
                                            "Introduce una cantidad de kilómetros válida."
                                    }

                                    else -> {
                                        mensajeError.value = ""

                                        val actividadAnterior = actividadViewModel.actividadHoy.value
                                        val experienciaAnterior = actividadAnterior?.experienciaOtorgada ?: 0

                                        val experienciaCalculada = progresoUsuarioViewModel.calcularExperienciaPorActividad(
                                            pasos = pasosConvertidos,
                                            kilometros = kilometrosConvertidos
                                        )

                                        val diferenciaExperiencia = experienciaCalculada - experienciaAnterior

                                        actividadViewModel.guardarActividad(
                                            pasos = pasosConvertidos,
                                            kilometros = kilometrosConvertidos,
                                            objetivoPasos = objetivoConvertido,
                                            experienciaOtorgada = experienciaCalculada
                                        )

                                        if (diferenciaExperiencia != 0) {
                                            progresoUsuarioViewModel.ganarExperiencia(diferenciaExperiencia)
                                        }

                                        controladorTeclado?.hide()
                                        gestorFoco.clearFocus()

                                        pasos.value = ""
                                        kilometros.value = ""
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            Text(text = "Guardar actividad")
                        }

                        if (mensajeError.value.isNotBlank()) {
                            Text(
                                text = mensajeError.value,
                                color = Color(0xFFF44336),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        if (actividadViewModel.mensajeOperacion.value.isNotBlank()) {
                            Text(
                                text = actividadViewModel.mensajeOperacion.value,
                                color = Color(0xFF4CAF50),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val actividadHoy = actividadViewModel.actividadHoy.value

                        if (actividadHoy != null) {
                            Card(
                                modifier = Modifier.fillMaxWidth(0.90f),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {

                                    Text(
                                        text = "Hoy",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TurquesaPrincipal,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "🚶 ${actividadHoy.pasos} / ${actividadHoy.objetivoPasos} pasos"
                                    )

                                    Text(
                                        text = "📍 ${actividadHoy.kilometros} km"
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = if (actividadHoy.objetivoCumplido) {
                                            "✅ Objetivo cumplido"
                                        } else {
                                            "⚠️ Objetivo no cumplido"
                                        },
                                        color = if (actividadHoy.objetivoCumplido) {
                                            Color(0xFF4CAF50)
                                        } else {
                                            Color(0xFFFF9800)
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                PestanaActividad.HISTORIAL -> {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Card(
                            modifier = Modifier.fillMaxWidth(0.90f),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = "🔥 Racha actual: ${actividadViewModel.rachaActual.value} días",
                                    color = TurquesaPrincipal,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "🏆 Mejor racha: ${actividadViewModel.mejorRacha.value} días",
                                    color = TurquesaPrincipal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (actividadPendienteEliminar.value != null) {
                        AlertDialog(
                            onDismissRequest = {
                                actividadPendienteEliminar.value = null
                            },
                            title = {
                                Text(text = "Eliminar actividad")
                            },
                            text = {
                                Text(
                                    text = "¿Seguro que quieres eliminar la actividad del día ${actividadPendienteEliminar.value!!.fecha}?"
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        val actividadEliminada = actividadPendienteEliminar.value!!

                                        progresoUsuarioViewModel.ganarExperiencia(
                                            -actividadEliminada.experienciaOtorgada
                                        )

                                        actividadViewModel.eliminarActividad(
                                            actividadEliminada
                                        )

                                        actividadPendienteEliminar.value = null
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFE57373)
                                    )
                                ) {
                                    Text(text = "Eliminar")
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        actividadPendienteEliminar.value = null
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFB0BEC5),
                                        contentColor = TextoOscuro
                                    )
                                ) {
                                    Text(text = "Cancelar")
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Últimos registros",
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (actividadViewModel.historialActividades.value.isEmpty()) {

                        Text(
                            text = "Todavía no has registrado ninguna actividad.",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF607D8B)
                        )

                    } else {

                        actividadViewModel.historialActividades.value.take(7).forEach { actividad ->

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.90f)
                                        .padding(vertical = 8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 2.dp
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp)
                                    ) {
                                        Text(text = actividad.fecha)

                                        Text(
                                            text = "🚶 ${actividad.pasos} / ${actividad.objetivoPasos} pasos"
                                        )

                                        Text(
                                            text = "📍 ${actividad.kilometros} km"
                                        )

                                        Text(
                                            text = if (actividad.objetivoCumplido) {
                                                "✅ Objetivo cumplido"
                                            } else {
                                                "⚠️ Objetivo no cumplido"
                                            },
                                            color = if (actividad.objetivoCumplido) {
                                                Color(0xFF4CAF50)
                                            } else {
                                                Color(0xFFFF9800)
                                            }
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Button(
                                            onClick = {
                                                actividadPendienteEliminar.value = actividad
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFE57373)
                                            )
                                        ) {
                                            Text(text = "Eliminar")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}