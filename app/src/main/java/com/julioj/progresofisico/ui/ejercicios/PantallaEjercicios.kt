package com.julioj.progresofisico.ui.ejercicios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.julioj.progresofisico.ui.theme.TextoOscuro
import com.julioj.progresofisico.ui.theme.TurquesaPrincipal
import com.julioj.progresofisico.ui.theme.TurquesaSecundario
import com.julioj.progresofisico.ui.viewmodel.EjercicioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEjercicios(
    ejercicioViewModel: EjercicioViewModel,
    volver: () -> Unit
) {
    LaunchedEffect(Unit) {
        ejercicioViewModel.cargarEjercicios()
    }

    val controladorTeclado = LocalSoftwareKeyboardController.current
    val gestorFoco = LocalFocusManager.current

    val nombre = remember { mutableStateOf("") }
    val grupoMuscular = remember { mutableStateOf("") }
    val desplegableGrupoAbierto = remember { mutableStateOf(false) }

    val gruposMusculares = listOf(
        "Pecho",
        "Espalda",
        "Pierna",
        "Hombro",
        "Bíceps",
        "Tríceps",
        "Abdomen",
        "Cardio"
    )

    val seriesObjetivo = remember { mutableStateOf("3") }
    val repeticionesMinimas = remember { mutableStateOf("8") }
    val repeticionesMaximas = remember { mutableStateOf("15") }
    val incrementoPeso = remember { mutableStateOf("2.5") }
    val mensajeError = remember { mutableStateOf("") }

    val ejercicioPendienteEliminar =
        remember { mutableStateOf<com.julioj.progresofisico.data.model.Ejercicio?>(null) }

    val ejercicioEditando =
        remember { mutableStateOf<com.julioj.progresofisico.data.model.Ejercicio?>(null) }

    val pestanaActiva = remember { mutableStateOf(PestanaEjercicios.FORMULARIO) }
    val textoBusqueda = remember { mutableStateOf("") }
    val grupoFiltroSeleccionado = remember { mutableStateOf("Todos") }
    val desplegableFiltroGrupoAbierto = remember { mutableStateOf(false) }

    val gruposFiltro = listOf("Todos") + gruposMusculares

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = TurquesaSecundario.copy(alpha = 0.15f)
            ) {
                NavigationBarItem(
                    selected = pestanaActiva.value == PestanaEjercicios.FORMULARIO,
                    onClick = {
                        pestanaActiva.value = PestanaEjercicios.FORMULARIO
                    },
                    icon = {},
                    label = {
                        Text(text = "Crear ejercicio")
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
                    selected = pestanaActiva.value == PestanaEjercicios.HISTORIAL,
                    onClick = {
                        pestanaActiva.value = PestanaEjercicios.HISTORIAL
                    },
                    icon = {},
                    label = {
                        Text(text = "Mis ejercicios")
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
        ) {

            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = if (pestanaActiva.value == PestanaEjercicios.FORMULARIO) {
                            if (ejercicioEditando.value == null) {
                                "Crear ejercicio"
                            } else {
                                "Editar ejercicio"
                            }
                        } else {
                            "Mis ejercicios"
                        }
                    )
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

            Spacer(modifier = Modifier.height(16.dp))

            when (pestanaActiva.value) {
                PestanaEjercicios.FORMULARIO -> {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .imePadding()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            value = nombre.value,
                            onValueChange = { nuevoValor ->
                                nombre.value = nuevoValor
                            },
                            label = {
                                Text(text = "Nombre del ejercicio")
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        ExposedDropdownMenuBox(
                            expanded = desplegableGrupoAbierto.value,
                            onExpandedChange = {
                                desplegableGrupoAbierto.value = !desplegableGrupoAbierto.value
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            OutlinedTextField(
                                value = grupoMuscular.value,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(text = "Grupo muscular")
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = desplegableGrupoAbierto.value
                                    )
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = desplegableGrupoAbierto.value,
                                onDismissRequest = {
                                    desplegableGrupoAbierto.value = false
                                },
                                containerColor = Color.White
                            ) {
                                gruposMusculares.forEach { grupo ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = grupo)
                                        },
                                        onClick = {
                                            grupoMuscular.value = grupo
                                            desplegableGrupoAbierto.value = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Configuración del ejercicio",
                            style = MaterialTheme.typography.titleSmall,
                            color = TurquesaPrincipal,
                            modifier = Modifier.fillMaxWidth(0.85f),
                            textAlign = TextAlign.Start
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = seriesObjetivo.value,
                            onValueChange = { nuevoValor ->
                                seriesObjetivo.value = nuevoValor
                            },
                            label = {
                                Text(text = "Series por entrenamiento")
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = repeticionesMinimas.value,
                            onValueChange = { nuevoValor ->
                                repeticionesMinimas.value = nuevoValor
                            },
                            label = {
                                Text(text = "Repeticiones mínimas")
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = repeticionesMaximas.value,
                            onValueChange = { nuevoValor ->
                                repeticionesMaximas.value = nuevoValor
                            },
                            label = {
                                Text(text = "Repeticiones máximas")
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = incrementoPeso.value,
                            onValueChange = { nuevoValor ->
                                incrementoPeso.value = nuevoValor
                            },
                            label = {
                                Text(text = "Incremento recomendado (kg)")
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Ejemplo: +2.5 kg cuando completes todas las series.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF607D8B),
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                val seriesConvertidas = seriesObjetivo.value.toIntOrNull()
                                val repeticionesMinimasConvertidas =
                                    repeticionesMinimas.value.toIntOrNull()
                                val repeticionesMaximasConvertidas =
                                    repeticionesMaximas.value.toIntOrNull()
                                val incrementoConvertido = incrementoPeso.value.toFloatOrNull()

                                when {
                                    nombre.value.isBlank() -> {
                                        mensajeError.value = "Introduce el nombre del ejercicio."
                                    }

                                    grupoMuscular.value.isBlank() -> {
                                        mensajeError.value = "Introduce el grupo muscular."
                                    }

                                    seriesConvertidas == null -> {
                                        mensajeError.value =
                                            "Las series deben ser un número válido."
                                    }

                                    repeticionesMinimasConvertidas == null -> {
                                        mensajeError.value =
                                            "Las repeticiones mínimas deben ser un número válido."
                                    }

                                    repeticionesMaximasConvertidas == null -> {
                                        mensajeError.value =
                                            "Las repeticiones máximas deben ser un número válido."
                                    }

                                    incrementoConvertido == null -> {
                                        mensajeError.value =
                                            "El incremento de peso debe ser un número válido."
                                    }

                                    repeticionesMinimasConvertidas > repeticionesMaximasConvertidas -> {
                                        mensajeError.value =
                                            "Las repeticiones mínimas no pueden ser mayores que las máximas."
                                    }

                                    else -> {
                                        mensajeError.value = ""

                                        if (ejercicioEditando.value == null) {
                                            ejercicioViewModel.insertarEjercicio(
                                                nombre = nombre.value,
                                                grupoMuscular = grupoMuscular.value,
                                                seriesObjetivo = seriesConvertidas,
                                                repeticionesMinimas = repeticionesMinimasConvertidas,
                                                repeticionesMaximas = repeticionesMaximasConvertidas,
                                                incrementoPeso = incrementoConvertido
                                            )
                                        } else {
                                            ejercicioViewModel.actualizarEjercicio(
                                                ejercicioEditando.value!!.copy(
                                                    nombre = nombre.value,
                                                    grupoMuscular = grupoMuscular.value,
                                                    seriesObjetivo = seriesConvertidas,
                                                    repeticionesMinimas = repeticionesMinimasConvertidas,
                                                    repeticionesMaximas = repeticionesMaximasConvertidas,
                                                    incrementoPeso = incrementoConvertido
                                                )
                                            )

                                            ejercicioEditando.value = null
                                        }

                                        controladorTeclado?.hide()
                                        gestorFoco.clearFocus()

                                        nombre.value = ""
                                        grupoMuscular.value = ""
                                        seriesObjetivo.value = "3"
                                        repeticionesMinimas.value = "8"
                                        repeticionesMaximas.value = "15"
                                        incrementoPeso.value = "2.5"
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            Text(
                                text = if (ejercicioEditando.value == null) {
                                    "Guardar ejercicio"
                                } else {
                                    "Guardar cambios"
                                }
                            )
                        }

                        if (ejercicioEditando.value != null) {

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    controladorTeclado?.hide()
                                    gestorFoco.clearFocus()

                                    ejercicioEditando.value = null

                                    nombre.value = ""
                                    grupoMuscular.value = ""
                                    seriesObjetivo.value = "3"
                                    repeticionesMinimas.value = "8"
                                    repeticionesMaximas.value = "15"
                                    incrementoPeso.value = "2.5"

                                    mensajeError.value = ""
                                },
                                modifier = Modifier.fillMaxWidth(0.85f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFB0BEC5),
                                    contentColor = TextoOscuro
                                )
                            ) {
                                Text(text = "Cancelar edición")
                            }
                        }

                        if (ejercicioViewModel.mensajeOperacion.value.isNotBlank()) {

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = ejercicioViewModel.mensajeOperacion.value,
                                color = Color(0xFF4CAF50),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        if (mensajeError.value.isNotBlank()) {

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = mensajeError.value,
                                color = Color(0xFFF44336),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                PestanaEjercicios.HISTORIAL -> {

                    Text(
                        text = "Ejercicios guardados",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ExposedDropdownMenuBox(
                            expanded = desplegableFiltroGrupoAbierto.value,
                            onExpandedChange = {
                                desplegableFiltroGrupoAbierto.value =
                                    !desplegableFiltroGrupoAbierto.value
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            OutlinedTextField(
                                value = grupoFiltroSeleccionado.value,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(text = "Filtrar por grupo")
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = desplegableFiltroGrupoAbierto.value
                                    )
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = desplegableFiltroGrupoAbierto.value,
                                onDismissRequest = {
                                    desplegableFiltroGrupoAbierto.value = false
                                },
                                containerColor = Color.White
                            ) {
                                gruposFiltro.forEach { grupo ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = grupo)
                                        },
                                        onClick = {
                                            grupoFiltroSeleccionado.value = grupo
                                            desplegableFiltroGrupoAbierto.value = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = textoBusqueda.value,
                            onValueChange = {
                                textoBusqueda.value = it
                            },
                            label = {
                                Text(text = "🔍 Buscar ejercicio")
                            },
                            modifier = Modifier.fillMaxWidth(0.85f),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    if (ejercicioPendienteEliminar.value != null) {
                        AlertDialog(
                            onDismissRequest = {
                                ejercicioPendienteEliminar.value = null
                            },
                            title = {
                                Text(text = "Eliminar ejercicio")
                            },
                            text = {
                                Text(
                                    text = "¿Seguro que quieres eliminar ${ejercicioPendienteEliminar.value!!.nombre}?"
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        ejercicioViewModel.eliminarEjercicio(
                                            ejercicioPendienteEliminar.value!!
                                        )
                                        ejercicioPendienteEliminar.value = null
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
                                        ejercicioPendienteEliminar.value = null
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

                    val ejerciciosFiltrados =
                        ejercicioViewModel.ejercicios.value.filter { ejercicio ->

                            val coincideNombre =
                                ejercicio.nombre.contains(
                                    textoBusqueda.value,
                                    ignoreCase = true
                                )

                            val coincideGrupo =
                                grupoFiltroSeleccionado.value == "Todos" ||
                                        ejercicio.grupoMuscular == grupoFiltroSeleccionado.value

                            coincideNombre && coincideGrupo
                        }

                    if (ejerciciosFiltrados.isEmpty()) {

                        Text(
                            text = "No se ha encontrado ningún ejercicio.",
                            color = Color(0xFF607D8B),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                    } else {

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(ejerciciosFiltrados) { ejercicio ->

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
                                            modifier = Modifier.padding(16.dp)
                                        ) {

                                            Text(
                                                text = ejercicio.nombre,
                                                style = MaterialTheme.typography.titleMedium
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

                                            Text(
                                                text = "💪 ${ejercicio.grupoMuscular} · " +
                                                        "🔁 ${ejercicio.seriesObjetivo} series · " +
                                                        "🎯 ${ejercicio.repeticionesMinimas}-${ejercicio.repeticionesMaximas} reps · " +
                                                        "⚖️ +${ejercicio.incrementoPeso} kg",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color(0xFF607D8B)
                                            )

                                            Spacer(modifier = Modifier.height(12.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {

                                                Button(
                                                    onClick = {
                                                        ejercicioEditando.value = ejercicio

                                                        nombre.value = ejercicio.nombre
                                                        grupoMuscular.value =
                                                            ejercicio.grupoMuscular
                                                        seriesObjetivo.value =
                                                            ejercicio.seriesObjetivo.toString()
                                                        repeticionesMinimas.value =
                                                            ejercicio.repeticionesMinimas.toString()
                                                        repeticionesMaximas.value =
                                                            ejercicio.repeticionesMaximas.toString()
                                                        incrementoPeso.value =
                                                            ejercicio.incrementoPeso.toString()

                                                        pestanaActiva.value =
                                                            PestanaEjercicios.FORMULARIO
                                                    },
                                                    modifier = Modifier.weight(1f),
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = Color(0xFFB0BEC5),
                                                        contentColor = TextoOscuro
                                                    )
                                                ) {
                                                    Text(text = "Editar")
                                                }

                                                Button(
                                                    onClick = {
                                                        ejercicioPendienteEliminar.value = ejercicio
                                                    },
                                                    modifier = Modifier.weight(1f),
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
    }
}