package com.example.pasteleria_mil_sabores.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pasteleria_mil_sabores.ui.screen.CartScreen
import com.example.pasteleria_mil_sabores.ui.screen.HomeScreen
import com.example.pasteleria_mil_sabores.ui.screen.LoginScreen
import com.example.pasteleria_mil_sabores.ui.screen.PerfilScreen
import com.example.pasteleria_mil_sabores.ui.screen.PostScreen
import com.example.pasteleria_mil_sabores.ui.screen.RegistroScreen
import com.example.pasteleria_mil_sabores.ui.screen.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash"){
        composable("splash"){ SplashScreen(navController = navController) }
        composable("login"){ LoginScreen(navController = navController) }
        composable ("registro"){ RegistroScreen(navController = navController) }
        composable ("posts"){ PostScreen(navController = navController) }

        composable("home"){
            HomeScreen(navController = navController, usuarioId = 0)
        }

        composable("home/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toInt() ?: 0
            HomeScreen(navController = navController, usuarioId = usuarioId)
        }

        composable("perfil/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toInt() ?: 0
            PerfilScreen(navController, usuarioId)
        }

        composable("carrito/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toInt() ?: 0
            CartScreen(navController = navController, usuarioId = usuarioId)
        }
    }
}


