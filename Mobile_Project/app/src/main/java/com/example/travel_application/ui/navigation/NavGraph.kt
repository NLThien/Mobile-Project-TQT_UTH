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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.travel_application.StackPages

@Composable     // quản lý thanh bar chính
fun AppNavigation(navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "StackPages") {
        composable("StackPages") {
            StackPages(navController)
        }

        composable("main") {
            MainScreen(navController)
        }
        composable("location") {
            LocationScreen(navController)
        }
        composable("notification") {
            NotificationsScreen(navController)
        }
    }
}

@Composable     // ui của bottom navigation
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

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .wrapContentSize(Alignment.BottomCenter)
            .padding(bottom = 20.dp), // Chỉ để padding bottom
        contentAlignment = Alignment.BottomCenter // Căn giữa theo chiều ngang
    ) {
        NavigationBar(
            containerColor = Color(0xFF4CAF50),
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth(0.9f)         // rộng 90%
                .height(100.dp)
                .padding(horizontal = 16.dp)
                .padding(bottom = 20.dp)
                .wrapContentSize(Alignment.BottomCenter)
                .clip(RoundedCornerShape(40.dp)),  // bo góc cho thanh
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
}

data class NavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAppBottomNavigation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomCenter)
    ) {
        val navController = rememberNavController()

        AppBottomNavigation(navController = navController)
    }
}