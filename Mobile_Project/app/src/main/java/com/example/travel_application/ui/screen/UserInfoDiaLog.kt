package com.example.travel_application.ui.screen

import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseUser
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon

@Composable
fun UserInfoDialog(
    user: FirebaseUser?,
    onDismiss: () -> Unit,
    onSignOut: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Thông tin tài khoản",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Column {
                // Avatar và email
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Avatar",
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF4285F4)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = user?.displayName ?: "Khách",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = user?.email ?: "Chưa đăng nhập",
                            color = Color.Gray
                        )
                    }
                }

                // Các thông tin khác (nếu có)
                if (user != null) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("UID: ${user.uid}", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        "Xác thực: ${if (user.isEmailVerified) "Đã xác thực" else "Chưa xác thực"}",
                        fontSize = 12.sp,
                        color = if (user.isEmailVerified) Color.Green else Color.Red
                    )
                }
            }
        },

        confirmButton = {
            if (user != null) {
                Button(
                    onClick = {
                        onSignOut()
                        onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Đăng xuất")
                }
            } else {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text("Đóng")
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}