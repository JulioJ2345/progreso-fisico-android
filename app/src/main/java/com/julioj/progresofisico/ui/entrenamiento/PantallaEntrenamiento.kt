package com.julioj.progresofisico.ui.entrenamiento

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.julioj.progresofisico.data.model.Ejercicio
import com.julioj.progresofisico.data.model.RegistroEntrenamiento
import com.julioj.progresofisico.ui.theme.TextoOscuro
import com.julioj.progresofisico.ui.theme.TurquesaPrincipal
import com.julioj.progresofisico.ui.theme.TurquesaSecundario
import com.julioj.progresofisico.ui.viewmodel.EntrenamientoViewModel
import com.julioj.progresofisico.ui.viewmodel.ProgresoUsuarioViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEntrenamiento(
    entrenamientoViewModel: EntrenamientoViewModel,
    progresoUsuarioViewModel: ProgresoUsuarioViewModel,
    volver: () -> Unit
) {
    LaunchedEffect(Unit) {
        entrenamientoViewModel.cargarEjercicios()
    }

    val ejercicioSeleccionado = remember { mutableStateOf<Ejercicio?>(null) }
    val peso = remember { mutableStateOf("") }
    val repeticionesPorSerie = remember { mutableStateOf<List<String>>(emptyList()) }
    val mensajeError = remember { mutableStateOf("") }
    val registroPendienteEliminar = remember { mutableStateOf<RegistroEntrenamiento?>(null) }
    val pestanaActiva = remember { mutableStateOf(PestanaEntrenamiento.REGISTRAR) }
    val textoBusqueda = remember { mutableStateOf("") }
    val grupoSeleccionado = remember { mutableStateOf("Todos") }
    val desplegableGrupoAbierto = remember { mutableStateOf(false) }
    val filtroDia = remember { mutableStateOf("") }
    val filtroMes = remember { mutableStateOf("") }
    val filtroAnio = remember { mutableStateOf("") }

    val gruposMusculares = listOf(
        "Todos",
        "Pecho",
        "Espalda",
        "Pierna",
        "Hombro",
        "Bíceps",
        "Tríceps",
        "Abdomen",
        "Cardio"
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = TurquesaSecundario.copy(alpha = 0.15f)
            ) {
                NavigationBarItem(
                    selected = pestanaActiva.value == PestanaEntrenamiento.REGISTRAR,
                    onClick = { pestanaActiva.value = PestanaEntrenamiento.REGISTRAR },
                    icon = {},
                    label = { Text(text = "Registrar") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TurquesaPrincipal,
                        selectedTextColor = TurquesaPrincipal,
                        indicatorColor = TurquesaSecundario.copy(alpha = 0.30f),
                        unselectedIconColor = Color(0xFF90A4AE),
                        unselectedTextColor = Color(0xFF90A4AE)
                    )
                )

                NavigationBarItem(
                    selected = pestanaActiva.value == PestanaEntrenamiento.HISTORIAL,
                    onClick = { pestanaActiva.value = PestanaEntrenamiento.HISTORIAL },
                    icon = {},
                    label = { Text(text = "Historial") },
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
                title = { Text(text = "Registrar entrenamiento") },
                navigationIcon = {
                    IconButton(onClick = { volver() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (pestanaActiva.value) {
                PestanaEntrenamiento.REGISTRAR -> {

                    Text(
                        text = "Selecciona ejercicio",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ExposedDropdownMenuBox(
                            expanded = desplegableGrupoAbierto.value,
                            onExpandedChange = {
                                desplegableGrupoAbierto.value = !desplegableGrupoAbierto.value
                            },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            OutlinedTextField(
                                value = grupoSeleccionado.value,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(text = "Grupo muscular") },
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
                                        text = { Text(text = grupo) },
                                        onClick = {
                                            grupoSeleccionado.value = grupo
                                            desplegableGrupoAbierto.value = false
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

                        Spacer(modifier = Modifier.height(16.dp))

                        val ejerciciosFiltrados =
                            entrenamientoViewModel.ejercicios.value.filter { ejercicio ->

                                val coincideNombre =
                                    ejercicio.nombre.contains(
                                        textoBusqueda.value,
                                        ignoreCase = true
                                    )

                                val coincideGrupo =
                                    grupoSeleccionado.value == "Todos" ||
                                            ejercicio.grupoMuscular == grupoSeleccionado.value

                                coincideNombre && coincideGrupo
                            }

                        if (ejerciciosFiltrados.isEmpty()) {

                            Text(
                                text = "No se ha encontrado ningún ejercicio.",
                                color = Color(0xFF607D8B),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.fillMaxWidth(0.85f),
                                textAlign = TextAlign.Center
                            )

                        } else {

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .heightIn(max = 220.dp)
                            ) {
                                items(ejerciciosFiltrados) { ejercicio ->

                                    val estaSeleccionado =
                                        ejercicioSeleccionado.value?.id == ejercicio.id

                                    Button(
                                        onClick = {
                                            ejercicioSeleccionado.value = ejercicio
                                            mensajeError.value = ""
                                            peso.value = ""

                                            entrenamientoViewModel.limpiarMensajeResultado()

                                            repeticionesPorSerie.value =
                                                List(ejercicio.seriesObjetivo) { "" }

                                            entrenamientoViewModel.cargarUltimoRegistro(ejercicio.id)
                                            entrenamientoViewModel.cargarHistorialPorEjercicio(
                                                ejercicio.id
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (estaSeleccionado) {
                                                TurquesaPrincipal
                                            } else {
                                                TurquesaPrincipal.copy(alpha = 0.18f)
                                            },
                                            contentColor = if (estaSeleccionado) {
                                                Color.White
                                            } else {
                                                TurquesaPrincipal
                                            }
                                        )
                                    ) {
                                        Text(text = ejercicio.nombre)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (ejercicioSeleccionado.value != null) {

                            Card(
                                modifier = Modifier.fillMaxWidth(0.85f),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Ejercicio seleccionado",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = TurquesaPrincipal
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = ejercicioSeleccionado.value!!.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "${ejercicioSeleccionado.value!!.seriesObjetivo} series · " +
                                                "${ejercicioSeleccionado.value!!.repeticionesMinimas}-" +
                                                "${ejercicioSeleccionado.value!!.repeticionesMaximas} reps",
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        if (entrenamientoViewModel.ultimoRegistro.value != null) {
                            val ultimoRegistro = entrenamientoViewModel.ultimoRegistro.value!!

                            Card(
                                modifier = Modifier.fillMaxWidth(0.85f),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 1.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Último entrenamiento",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Text(
                                        text = "${ultimoRegistro.fecha} · " +
                                                "${ultimoRegistro.peso} kg · " +
                                                ultimoRegistro.repeticionesPorSerie.joinToString("/")
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            if (entrenamientoViewModel.mensajeResultado.value.isNotBlank()) {

                                Spacer(modifier = Modifier.height(12.dp))

                                Card(
                                    modifier = Modifier.fillMaxWidth(0.85f),
                                    colors = CardDefaults.cardColors(
                                        containerColor = TurquesaSecundario.copy(alpha = 0.15f)
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 1.dp
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp)
                                    ) {
                                        Text(
                                            text = "💡 Recomendación",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = TurquesaPrincipal
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Text(
                                            text = entrenamientoViewModel.mensajeResultado.value,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextoOscuro
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (ejercicioSeleccionado.value != null) {

                            OutlinedTextField(
                                value = peso.value,
                                onValueChange = { nuevoValor ->
                                    peso.value = nuevoValor
                                },
                                label = {
                                    Text(text = "Peso usado")
                                },
                                modifier = Modifier.fillMaxWidth(0.85f)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            repeticionesPorSerie.value.forEachIndexed { indice, valor ->

                                OutlinedTextField(
                                    value = valor,
                                    onValueChange = { nuevoValor ->
                                        repeticionesPorSerie.value =
                                            repeticionesPorSerie.value.toMutableList()
                                                .also { lista ->
                                                    lista[indice] = nuevoValor
                                                }
                                    },
                                    label = {
                                        Text(text = "Serie ${indice + 1}")
                                    },
                                    modifier = Modifier.fillMaxWidth(0.85f)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    val ejercicioActual = ejercicioSeleccionado.value
                                    val pesoConvertido = peso.value.toFloatOrNull()

                                    val repeticionesConvertidas =
                                        repeticionesPorSerie.value.mapNotNull { texto ->
                                            texto.toIntOrNull()
                                        }

                                    val todasLasSeriesSonValidas =
                                        repeticionesConvertidas.size == repeticionesPorSerie.value.size

                                    val todasLasRepeticionesSonPositivas =
                                        repeticionesConvertidas.all { repeticion ->
                                            repeticion > 0
                                        }

                                    val pesoEsPositivo =
                                        pesoConvertido != null && pesoConvertido > 0

                                    when {
                                        ejercicioActual == null -> {
                                            mensajeError.value = "Selecciona un ejercicio."
                                        }

                                        !pesoEsPositivo -> {
                                            mensajeError.value = "Introduce un peso mayor que 0."
                                        }

                                        !todasLasSeriesSonValidas -> {
                                            mensajeError.value =
                                                "Introduce repeticiones válidas en todas las series."
                                        }

                                        !todasLasRepeticionesSonPositivas -> {
                                            mensajeError.value =
                                                "Las repeticiones deben ser mayores que 0."
                                        }

                                        else -> {
                                            mensajeError.value = ""

                                            val experienciaGanada =
                                                progresoUsuarioViewModel.calcularExperienciaPorEntrenamiento(
                                                    peso = pesoConvertido,
                                                    repeticionesPorSerie = repeticionesConvertidas,
                                                    grupoMuscular = ejercicioActual.grupoMuscular
                                                )

                                            entrenamientoViewModel.registrarEntrenamiento(
                                                ejercicio = ejercicioActual,
                                                peso = pesoConvertido,
                                                repeticionesPorSerie = repeticionesConvertidas,
                                                experienciaOtorgada = experienciaGanada
                                            )

                                            progresoUsuarioViewModel.ganarExperiencia(
                                                experienciaGanada
                                            )

                                            peso.value = ""
                                            repeticionesPorSerie.value =
                                                List(ejercicioActual.seriesObjetivo) { "" }

                                            entrenamientoViewModel.cargarUltimoRegistro(
                                                ejercicioActual.id
                                            )
                                            entrenamientoViewModel.cargarHistorialPorEjercicio(
                                                ejercicioActual.id
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(0.85f)
                            ) {
                                Text(text = "Guardar entrenamiento")
                            }
                        }

                        if (mensajeError.value.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = mensajeError.value,
                                color = Color(0xFFF44336),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                PestanaEntrenamiento.HISTORIAL -> {

                    Spacer(modifier = Modifier.height(12.dp))

                    val ejercicioActual = ejercicioSeleccionado.value

                    Text(
                        text = if (ejercicioActual == null) {
                            "Historial"
                        } else {
                            "Historial de ${ejercicioActual.nombre}"
                        },
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Filtrar por fecha",
                            style = MaterialTheme.typography.titleSmall,
                            color = TurquesaPrincipal,
                            modifier = Modifier.fillMaxWidth(0.90f)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(0.90f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = filtroDia.value,
                                onValueChange = { nuevoValor ->
                                    filtroDia.value = nuevoValor.filter { caracter -> caracter.isDigit() }.take(2)
                                },
                                label = {
                                    Text(text = "Día")
                                },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = filtroMes.value,
                                onValueChange = { nuevoValor ->
                                    filtroMes.value = nuevoValor.filter { caracter -> caracter.isDigit() }.take(2)
                                },
                                label = {
                                    Text(text = "Mes")
                                },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = filtroAnio.value,
                                onValueChange = { nuevoValor ->
                                    filtroAnio.value = nuevoValor.filter { caracter -> caracter.isDigit() }.take(4)
                                },
                                label = {
                                    Text(text = "Año")
                                },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                filtroDia.value = ""
                                filtroMes.value = ""
                                filtroAnio.value = ""
                            },
                            modifier = Modifier.fillMaxWidth(0.90f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB0BEC5),
                                contentColor = TextoOscuro
                            )
                        ) {
                            Text(text = "Limpiar filtro")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (ejercicioActual == null) {

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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text = "🏋️",
                                        style = MaterialTheme.typography.headlineMedium
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "Ningún ejercicio seleccionado",
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Selecciona un ejercicio en la pestaña Registrar para ver su historial.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF607D8B),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                    } else {

                        if (entrenamientoViewModel.historialEjercicioSeleccionado.value.isEmpty()) {
                            Text(
                                text = "Todavía no has registrado entrenamientos para este ejercicio.",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color(0xFF607D8B)
                            )
                        }

                        if (registroPendienteEliminar.value != null) {
                            AlertDialog(
                                onDismissRequest = {
                                    registroPendienteEliminar.value = null
                                },
                                title = {
                                    Text(text = "Eliminar entrenamiento")
                                },
                                text = {
                                    Text(
                                        text = "¿Seguro que quieres eliminar el entrenamiento del día ${registroPendienteEliminar.value!!.fecha}?"
                                    )
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            val registroEliminado = registroPendienteEliminar.value!!

                                            progresoUsuarioViewModel.ganarExperiencia(
                                                -registroEliminado.experienciaOtorgada
                                            )

                                            entrenamientoViewModel.eliminarRegistro(
                                                registroEliminado
                                            )

                                            registroPendienteEliminar.value = null
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
                                            registroPendienteEliminar.value = null
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

                        val historialFiltrado =
                            entrenamientoViewModel.historialEjercicioSeleccionado.value.filter { registro ->

                                val partesFecha = registro.fecha.split("/")

                                val diaRegistro = partesFecha.getOrNull(0) ?: ""
                                val mesRegistro = partesFecha.getOrNull(1) ?: ""
                                val anioRegistro = partesFecha.getOrNull(2) ?: ""

                                val coincideDia =
                                    filtroDia.value.isBlank() || diaRegistro == filtroDia.value.padStart(2, '0')

                                val coincideMes =
                                    filtroMes.value.isBlank() || mesRegistro == filtroMes.value.padStart(2, '0')

                                val coincideAnio =
                                    filtroAnio.value.isBlank() || anioRegistro == filtroAnio.value

                                coincideDia && coincideMes && coincideAnio
                            }

                        if (historialFiltrado.isEmpty()) {
                            Text(
                                text = "No hay entrenamientos para esa fecha.",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color(0xFF607D8B)
                            )
                        }

                        historialFiltrado.take(10).forEach { registro ->

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
                                                text = registro.fecha,
                                                style = MaterialTheme.typography.titleMedium
                                            )

                                            Spacer(modifier = Modifier.height(6.dp))

                                            Text(
                                                text = "⚖️ ${registro.peso} kg",
                                                color = Color(0xFF607D8B)
                                            )

                                            Text(
                                                text = "🔁 ${registro.repeticionesPorSerie.joinToString(" · ")}",
                                                color = Color(0xFF607D8B)
                                            )

                                            Spacer(modifier = Modifier.height(12.dp))

                                            Button(
                                                onClick = {
                                                    registroPendienteEliminar.value = registro
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