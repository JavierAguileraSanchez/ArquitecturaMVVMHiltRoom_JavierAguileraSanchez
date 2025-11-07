package com.example.mvvm

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvm.ui.HomeScreen
import com.example.mvvm.ui.ListadoIncidenciasScreen
import com.example.mvvm.ui.RegistroIncidenciaScreen
import com.example.mvvm.viewmodel.IncidenciasViewModel

object Route {
    const val HOME = "home"
    const val LIST = "list"
    const val REGISTER = "register"
    const val REGISTER_WITH_ID = "register/{incidenciaId}"
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val vm: IncidenciasViewModel = hiltViewModel()

    NavHost(navController = nav, startDestination = Route.HOME) {
        composable(Route.HOME) {
            HomeScreen(
                onGoList = { nav.navigate(Route.LIST) },
                onGoRegister = { nav.navigate(Route.REGISTER) }
            )
        }
        composable(Route.LIST) {
            ListadoIncidenciasScreen(
                vm = vm,
                onAdd = { nav.navigate(Route.REGISTER) },
                onEdit = { id -> nav.navigate("register/$id") }
            )
        }
        composable(Route.REGISTER) {
            RegistroIncidenciaScreen(
                vm = vm,
                incidenciaId = null,
                onDone = { nav.popBackStack() }
            )
        }
        composable(
            Route.REGISTER_WITH_ID,
            arguments = listOf(navArgument("incidenciaId") { type = NavType.LongType })
        ) { backStack ->
            val id = backStack.arguments?.getLong("incidenciaId")
            RegistroIncidenciaScreen(
                vm = vm,
                incidenciaId = id,
                onDone = { nav.popBackStack() }
            )
        }
    }
}
