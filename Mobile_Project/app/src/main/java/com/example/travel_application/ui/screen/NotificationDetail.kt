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

@Composable
fun NotificationDetailScreen(
    navController: NavController,
    notificationId: Int?
) {
    val messageBox = rememberMessageBoxState()
    // Lấy thông tin thông báo từ ID (trong thực tế có thể lấy từ database/viewmodel)
    val notification = remember(notificationId) {
        when (notificationId) {
            1 -> Notification(
                id = 1,
                title = "Nhắc nhở tour",
                message = "Tour Hạ Long của bạn sẽ bắt đầu vào ngày mai. Vui lòng đến điểm tập trung trước 7:00",
                type = NotificationType.TOUR_REMINDER,
                time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
            )
            2 -> Notification(
                id = 2,
                title = "Thanh toán thành công",
                message = "Bạn đã thanh toán thành công cho tour Đà Nẵng. Vui lòng kiểm tra email để xem vé",
                type = NotificationType.PAYMENT_SUCCESS,
                time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
            )
            3 -> Notification(
                id = 3,
                title = "Cảm ơn",
                message = "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi. Chúc bạn có chuyến đi vui vẻ!",
                type = NotificationType.THANK_YOU,
                time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
            )
            4 -> Notification(
                id = 4,
                title = "Khẩn cấp",
                message = "Tour Sapa ngày 15/05 đã bị hoãn do thời tiết. Vui lòng liên hệ hỗ trợ để biết thêm chi tiết",
                type = NotificationType.URGENT,
                time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
            )
            else -> null
        }
    }

    if (notification == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Không tìm thấy thông báo")
        }
        return
    }

    val backgroundColor = when(notification.type) {
        NotificationType.TOUR_REMINDER -> Color(0xFFE3F2FD)
        NotificationType.PAYMENT_SUCCESS -> Color(0xFFE8F5E9)
        NotificationType.THANK_YOU -> Color(0xFFFFF8E1)
        NotificationType.URGENT -> Color(0xFFFFEBEE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Header với nút back
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Chi tiết thông báo",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp)) // Cân bằng layout
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card thông tin chi tiết
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = notification.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Thêm thông tin chi tiết hơn nếu cần
                if (notification.type == NotificationType.URGENT) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Liên hệ hỗ trợ: 1900 1234",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Thêm các action nếu cần
        if (notification.type == NotificationType.TOUR_REMINDER) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    messageBox.show("Thông báo", "Bạn đã xác nhận nhận thông báo này")
                },

                modifier = Modifier
                    .fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )

            ) {
                Text("Xem chi tiết tour")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationDetailScreen() {
    val navController = NavController(LocalContext.current)
    NotificationDetailScreen(
        navController = navController,
        notificationId = 1
    )
}