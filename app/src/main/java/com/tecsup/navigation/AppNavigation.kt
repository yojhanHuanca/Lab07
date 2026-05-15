package com.tecsup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tecsup.ui.screens.HomePage
import com.tecsup.ui.screens.LoginScreen
import com.tecsup.ui.screens.ProfileScreen
import com.tecsup.ui.screens.RegisterScreen
import com.tecsup.ui.screens.RoutineScreens
import com.tecsup.viewmodel.RoutineViewModel
import com.tecsup.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    usuarioVM: UsuarioViewModel,
    routineVM: RoutineViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = usuarioVM,
                onLoginSuccess = { usuarioId ->
                    navController.navigate(Screen.Home.createRoute(usuarioId)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onIrRegistro = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = usuarioVM,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
        ) { backStack ->
            val usuarioId = backStack.arguments!!.getInt("usuarioId")
            HomePage(
                usuarioId    = usuarioId,
                viewModel    = usuarioVM,
                onIrAgregar  = { navController.navigate(Screen.AddRoutine.createRoute(usuarioId)) },
                onIrLista    = { navController.navigate(Screen.RoutineList.createRoute(usuarioId)) },
                onIrPerfil   = { navController.navigate(Screen.Profile.createRoute(usuarioId)) },
                onCerrarSesion = {
                    usuarioVM.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.AddRoutine.route,
            arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
        ) { backStack ->
            val usuarioId = backStack.arguments!!.getInt("usuarioId")
            RoutineScreens.AgregarRutina(
                usuarioId  = usuarioId,
                viewModel  = routineVM,
                onGuardado = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.RoutineList.route,
            arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
        ) { backStack ->
            val usuarioId = backStack.arguments!!.getInt("usuarioId")
            RoutineScreens.ListaRutinas(
                usuarioId   = usuarioId,
                viewModel   = routineVM,
                onIrDetalle = { rutinaId ->
                    navController.navigate(Screen.RoutineDetail.createRoute(rutinaId, usuarioId))
                },
                onIrAgregar = { navController.navigate(Screen.AddRoutine.createRoute(usuarioId)) },

                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.RoutineDetail.route,
            arguments = listOf(
                navArgument("rutinaId")  { type = NavType.IntType },
                navArgument("usuarioId") { type = NavType.IntType }
            )
        ) { backStack ->
            val rutinaId  = backStack.arguments!!.getInt("rutinaId")
            val usuarioId = backStack.arguments!!.getInt("usuarioId")
            RoutineScreens.DetalleRutina(
                rutinaId   = rutinaId,
                usuarioId  = usuarioId,
                viewModel  = routineVM,
                onGuardado  = { navController.popBackStack() },
                onEliminado = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
        ) { backStack ->
            val usuarioId = backStack.arguments!!.getInt("usuarioId")
            ProfileScreen(
                usuarioId      = usuarioId,
                viewModel      = usuarioVM,
                onCerrarSesion = {
                    usuarioVM.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
