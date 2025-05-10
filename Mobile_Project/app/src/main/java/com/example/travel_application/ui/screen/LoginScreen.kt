package com.example.travel_application.ui.screen

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import com.example.travel_application.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.travel_application.ui.screen.MessageBox
import com.example.travel_application.accessibility.AuthViewModel
import com.example.travel_application.accessibility.rememberMessageBoxState

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess : () -> Unit,
    authViewModel: AuthViewModel
){
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val messageBox = rememberMessageBoxState()

    // Khởi tạo AuthViewModel khi Composable được tạo lần đầu
    LaunchedEffect(Unit) {
        authViewModel.initializeAuth(context)
    }

    // Launcher để xử lý kết quả từ Google Sign-In Intent
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                authViewModel.handleSignInResult(result) {
                    // Gọi onLoginSuccess khi đăng nhập Google thành công
                    onLoginSuccess()
                }
            } else {
                // Xử lý trường hợp người dùng hủy hoặc đăng nhập thất bại
                messageBox.show("Đăng nhập", "Đăng nhập Google thất bại.")
            }
        }
    )

    // Xử lý đăng nhập bằng email/mật khẩu
    fun loginUser() {
        if (email.isEmpty() || password.isEmpty()) {
            messageBox.show("Lỗi", "Vui lòng nhập email và mật khẩu")
            return
        }

        authViewModel.loginWithEmail(
            email = email,
            password = password,
            onSuccess = onLoginSuccess,
            onError = { error ->
                messageBox.show("Đăng nhập thất bại", error)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF8BC34A)
                    )
                )
            )
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.travel_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Chào mừng trở lại",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Đăng nhập để tiếp tục",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tên đăng nhập", color = Color.White) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Mật khẩu", color = Color.White) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    loginUser()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4CAF50)
                )
            ) {
                Text("ĐĂNG NHẬP", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Forgot Password & Sign Up
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    messageBox.show("Thông báo", "Chức năng này chưa được cài đặt")
                }) {
                    Text(
                        "Quên mật khẩu?",
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                TextButton(onClick = {
                    navController.navigate("resigter")
                }) {
                    Text(
                        "Đăng ký tài khoản",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            // Nút đăng nhập bằng Google
            Button(
                onClick = {
                    val signInIntent = authViewModel.getGoogleSignInIntent()
                    googleSignInLauncher.launch(signInIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3290B2), // Màu Google Blue
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle, // Hoặc Icons.Rounded.Google
                        contentDescription = "Google icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ĐĂNG NHẬP VỚI GOOGLE", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val authViewModel = remember { AuthViewModel() }
    val navController = rememberNavController()
    LoginScreen(navController = navController, onLoginSuccess = {}, authViewModel = authViewModel)
}