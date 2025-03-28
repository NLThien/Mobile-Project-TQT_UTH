package com.example.travel_application.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.travel_application.ui.screen.LocationScreen
import com.example.travel_application.ui.screen.MainScreen
import com.example.travel_application.ui.screen.NotificationsScreen
import com.example.travel_application.ui.screen.LoginScreen

@Composable
fun TravelApp(navController: NavHostController = rememberNavController()) {

    Scaffold(
        bottomBar = {
            if ( navController.currentBackStackEntry?.destination?.route != "login") {
                AppBottomNavigation(navController)
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            AppBottomNavigation(navController)
        }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") { MainScreen(navController) }

            composable("location") { LocationScreen(navController) }

            composable("notifications") { NotificationsScreen(navController) }

            composable("login") { LoginScreen(navController) }
        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(
        NavItem(
            route = "main",
            icon = Icons.Filled.Home,
            label = "Home"
        ),
        NavItem(
            route = "location",
            icon = Icons.Filled.LocationOn,
            label = "Location"
        ),
        NavItem(
            route = "notifications",
            icon = Icons.Filled.Notifications,
            label = "Notifications"
        )
    )

    NavigationBar(
        containerColor = Color(0xFF4CAF50),
        tonalElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(
                topStart = 40.dp,
                topEnd = 40.dp,
                bottomStart = 40.dp,
                bottomEnd = 40.dp
            ))
    ) {
        navItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) Color.White else Color.Black
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (currentRoute == item.route) Color.White else Color.Black
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color(0xFF388E3C)
                )
            )
        }
    }
}

data class NavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)

@Preview(showBackground = true)
@Composable
fun PreviewTravelApp() {
    TravelApp()
}