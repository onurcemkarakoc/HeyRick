package com.onurcemkarakoc.heyrick

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onurcemkarakoc.core.common.ui.theme.HeyRickTheme
import com.onurcemkarakoc.core.common.utils.currentRoute
import com.onurcemkarakoc.core.data.Language
import com.onurcemkarakoc.core.data.Theme
import com.onurcemkarakoc.feature.details.CharacterDetailsScreen
import com.onurcemkarakoc.feature.episode.CharacterEpisodeScreen
import com.onurcemkarakoc.feature.list.CharacterListScreen
import com.onurcemkarakoc.feature.settings.SettingsScreen
import com.onurcemkarakoc.feature.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val items = listOf(Screen.CharacterList, Screen.Settings)
    private val nonShowedBottomBarScreens = listOf(Screen.CharacterDetails, Screen.CharacterEpisode)
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LaunchedEffect(key1 = Unit) {
                settingsViewModel.getAllSettings()
            }

            val navController: NavHostController = rememberNavController()
            var selectedIndex by remember {
                mutableIntStateOf(0)
            }
            val state = settingsViewModel.state.collectAsState()
            val context = LocalContext.current
            setLocale(context, state.value.language)

            HeyRickTheme(darkTheme = state.value.theme == Theme.DARK) {
                Scaffold(
                    bottomBar = {
                        val currentRoute = currentRoute(navController)
                        val hasBottomBar = nonShowedBottomBarScreens.map { it.route }
                            .none { currentRoute?.contains(it) == true }
                        if (hasBottomBar) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.primary,
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
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.secondary,
                                            selectedTextColor = MaterialTheme.colorScheme.secondary,
                                            unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                                            unselectedTextColor = MaterialTheme.colorScheme.tertiary,
                                            indicatorColor = Color.Transparent,
                                        )

                                    )
                                }
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
                            SettingsScreen(settingsViewModel)
                        }
                    }
                }
            }
        }
    }

    private fun setLocale(context: Context, language: Language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(language.localeCode)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(language.localeCode)
            )
        }

    }
}
