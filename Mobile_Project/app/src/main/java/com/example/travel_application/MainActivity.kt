package com.example.travel_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.travel_application.ui.resources.AppTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.Box
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_application.ui.screen.LoginScreen
import com.example.travel_application.ui.screen.LocationScreen
import com.example.travel_application.ui.screen.MainScreen
import com.example.travel_application.ui.screen.NotificationsScreen
import com.google.firebase.auth.FirebaseAuth
import com.example.travel_application.ui.navigation.AppNavigation
import com.example.travel_application.ui.navigation.AppBottomNavigation
import com.example.travel_application.ui.screen.NotificationDetailScreen
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.travel_application.ui.screen.RegisterScreen
import com.example.travel_application.ui.screen.DetailPlacedInMapScreen
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.example.travel_application.viewmodel.TravelPlaceViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travel_application.accessibility.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                TravelApp()
            }
        }
    }
}

@Composable
fun TravelApp() {
    val navController = rememberNavController()
    val isLoggedIn = remember { mutableStateOf(false) }
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn.value) "main" else "login",
    ) {
        composable("login") {
            val authViewModel: AuthViewModel = viewModel()
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    isLoggedIn.value = true
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }

        composable("resigter") {
            val authViewModel: AuthViewModel = viewModel()
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = {
                    isLoggedIn.value = true
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            MainScreenWithBottomBar(navController)
        }

        composable("location") {
            LocationScreenWithBottomBar(navController)
        }


        composable("notifications") {
            NotificationsScreenWithBottomBar(navController)
        }

        composable(
            route = "notificationDetail/{notificationId}",
            arguments = listOf(
                navArgument("notificationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            NotificationDetailScreen(
                navController = navController,
                notificationId = backStackEntry.arguments?.getInt("notificationId")
            )
        }

        composable("place_detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            DetailPlacedInMapScreen(navController,placeId = placeId)
        }
    }
}

@Composable
fun StackPages(navController: NavController){
    var currentScreen by remember { mutableStateOf("Login") }
    var authViewModel: AuthViewModel = viewModel()
    when(currentScreen){
        "Login" -> LoginScreen(
            navController,
            onLoginSuccess = { currentScreen = "Main" },
            authViewModel = authViewModel
        )
    }
}

@Composable
fun MainScreenWithBottomBar(navController: NavHostController) {
    Scaffold(
        bottomBar = { AppBottomNavigation(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainScreen(navController)
        }
    }
}

@Composable
fun LocationScreenWithBottomBar(navController: NavHostController) {
    Scaffold(
        bottomBar = { AppBottomNavigation(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LocationScreen(navController)
        }
    }
}

@Composable
fun NotificationsScreenWithBottomBar(navController: NavHostController) {
    Scaffold(
        bottomBar = { AppBottomNavigation(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NotificationsScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    var authViewModel: AuthViewModel = viewModel()
    AppTheme {
        LoginScreen(navController = rememberNavController(), onLoginSuccess = {true}, authViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    AppTheme {
        LocationScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    AppTheme {
        NotificationsScreen(navController = rememberNavController())
    }
}