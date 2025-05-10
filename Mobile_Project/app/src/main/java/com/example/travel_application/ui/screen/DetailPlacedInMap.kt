package com.example.travel_application.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travel_application.ui.resources.AppTheme
import com.example.travel_application.R
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import com.example.travel_application.accessibility.TravelRepository
import com.example.travel_application.db.TravelPlace
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.CircularProgressIndicator
import coil.compose.AsyncImage
import android.widget.DatePicker
import androidx.compose.material3.TextButton
import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat
import com.example.travel_application.accessibility.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx. lifecycle. viewmodel. compose. viewModel
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun DetailPlacedInMapScreen(
    navController: NavController,
    placeId: String
) {
    // State và ViewModel
    val travelRepository = remember { TravelRepository(FirebaseFirestore.getInstance()) }
    val authViewModel: AuthViewModel = viewModel()
    val currentUser = authViewModel.getCurrentUser()

    var place by remember { mutableStateOf<TravelPlace?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var bookingStatus by remember { mutableStateOf<BookingStatus?>(null) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var numberOfPeople by remember { mutableStateOf(1) }

    // Load place details
    LaunchedEffect(placeId) {
        place = travelRepository.getPlaceById(placeId)
        isLoading = false
    }

    val scope = rememberCoroutineScope()

    // UI Content
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (place == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Không tìm thấy địa điểm")
        }
    } else {
        // Sử dụng biến val để tránh smart cast issue
        val currentPlace = place!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Hiển thị thông tin địa điểm
            AsyncImage(
                model = currentPlace.imageURL,
                contentDescription = currentPlace.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentPlace.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = "Location")
                Text(
                    text = currentPlace.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Giá: ${"%,d".format(currentPlace.price)} VND",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chọn ngày đi
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Chọn ngày đi: ${SimpleDateFormat("dd/MM/yyyy").format(selectedDate)}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chọn số lượng người
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Số lượng:", modifier = Modifier.weight(1f))
                IconButton(onClick = { if (numberOfPeople > 1) numberOfPeople-- }) {
                    Icon(Icons.Default.Remove, contentDescription = "Giảm")
                }
                Text("$numberOfPeople", modifier = Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = { if (numberOfPeople < 10) numberOfPeople++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Tăng")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nút đặt tour
            Button(
                onClick = {
                    val userId = AuthViewModel().getCurrentUser()?.uid ?: return@Button
                    bookingStatus = BookingStatus.LOADING

                    scope.launch {
                        val success = travelRepository.bookTour(
                            tourId = "TOUR_${System.currentTimeMillis()}",
                            placeId = placeId,
                            userId = userId,
                            bookingDate = Date(),
                            travelDate = selectedDate
                        )

                        bookingStatus = if (success) BookingStatus.SUCCESS else BookingStatus.FAILURE
                    }
                },
            )  {
                when (bookingStatus) {
                    BookingStatus.LOADING -> CircularProgressIndicator(color = Color.White)
                    else -> Text("ĐẶT TOUR NGAY")
                }
            }

            if (currentUser == null) {
                Text(
                    text = "Vui lòng đăng nhập để đặt tour",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // DatePicker Dialog đơn giản
        if (showDatePicker) {
            AlertDialog(
                onDismissRequest = { showDatePicker = false },
                title = { Text("Chọn ngày đi") },
                text = {
                    // Đơn giản chỉ hiển thị TextInput cho ngày
                    // (Có thể thay bằng DatePicker thực tế nếu cần)
                    TextField(
                        value = SimpleDateFormat("dd/MM/yyyy").format(selectedDate),
                        onValueChange = { /* Xử lý nếu cần */ },
                        label = { Text("Ngày đi (dd/MM/yyyy)") }
                    )
                },
                confirmButton = {
                    Button(onClick = { showDatePicker = false }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Hủy")
                    }
                }
            )
        }

        // Hiển thị kết quả đặt tour
        when (bookingStatus) {
            BookingStatus.SUCCESS -> {
                AlertDialog(
                    onDismissRequest = { bookingStatus = null },
                    title = { Text("Thành công") },
                    text = { Text("Đặt tour thành công!") },
                    confirmButton = {
                        Button(onClick = {
                            bookingStatus = null
                            navController.popBackStack()
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
            BookingStatus.FAILURE -> {
                AlertDialog(
                    onDismissRequest = { bookingStatus = null },
                    title = { Text("Lỗi") },
                    text = { Text("Đặt tour thất bại. Vui lòng thử lại.") },
                    confirmButton = {
                        Button(onClick = { bookingStatus = null }) {
                            Text("Thử lại")
                        }
                    }
                )
            }
            else -> {}
        }
    }
}

enum class BookingStatus { LOADING, SUCCESS, FAILURE }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        var navController = rememberNavController()
        DetailPlacedInMapScreen(navController,"NT02")
    }
}