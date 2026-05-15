package com.tecsup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tecsup.data.database.AppDatabase
import com.tecsup.navigation.AppNavigation
import com.tecsup.ui.theme.GymtrackerTheme
import com.tecsup.viewmodel.RoutineViewModel
import com.tecsup.viewmodel.UsuarioViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymtrackerTheme {
                GymtrackerApp()
            }
        }
    }
}

@Composable
fun GymtrackerApp() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val usuarioDao = database.usuarioDao()
    val rutinaDao = database.rutinaDao()

    // Usar una Factory básica para inyectar los DAOs en los ViewModels
    val usuarioVM: UsuarioViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UsuarioViewModel(usuarioDao) as T
            }
        }
    )

    val routineVM: RoutineViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RoutineViewModel(rutinaDao) as T
            }
        }
    )

    val navController = rememberNavController()
    AppNavigation(
        navController = navController,
        usuarioVM = usuarioVM,
        routineVM = routineVM
    )
}
