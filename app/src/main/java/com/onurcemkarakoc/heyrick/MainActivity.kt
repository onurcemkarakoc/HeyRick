package com.onurcemkarakoc.heyrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onurcemkarakoc.heyrick.ui.theme.HeyRickTheme
import com.onurcemkarakoc.network.KtorClient

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            HeyRickTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "character_detail") {
                        composable("character_detail") {
                            CharacterDetailsScreen(ktorClient = ktorClient, characterId = 125) {
                                navController.navigate("character_episodes/$it")
                            }
                        }
                        composable(
                            route ="character_episodes/{characterId}",
                            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                        ){ backStackEntry ->
                            CharacterEpisodeScreen(characterId = backStackEntry.arguments?.getInt("characterId") ?: 0)
                        }
                    }

                }
            }
        }
    }
}