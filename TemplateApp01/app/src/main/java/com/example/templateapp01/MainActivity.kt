package com.example.templateapp01

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.templateapp01.ui.extentions.mainContentPadding
import com.example.templateapp01.ui.favorite.FavoriteScreen
import com.example.templateapp01.ui.home.HomeScreen
import com.example.templateapp01.ui.search.ResultScreen
import com.example.templateapp01.ui.search.SearchScreen
import com.example.templateapp01.ui.theme.TemplateApp01Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
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
                Scaffold(
                    bottomBar = {
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
                        Log.d(TAG, "selectedItem: " + items[selectedItem].routeName)
                        Log.d(TAG, "paddingValues: $it")
                        Log.d(
                            TAG,
                            "calculateStartPadding Ltr: " + it.calculateStartPadding(LayoutDirection.Ltr)
                        )
                        Log.d(
                            TAG,
                            "calculateStartPadding Rtl: " + it.calculateStartPadding(LayoutDirection.Rtl)
                        )
                        Log.d(TAG, "calculateBottomPadding: " + it.calculateBottomPadding())
                        MyAppNavigationGraph(
                            startDestination = items[selectedItem].routeName,
                            modifier = Modifier.mainContentPadding(it)
                        )
                    })
            }
        }
    }

    @Composable
    fun MyAppNavigationGraph(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {

            composable(route = TopDestinations.HomeRoute.routeName, content = {
                HomeScreen(
                    modifier = modifier,
                    navigateToNextScreen = {},
                )
            })

            composable(route = TopDestinations.FavoriteRoute.routeName, content = {
                FavoriteScreen(modifier = modifier)
            })

            // nest navigation
            navigation(
                startDestination = SearchDestinations.TopRoute.routeName,
                route = TopDestinations.SearchRoute.routeName,
            ) {
                composable(
                    route = SearchDestinations.TopRoute.routeName,
                    content = {
                        SearchScreen(
                            modifier = modifier,
                            navigateToNextScreen = { query ->
                                navController.navigate(SearchDestinations.ResultRoute.withArgs(query))
                            },
                        )
                    }
                )
                composable(
                    route = SearchDestinations.ResultRoute.routeName + "/{query}",
                    arguments = listOf(
                        navArgument("query") {
                            type = NavType.StringType
                            defaultValue = "no value given"
                            nullable = false
                        }
                    ),
                    content = {
                        val query = requireNotNull(it.arguments?.getString("query"))
                        Log.d(TAG, "query: $query")
                        ResultScreen(
                            navController = navController,
                            query = query,
                            modifier = modifier
                        )
                    }
                )
            }
        }
    }
}