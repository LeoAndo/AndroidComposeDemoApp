package com.example.templateapp01

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.templateapp01.ui.favorite.FavoriteScreen
import com.example.templateapp01.ui.home.HomeScreen
import com.example.templateapp01.ui.search.ResultScreen
import com.example.templateapp01.ui.search.SearchScreen
import com.example.templateapp01.ui.theme.TemplateApp01Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        const val LOG_TAG = "MainActivity"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TemplateApp01Theme {
                var selectedItem by remember { mutableStateOf(0) }
                val items = listOf(
                    TopDestinations.HomeRoute,
                    TopDestinations.FavoriteRoute,
                    TopDestinations.SearchRoute
                )
                Scaffold(bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = { Icon(item.icon, contentDescription = item.label) },
                                label = { Text(item.label) },
                                selected = selectedItem == index,
                                onClick = { selectedItem = index }
                            )
                        }
                    }
                }, content = {
                    Log.d(LOG_TAG, "selectedItem: " + items[selectedItem].routeName)
                    MyAppNavigationGraph(startDestination = items[selectedItem].routeName)
                })
            }
        }
    }

    @Composable
    fun MyAppNavigationGraph(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {

            composable(route = TopDestinations.HomeRoute.routeName) {
                HomeScreen()
            }

            composable(route = TopDestinations.FavoriteRoute.routeName) {
                FavoriteScreen()
            }

            // nest navigation
            navigation(
                startDestination = SearchDestinations.TopRoute.routeName,
                route = TopDestinations.SearchRoute.routeName,
            ) {
                composable(
                    route = SearchDestinations.TopRoute.routeName,
                    content = {
                        SearchScreen(navigateToNextScreen = { id ->
                            navController.navigate(SearchDestinations.ResultRoute.withArgs(id))
                        })
                    }
                )
                composable(
                    route = SearchDestinations.ResultRoute.routeName + "/{pokemonId}",
                    arguments = listOf(
                        navArgument("pokemonId") {
                            type = NavType.StringType
                            defaultValue = "no value given"
                            nullable = false
                        }
                    )
                ) {
                    val pokemonId = requireNotNull(it.arguments?.getString("pokemonId"))
                    Log.d(LOG_TAG, "pokemonId: $pokemonId")
                    ResultScreen(
                        navController = navController,
                        id = pokemonId
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TemplateApp01Theme {
            // MainActivityScreen("Android")
        }
    }
}