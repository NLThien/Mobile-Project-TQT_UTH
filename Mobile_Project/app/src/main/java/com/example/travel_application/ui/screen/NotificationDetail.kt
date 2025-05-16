package com.example.travel_application.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.filled.ArrowBack
import com.example.travel_application.accessibility.rememberMessageBoxState
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_application.viewmodel.NotificationViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailScreen(
    navController: NavController,
    notificationId: String?
) {
    // Kiểm tra null ngay từ đầu
    if (notificationId.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Không tìm thấy thông báo")
        }
        navController.popBackStack() // Tự động quay lại nếu không có ID
        return
    }

    val viewModel: NotificationViewModel = hiltViewModel()
    val notification by viewModel.notificationDetail.collectAsState()
    val transactionDoc by viewModel.transactionDocument.collectAsState()

    // Tạo booking từ transaction document nếu có
    val relatedBooking = remember(transactionDoc) {
        transactionDoc?.let { viewModel.createBookingFromDocument(it) }
    }

    // Load dữ liệu
    LaunchedEffect(notificationId) {
        if (!notificationId.isNullOrEmpty()) {
            viewModel.loadNotificationDetail(notificationId)
        }
    }

    if (notification == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Không tìm thấy thông báo")
        }
        return
    }

    val currentNotification = notification!!
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getNotificationBackground(currentNotification.type))
    ) {
        // Header với nút back
        TopAppBar(
            title = { Text("Chi tiết thông báo") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors() // Fix experimental API warning
        )

        // Card thông báo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Fix experimental API warning
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = currentNotification.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(currentNotification.time, color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                Text(currentNotification.message, style = MaterialTheme.typography.bodyLarge)

                // Hiển thị thông tin tour liên quan nếu có
                relatedBooking?.let { booking ->
                    Spacer(modifier = Modifier.height(24.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Thông tin tour liên quan:", fontWeight = FontWeight.Bold)
                    Text("• Mã tour: ${booking.tourId}")
                    Text("• Ngày đi: ${formatDate(booking.travelDate)}")
                    Text("• Trạng thái: ${booking.status.name}")
                }

                // Action buttons
                when (currentNotification.type) {
                    NotificationType.TOUR_REMINDER -> {
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                //
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Xem chi tiết tour")
                        }
                    }
                    NotificationType.URGENT -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Liên hệ hỗ trợ: 1900 1234",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

fun formatDate(date: Date): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
}

fun getNotificationBackground(type: NotificationType): Color {
    return when (type) {
        NotificationType.TOUR_REMINDER -> Color(0xFFE3F2FD)
        NotificationType.PAYMENT_SUCCESS -> Color(0xFFE8F5E9)
        NotificationType.THANK_YOU -> Color(0xFFFFF8E1)
        NotificationType.URGENT -> Color(0xFFFFEBEE)
    }
}