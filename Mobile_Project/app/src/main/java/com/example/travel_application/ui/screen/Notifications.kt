package com.example.travel_application.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travel_application.db.Notification
import com.example.travel_application.db.NotificationType
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.auth.FirebaseAuth
import com.example.travel_application.viewmodel.NotificationViewModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import com.example.travel_application.accessibility.AuthViewModel
import com.example.travel_application.accessibility.ServiceLocator
import com.example.travel_application.accessibility.TravelRepository
import com.example.travel_application.db.TravelPlace
import kotlin.let
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.firebase.firestore.DocumentSnapshot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    // Lấy repository từ ServiceLocator
    val travelRepository = remember {
        ServiceLocator.get(TravelRepository::class.java)
    }

    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val currentUser = authViewModel.getCurrentUser() // Lấy thông tin user hiện tại
    val userId = currentUser?.uid

    Log.d("USER_ID_DEBUG", "UserID to query: $userId")

    LaunchedEffect(userId) { // Chạy lại khi userId thay đổi
        notifications = travelRepository.getUserNotifications(userId.toString())
        isLoading = false
    }

    LaunchedEffect(Unit) {
        try {
            notifications = travelRepository.getUserNotifications(userId.toString())
        } catch (e: Exception) {
            error = "Failed to load places: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (userId == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Vui lòng đăng nhập")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Text("Thông báo", style = MaterialTheme.typography.headlineSmall)
        }

        // Danh sách thông báo
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(notifications) { notification ->
                NotificationItem(
                    notification = notification,
                    onNotificationClick = { id ->
                        navController.navigate("notificationDetail/${notification.id}"){
                            launchSingleTop = true
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onNotificationClick: (String) -> Unit = {}
) {
    val backgroundColor = when(notification.type) {
        NotificationType.TOUR_REMINDER -> Color(0xFFE3F2FD)
        NotificationType.PAYMENT_SUCCESS -> Color(0xFFE8F5E9)
        NotificationType.THANK_YOU -> Color(0xFFFFF8E1)
        NotificationType.URGENT -> Color(0xFFFFEBEE)
    }

    val borderColor = when(notification.type) {
        NotificationType.TOUR_REMINDER -> Color(0xFF90CAF9)
        NotificationType.PAYMENT_SUCCESS -> Color(0xFFA5D6A7)
        NotificationType.THANK_YOU -> Color(0xFFFFE082)
        NotificationType.URGENT -> Color(0xFFEF9A9A)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable { onNotificationClick(notification.id) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = notification.time,
                    fontSize = 12.sp,
                    color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = notification.message,
                fontSize = 14.sp,
                color = Color.DarkGray)
        }
    }
}