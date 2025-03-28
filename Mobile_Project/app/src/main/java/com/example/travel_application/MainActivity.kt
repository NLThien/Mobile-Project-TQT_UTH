package com.example.travel_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.setContent
import com.example.travel_application.ui.navigation.TravelApp
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import com.example.travel_application.ui.screen.LocationScreen
import com.example.travel_application.ui.screen.MainScreen
import com.example.travel_application.ui.screen.NotificationsScreen
import androidx.navigation.compose.NavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TravelApp(navController = navController)
        }
    }
}