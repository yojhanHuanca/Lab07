package com.tecsup.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.data.entity.Rutina
import com.tecsup.viewmodel.RoutineViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object RoutineScreens {

    // ─────────────────────────────────────────
    // AGREGAR RUTINA (CREATE)
    // ─────────────────────────────────────────
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AgregarRutina(
        usuarioId: Int,
        viewModel: RoutineViewModel,
        onGuardado: () -> Unit
    ) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaActual = sdf.format(Date())

        var ejercicio      by remember { mutableStateOf("") }
        var grupoMuscular  by remember { mutableStateOf("") }
        var series         by remember { mutableStateOf("") }
        var repeticiones   by remember { mutableStateOf("") }
        var pesoKg         by remember { mutableStateOf("") }
        var fecha          by remember { mutableStateOf(fechaActual) }
        var expandedDropdown by remember { mutableStateOf(false) }

        val gruposMusculares = listOf(
            "Pecho", "Espalda", "Hombros",
            "Bíceps", "Tríceps", "Brazo",
            "Pierna", "Glúteos", "Abdomen", "Cardio"
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nueva rutina") },
                    navigationIcon = {
                        IconButton(onClick = onGuardado) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val rutina = Rutina(
                                    usuarioId     = usuarioId,
                                    ejercicio     = ejercicio.trim(),
                                    grupoMuscular = grupoMuscular,
                                    series        = series.toIntOrNull() ?: 0,
                                    repeticiones  = repeticiones.toIntOrNull() ?: 0,
                                    pesoKg        = pesoKg.toDoubleOrNull() ?: 0.0,
                                    fecha         = fecha
                                )
                                viewModel.insertar(rutina) { onGuardado() }
                            },
                            enabled = ejercicio.isNotBlank() && grupoMuscular.isNotBlank()
                                    && series.isNotBlank() && repeticiones.isNotBlank()
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Guardar")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Ejercicio
                OutlinedTextField(
                    value = ejercicio,
                    onValueChange = { ejercicio = it },
                    label = { Text("Ejercicio") },
                    placeholder = { Text("Press banca") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Grupo muscular (Dropdown)
                ExposedDropdownMenuBox(
                    expanded = expandedDropdown,
                    onExpandedChange = { expandedDropdown = it }
                ) {
                    OutlinedTextField(
                        value = grupoMuscular,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Grupo muscular") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false }
                    ) {
                        gruposMusculares.forEach { grupo ->
                            DropdownMenuItem(
                                text = { Text(grupo) },
                                onClick = {
                                    grupoMuscular = grupo
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }

                // Series y Repeticiones en fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = series,
                        onValueChange = { series = it },
                        label = { Text("Series") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = repeticiones,
                        onValueChange = { repeticiones = it },
                        label = { Text("Repeticiones") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                // Peso
                OutlinedTextField(
                    value = pesoKg,
                    onValueChange = { pesoKg = it },
                    label = { Text("Peso (kg)") },
                    placeholder = { Text("60.5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Fecha
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val rutina = Rutina(
                            usuarioId     = usuarioId,
                            ejercicio     = ejercicio.trim(),
                            grupoMuscular = grupoMuscular,
                            series        = series.toIntOrNull() ?: 0,
                            repeticiones  = repeticiones.toIntOrNull() ?: 0,
                            pesoKg        = pesoKg.toDoubleOrNull() ?: 0.0,
                            fecha         = fecha
                        )
                        viewModel.insertar(rutina) { onGuardado() }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = ejercicio.isNotBlank() && grupoMuscular.isNotBlank()
                            && series.isNotBlank() && repeticiones.isNotBlank()
                ) {
                    Text("Guardar rutina")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // ─────────────────────────────────────────
    // LISTA DE RUTINAS (READ + DELETE)
    // ─────────────────────────────────────────
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ListaRutinas(
        usuarioId: Int,
        viewModel: RoutineViewModel,
        onIrDetalle: (Int) -> Unit,
        onIrAgregar: () -> Unit,
        onVolver: () -> Unit
    ) {
        val rutinas by viewModel.rutinas.collectAsState()
        var rutinaAEliminar by remember { mutableStateOf<Rutina?>(null) }

        LaunchedEffect(usuarioId) {
            viewModel.cargarRutinas(usuarioId)
        }

        // AlertDialog de confirmación para eliminar
        rutinaAEliminar?.let { rutina ->
            AlertDialog(
                onDismissRequest = { rutinaAEliminar = null },
                title = { Text("Eliminar rutina") },
                text = { Text("¿Seguro que deseas eliminar \"${rutina.ejercicio}\"?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.eliminar(rutina, usuarioId)
                            rutinaAEliminar = null
                        }
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { rutinaAEliminar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mis rutinas") },
                    navigationIcon = {
                        IconButton(onClick = onVolver) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        IconButton(onClick = onIrAgregar) {
                            Icon(Icons.Default.Search, contentDescription = "Buscar")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onIrAgregar) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar rutina")
                }
            }
        ) { padding ->
            if (rutinas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No tienes rutinas aún",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = onIrAgregar) {
                            Text("Agregar primera rutina")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(rutinas) { rutina ->
                        RutinaItem(
                            rutina = rutina,
                            onEditar = { onIrDetalle(rutina.id) },
                            onEliminar = { rutinaAEliminar = rutina }
                        )
                    }
                }
            }
        }
    }

    // ─────────────────────────────────────────
    // DETALLE / EDITAR RUTINA (UPDATE)
    // ─────────────────────────────────────────
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DetalleRutina(
        rutinaId: Int,
        usuarioId: Int,
        viewModel: RoutineViewModel,
        onGuardado: () -> Unit,
        onEliminado: () -> Unit
    ) {
        val rutinaActual by viewModel.rutinaActual.collectAsState()
        var mostrarDialogEliminar by remember { mutableStateOf(false) }

        var ejercicio     by remember { mutableStateOf("") }
        var grupoMuscular by remember { mutableStateOf("") }
        var series        by remember { mutableStateOf("") }
        var repeticiones  by remember { mutableStateOf("") }
        var pesoKg        by remember { mutableStateOf("") }
        var fecha         by remember { mutableStateOf("") }
        var expandedDropdown by remember { mutableStateOf(false) }

        val gruposMusculares = listOf(
            "Pecho", "Espalda", "Hombros",
            "Bíceps", "Tríceps", "Brazo",
            "Pierna", "Glúteos", "Abdomen", "Cardio"
        )

        // Cargar rutina y llenar los campos
        LaunchedEffect(rutinaId) {
            viewModel.cargarRutinaPorId(rutinaId)
        }

        LaunchedEffect(rutinaActual) {
            rutinaActual?.let {
                ejercicio     = it.ejercicio
                grupoMuscular = it.grupoMuscular
                series        = it.series.toString()
                repeticiones  = it.repeticiones.toString()
                pesoKg        = it.pesoKg.toString()
                fecha         = it.fecha
            }
        }

        // Dialog confirmar eliminar
        if (mostrarDialogEliminar) {
            AlertDialog(
                onDismissRequest = { mostrarDialogEliminar = false },
                title = { Text("Eliminar rutina") },
                text = { Text("¿Seguro que deseas eliminar esta rutina?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            rutinaActual?.let {
                                viewModel.eliminar(it, usuarioId)
                            }
                            onEliminado()
                        }
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogEliminar = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Editar rutina #$rutinaId") },
                    navigationIcon = {
                        IconButton(onClick = onGuardado) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        // Ícono basura en TopAppBar
                        IconButton(onClick = { mostrarDialogEliminar = true }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error
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
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                // Chip informativo
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Modificando registro existente",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                // Ejercicio
                OutlinedTextField(
                    value = ejercicio,
                    onValueChange = { ejercicio = it },
                    label = { Text("Ejercicio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Grupo muscular (Dropdown)
                ExposedDropdownMenuBox(
                    expanded = expandedDropdown,
                    onExpandedChange = { expandedDropdown = it }
                ) {
                    OutlinedTextField(
                        value = grupoMuscular,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Grupo muscular") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false }
                    ) {
                        gruposMusculares.forEach { grupo ->
                            DropdownMenuItem(
                                text = { Text(grupo) },
                                onClick = {
                                    grupoMuscular = grupo
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }

                // Series y Repeticiones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = series,
                        onValueChange = { series = it },
                        label = { Text("Series") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = repeticiones,
                        onValueChange = { repeticiones = it },
                        label = { Text("Repeticiones") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                // Peso
                OutlinedTextField(
                    value = pesoKg,
                    onValueChange = { pesoKg = it },
                    label = { Text("Peso (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Fecha
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        rutinaActual?.let { original ->
                            val actualizada = original.copy(
                                ejercicio     = ejercicio.trim(),
                                grupoMuscular = grupoMuscular,
                                series        = series.toIntOrNull() ?: original.series,
                                repeticiones  = repeticiones.toIntOrNull() ?: original.repeticiones,
                                pesoKg        = pesoKg.toDoubleOrNull() ?: original.pesoKg,
                                fecha         = fecha
                            )
                            viewModel.actualizar(actualizada) { onGuardado() }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = ejercicio.isNotBlank() && grupoMuscular.isNotBlank()
                ) {
                    Text("Actualizar cambios")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ─────────────────────────────────────────
// COMPONENTE: Item de rutina en la lista
// ─────────────────────────────────────────
@Composable
private fun RutinaItem(
    rutina: Rutina,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = rutina.ejercicio,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = rutina.grupoMuscular,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${rutina.series} series × ${rutina.repeticiones} reps · ${rutina.pesoKg} kg · ${rutina.fecha}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // Ícono editar
            IconButton(onClick = onEditar) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            // Ícono eliminar
            IconButton(onClick = onEliminar) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}