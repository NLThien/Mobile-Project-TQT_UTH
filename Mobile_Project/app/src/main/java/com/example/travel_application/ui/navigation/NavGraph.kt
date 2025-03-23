package com.example.travel_application.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travel_application.ui.screen.MainScreen
import com.example.travel_application.ui.screen.LocationScreen
import com.example.travel_application.ui.screen.NotificationsScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.getValue

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("location") { LocationScreen(navController) }
        composable("notifications") { NotificationsScreen(navController) }
    }

    BottomNavigationBar(navController)
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomNavigation {
        BottomNavigationItem(
            icon = { Icons.Filled.Home },
            label = { "Home" },
            selected = currentRoute == "main",
            onClick = {
                navController.navigate("main") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icons.Filled.LocationOn },
            label = { "Location" },
            selected = currentRoute == "location",
            onClick = {
                navController.navigate("location") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = { Icons.Filled.Notifications },
            label = { "Notifications" },
            selected = currentRoute == "notifications",
            onClick = {
                navController.navigate("notifications") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}