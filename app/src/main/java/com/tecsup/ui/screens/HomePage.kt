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
import androidx.compose.ui.graphics.vector.ImageVector
import com.tecsup.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    usuarioId: Int,
    viewModel: UsuarioViewModel,
    onIrAgregar: () -> Unit,
    onIrLista: () -> Unit,
    onIrPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {

    val usuario by viewModel.usuario.collectAsState()

    val drawerState = rememberDrawerState(
        DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(usuarioId) {

        viewModel.cargarUsuario(usuarioId)
    }

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = CircleShape,
                        color =
                            MaterialTheme.colorScheme
                                .primaryContainer
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text =
                                    usuario?.nombreCompleto
                                        ?.split(" ")
                                        ?.take(2)
                                        ?.joinToString("") {
                                            it.first().uppercase()
                                        } ?: "GY",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onPrimaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = usuario?.nombreCompleto ?: "",
                        style =
                            MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = usuario?.email ?: "",
                        style =
                            MaterialTheme.typography.bodySmall,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                DrawerItem(
                    icon = Icons.Default.Home,
                    text = "Inicio",
                    selected = true
                ) {
                    scope.launch {
                        drawerState.close()
                    }
                }

                DrawerItem(
                    icon = Icons.Default.Add,
                    text = "Agregar rutina"
                ) {
                    scope.launch {
                        drawerState.close()
                    }

                    onIrAgregar()
                }

                DrawerItem(
                    icon = Icons.Default.List,
                    text = "Mis rutinas"
                ) {
                    scope.launch {
                        drawerState.close()
                    }

                    onIrLista()
                }

                DrawerItem(
                    icon = Icons.Default.Person,
                    text = "Mi perfil"
                ) {
                    scope.launch {
                        drawerState.close()
                    }

                    onIrPerfil()
                }

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider()

                DrawerItem(
                    icon = Icons.Default.ExitToApp,
                    text = "Cerrar sesión"
                ) {
                    onCerrarSesion()
                }
            }
        }

    ) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {
                        Text("GymTracker Pro")
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {

                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    },

                    actions = {

                        IconButton(
                            onClick = onIrPerfil
                        ) {

                            Icon(
                                Icons.Default.Person,
                                contentDescription = null
                            )
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

                verticalArrangement =
                    Arrangement.spacedBy(16.dp)

            ) {

                Text(
                    text = "Hola,",
                    style =
                        MaterialTheme.typography.bodyLarge,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )

                Text(
                    text = usuario?.nombreCompleto ?: "",
                    style =
                        MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Add,
                        titulo = "Agregar\nrutina",
                        onClick = onIrAgregar
                    )

                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.List,
                        titulo = "Mis\nrutinas",
                        onClick = onIrLista
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Person,
                        titulo = "Mi\nperfil",
                        onClick = onIrPerfil
                    )

                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.ExitToApp,
                        titulo = "Cerrar\nsesión",
                        onClick = onCerrarSesion
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {

    NavigationDrawerItem(

        icon = {
            Icon(icon, contentDescription = null)
        },

        label = {
            Text(text)
        },

        selected = selected,

        onClick = onClick
    )
}

@Composable
private fun DashboardCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    titulo: String,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,

        modifier = modifier.height(110.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement =
                Arrangement.Center,

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),

                tint =
                    MaterialTheme.colorScheme
                        .onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = titulo,

                style =
                    MaterialTheme.typography.labelLarge,

                color =
                    MaterialTheme.colorScheme
                        .onPrimaryContainer
            )
        }
    }
}