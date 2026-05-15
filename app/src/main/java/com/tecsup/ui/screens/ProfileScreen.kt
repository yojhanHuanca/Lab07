package com.tecsup.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    usuarioId: Int,
    viewModel: UsuarioViewModel,
    onCerrarSesion: () -> Unit
) {

    val usuario by viewModel.usuario.collectAsState()
    val totalRutinas by viewModel.totalRutinas.collectAsState()
    val volumenTotal by viewModel.volumenTotal.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarUsuario(usuarioId)
        viewModel.cargarEstadisticas(usuarioId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                actions = {
                    IconButton(onClick = onCerrarSesion) {
                        Icon(Icons.Default.ExitToApp, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Avatar
            Surface(
                modifier = Modifier.size(90.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {

                Box(contentAlignment = Alignment.Center) {

                    Text(
                        text = usuario?.nombreCompleto
                            ?.split(" ")
                            ?.take(2)
                            ?.joinToString("") {
                                it.first().uppercase()
                            } ?: "GY",

                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            // Datos usuario
            Text(
                text = usuario?.nombreCompleto ?: "",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "@${usuario?.nombreUsuario ?: ""}",
                color = MaterialTheme.colorScheme.primary
            )

            // Card información
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    InfoItem(
                        icon = Icons.Default.Email,
                        text = usuario?.email ?: ""
                    )

                    InfoItem(
                        icon = Icons.Default.Person,
                        text = "${usuario?.edad ?: 0} años"
                    )

                    InfoItem(
                        icon = Icons.Default.DateRange,
                        text = usuario?.fechaRegistro ?: ""
                    )
                }
            }

            // Estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                StatCard(
                    modifier = Modifier.weight(1f),
                    titulo = "Rutinas",
                    valor = totalRutinas.toString()
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    titulo = "Volumen",
                    valor = String.format("%.1f", volumenTotal)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(Icons.Default.ExitToApp, null)

                Spacer(modifier = Modifier.width(8.dp))

                Text("Cerrar sesión")
            }
        }
    }
}

// =====================================================
// COMPONENTES
// =====================================================

@Composable
fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(text)
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    titulo: String,
    valor: String
) {

    Card(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(titulo)
        }
    }
}