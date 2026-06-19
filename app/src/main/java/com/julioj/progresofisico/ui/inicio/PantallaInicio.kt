package com.julioj.progresofisico.ui.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.julioj.progresofisico.ui.theme.TurquesaPrincipal
import com.julioj.progresofisico.ui.viewmodel.ProgresoUsuarioViewModel

@Composable
fun PantallaInicio(
    progresoUsuarioViewModel: ProgresoUsuarioViewModel,
    navegarAEjercicios: () -> Unit,
    navegarAEntrenamiento: () -> Unit,
    navegarAActividad: () -> Unit
) {

    LaunchedEffect(Unit) {
        progresoUsuarioViewModel.cargarProgreso()
    }

    val progresoUsuario = progresoUsuarioViewModel.progresoUsuario.value

    val nivelUsuario = progresoUsuario.nivel
    val experienciaActual = progresoUsuario.experienciaActual
    val experienciaNecesaria = progresoUsuarioViewModel.calcularExperienciaNecesaria(nivelUsuario)
    val progresoExperiencia = (experienciaActual.toFloat() / experienciaNecesaria.toFloat())
        .coerceIn(0f, 1f)
    val porcentajeExperiencia = (progresoExperiencia * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Progreso Físico",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Cada entrenamiento cuenta",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF607D8B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(0.90f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nivel $nivelUsuario",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TurquesaPrincipal
                )

                Text(
                    text = "$porcentajeExperiencia% completado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF607D8B)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(28.dp)
                            .clip(RoundedCornerShape(14.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(28.dp)
                                .background(TurquesaPrincipal.copy(alpha = 0.20f))
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progresoExperiencia)
                                .height(28.dp)
                                .background(TurquesaPrincipal)
                        )
                    }

                    Text(
                        text = "$experienciaActual / $experienciaNecesaria XP",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navegarAEjercicios()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "🏋️ Mis ejercicios")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navegarAEntrenamiento()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "📝 Registrar entrenamiento")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navegarAActividad()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "🚶 Actividad diaria")
        }
    }
}