package com.onurcemkarakoc.heyrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onurcemkarakoc.core.common.ui.theme.HeyRickTheme
import com.onurcemkarakoc.core.common.ui.theme.MainBackground
import com.onurcemkarakoc.core.common.ui.theme.RickPrimary
import com.onurcemkarakoc.core.common.ui.theme.RickPrimary50
import com.onurcemkarakoc.core.data.KtorClient
import com.onurcemkarakoc.feature.details.CharacterDetailsScreen
import com.onurcemkarakoc.feature.episode.CharacterEpisodeScreen
import com.onurcemkarakoc.feature.list.CharacterListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    val items = listOf(Screen.CharacterList, Screen.Settings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var selectedIndex by remember {
                mutableIntStateOf(0)
            }

            HeyRickTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = MainBackground, windowInsets = WindowInsets(
                                0, 0, 0, 0
                            )
                        ) {
                            items.forEachIndexed { index, screen ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            screen.icon,
                                            modifier = Modifier
                                                .size(24.dp),
                                            contentDescription = null,
                                        )
                                    },
                                    label = {
                                        Text(
                                            stringResource(screen.resourceId),
                                            fontSize = 16.sp,
                                            lineHeight = 16.sp,
                                        )
                                    },
                                    selected = index == selectedIndex,
                                    onClick = {
                                        selectedIndex = index
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = RickPrimary,
                                        selectedTextColor = RickPrimary,
                                        unselectedIconColor = RickPrimary50,
                                        unselectedTextColor = RickPrimary50,
                                        indicatorColor = Color.Transparent,

                                        )

                                )
                            }
                        }
                    }) { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.CharacterList.route
                    ) {

                        composable(route = Screen.CharacterList.route) {
                            CharacterListScreen {
                                navController.navigate("${Screen.CharacterDetails.route}/$it")
                            }
                        }

                        composable(
                            route = "${Screen.CharacterDetails.route}/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            CharacterDetailsScreen(characterId = backStackEntry.arguments?.getInt("characterId")
                                ?: 0,
                                onBackPressedClick = {
                                    navController.navigateUp()
                                }) {
                                navController.navigate("${Screen.CharacterEpisode.route}/$it")
                            }
                        }
                        composable(
                            route = "${Screen.CharacterEpisode.route}/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            CharacterEpisodeScreen(characterId = backStackEntry.arguments?.getInt("characterId")
                                ?: 0,
                                onBackPressedClick = {
                                    navController.navigateUp()
                                })
                        }
                        composable(route = Screen.Settings.route) {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .background(MainBackground),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text("Settings", color = RickPrimary)
                            }
                        }
                    }
                }
            }
        }
    }
}
