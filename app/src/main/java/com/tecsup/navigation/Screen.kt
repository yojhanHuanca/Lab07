package com.tecsup.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")

    object Home : Screen("home/{usuarioId}") {
        fun createRoute(usuarioId: Int) = "home/$usuarioId"
    }

    object AddRoutine : Screen("add_routine/{usuarioId}") {
        fun createRoute(usuarioId: Int) = "add_routine/$usuarioId"
    }

    object RoutineList : Screen("routine_list/{usuarioId}") {
        fun createRoute(usuarioId: Int) = "routine_list/$usuarioId"
    }

    object RoutineDetail : Screen("routine_detail/{rutinaId}/{usuarioId}") {
        fun createRoute(rutinaId: Int, usuarioId: Int) = "routine_detail/$rutinaId/$usuarioId"
    }

    object Profile : Screen("profile/{usuarioId}") {
        fun createRoute(usuarioId: Int) = "profile/$usuarioId"
    }
}
