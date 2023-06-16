package com.example.muplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.muplayer.ui.theme.MUPlayerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MUPlayerTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) {
                            PlayerApp(navController = navController, onItemClick = { playerId ->
                                navController.navigate("${Screen.Detail.route}/$playerId")
                            })
                        }

                        composable(Screen.About.route) {
                            AboutScreen()
                        }

                        composable(
                            route = "${Screen.Detail.route}/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val playerId = backStackEntry.arguments?.getString("id")
                            PlayerDetailScreen(playerId = playerId.orEmpty())
                        }
                    }
                }
            }
        }
    }
}