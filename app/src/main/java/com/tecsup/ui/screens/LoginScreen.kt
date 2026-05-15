package com.tecsup.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tecsup.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: UsuarioViewModel,
    onLoginSuccess: (Int) -> Unit,
    onIrRegistro: () -> Unit
) {

    val usuario by viewModel.usuario.collectAsState()
    val loginError by viewModel.loginError.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    var nombreUsuario by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(usuario) {

        usuario?.let {

            onLoginSuccess(it.id)
        }
    }

    LaunchedEffect(loginError) {

        if (loginError) {

            scope.launch {

                snackbarHostState.showSnackbar(
                    "Usuario o contraseña incorrectos"
                )

                viewModel.limpiarLoginError()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "GymTracker Pro",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Inicia sesión para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = {
                    nombreUsuario = it
                },
                label = {
                    Text("Usuario")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Contraseña")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                visualTransformation =
                    PasswordVisualTransformation(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),

                modifier = Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {

                    viewModel.login(
                        nombreUsuario,
                        password
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                enabled =
                    nombreUsuario.isNotBlank() &&
                            password.isNotBlank()
            ) {

                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onIrRegistro
            ) {

                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}