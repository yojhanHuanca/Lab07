package com.tecsup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tecsup.data.database.AppDatabase
import com.tecsup.navigation.AppNavigation
import com.tecsup.ui.theme.GymtrackerTheme
import com.tecsup.viewmodel.RoutineViewModel
import com.tecsup.viewmodel.UsuarioViewModel

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

    val usuarioVM: UsuarioViewModel = viewModel(
        factory = simpleFactory {
            UsuarioViewModel(database.usuarioDao())
        }
    )

    val routineVM: RoutineViewModel = viewModel(
        factory = simpleFactory {
            RoutineViewModel(database.rutinaDao())
        }
    )

    val navController = rememberNavController()

    AppNavigation(
        navController = navController,
        usuarioVM = usuarioVM,
        routineVM = routineVM
    )
}



fun <T : ViewModel> simpleFactory(
    create: () -> T
): ViewModelProvider.Factory {

    return object : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <VM : ViewModel> create(
            modelClass: Class<VM>
        ): VM {
            return create() as VM
        }
    }
}