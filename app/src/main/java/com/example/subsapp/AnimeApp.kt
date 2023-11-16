package com.example.subsapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.subsapp.ui.navigation.NavigationItem
import com.example.subsapp.ui.navigation.Screen
import com.example.subsapp.ui.screen.cart.CartScreen
import com.example.subsapp.ui.screen.detail.DetailScreen
import com.example.subsapp.ui.screen.home.HomeScreen
import com.example.subsapp.ui.screen.profile.ProfileScreen


@Composable
fun AnimeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        bottomBar = {
            if (currentRoute != Screen.DetailAnime.route) {
                BottomBar(navController)
            }
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
            ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = {animeId ->
                        navController.navigate(Screen.DetailAnime.createRoute(animeId))
                    }
                )
            }
            composable(Screen.Cart.route) {
                val context = LocalContext.current
                CartScreen(onOrderButtonCLicked = {message ->
                   Toast.makeText(context, message ,Toast.LENGTH_SHORT).show()
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.DetailAnime.route,
                arguments = listOf(navArgument("animeId") {
                    type = NavType.LongType
                }),
            ) {
                val id = it.arguments?.getLong("animeId") ?: -1L
                DetailScreen(
                    animeId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar (
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem (
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.Keranjang),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(R.string.profile),
                icon = Icons.Default.Person,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected =  currentRoute == item.screen.route,
                label = { Text(item.title)},
                onClick = {
                          navController.navigate(item.screen.route) {
                              popUpTo(navController.graph.findStartDestination().id) {
                                  saveState = true
                              }
                              restoreState = true
                              launchSingleTop = true
                          }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title ) }
            )
        }
    }
}