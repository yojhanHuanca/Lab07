package com.tecsup.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tecsup.data.entity.Usuario
import com.tecsup.viewmodel.UsuarioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: UsuarioViewModel,
    onVolver: () -> Unit
) {
    val registroExitoso by viewModel.registroExitoso.collectAsState()

    var nombreCompleto by remember { mutableStateOf("") }
    var nombreUsuario  by remember { mutableStateOf("") }
    var email          by remember { mutableStateOf("") }
    var edad           by remember { mutableStateOf("") }
    var password       by remember { mutableStateOf("") }

    var emailError        by remember { mutableStateOf(false) }
    var usuarioExisteError by remember { mutableStateOf(false) }

    // Volver al login cuando el registro sea exitoso
    LaunchedEffect(registroExitoso) {
        if (registroExitoso) {
            viewModel.resetRegistro()
            onVolver()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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

            // Nombre completo
            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = { nombreCompleto = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Usuario
            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = {
                    nombreUsuario = it
                    usuarioExisteError = false
                },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = usuarioExisteError,
                supportingText = {
                    if (usuarioExisteError) Text("Este usuario ya existe")
                }
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = emailError,
                supportingText = {
                    if (emailError) Text("Formato de email inválido")
                }
            )

            // Edad
            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón registrar
            Button(
                onClick = {
                    // Validar email
                    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
                    if (!emailRegex.matches(email.trim())) {
                        emailError = true
                        return@Button
                    }

                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val fechaActual = sdf.format(Date())

                    val nuevoUsuario = Usuario(
                        nombreUsuario   = nombreUsuario.trim(),
                        password        = password,
                        nombreCompleto  = nombreCompleto.trim(),
                        email           = email.trim(),
                        edad            = edad.toIntOrNull() ?: 0,
                        fechaRegistro   = fechaActual
                    )

                    viewModel.registrar(nuevoUsuario) {
                        usuarioExisteError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = nombreCompleto.isNotBlank() && nombreUsuario.isNotBlank() &&
                        email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Registrarme")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}