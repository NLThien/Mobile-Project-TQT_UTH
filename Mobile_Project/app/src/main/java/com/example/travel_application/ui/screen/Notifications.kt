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

@Composable
fun NotificationsScreen(navController: NavController) {
    // Danh sách thông báo mẫu
    val notifications = listOf(
        Notification(
            id = 1,
            title = "Nhắc nhở tour",
            message = "Tour Hạ Long của bạn sẽ bắt đầu vào ngày mai. Vui lòng đến điểm tập trung đúng giờ",
            type = NotificationType.TOUR_REMINDER,
            time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
        ),
        Notification(
            id = 2,
            title = "Thanh toán thành công",
            message = "Bạn đã thanh toán thành công cho tour Đà Nẵng. Vui lòng kiểm tra email để xem vé",
            type = NotificationType.PAYMENT_SUCCESS,
            time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
        ),
        Notification(
            id = 3,
            title = "Cảm ơn",
            message = "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi. Chúc bạn có chuyến đi vui vẻ!",
            type = NotificationType.THANK_YOU,
            time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
        ),
        Notification(
            id = 4,
            title = "Khẩn cấp",
            message = "Tour Sapa ngày 15/05 đã bị hoãn do thời tiết. Vui lòng liên hệ hỗ trợ để biết thêm chi tiết",
            type = NotificationType.URGENT,
            time = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(Date())
        )
    )

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

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Thông báo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Danh sách thông báo
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(notifications) { notification ->
                NotificationItem(
                    notification = notification,
                    onNotificationClick = { notificationId ->
                        navController.navigate("notificationDetail/$notificationId")
                }
                )
//                NotificationItem(notification = notification)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onNotificationClick: (Int) -> Unit = {}
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

@Preview(showBackground = true)
@Composable
fun PreviewNotificationsScreen() {
    val navController = NavController(LocalContext.current)
    NotificationsScreen(navController = navController)
}