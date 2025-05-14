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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.foundation.layout.paddingFrom
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.draw.clip
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import com.example.travel_application.db.Review
import com.example.travel_application.db.TravelDetail
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api // Cần annotation này
import androidx.compose.material3.rememberDatePickerState
import java.util.Calendar // Cần import Calendar
import java.util.TimeZone // Cần import TimeZone
import java.util.concurrent.TimeUnit // Cần import TimeUnit
import com.google.common.primitives.UnsignedBytes.toInt
import android.icu.util.UniversalTimeScale.toLong
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPlacedInMapScreen(
    navController: NavController,
    placeId: String
) {
    // State và ViewModel
    val travelRepository = remember { TravelRepository(FirebaseFirestore.getInstance()) }
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUser = authViewModel.getCurrentUser()

    var place by remember { mutableStateOf<TravelPlace?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var placeDetails by remember { mutableStateOf<TravelDetail?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var numberOfPeople by remember { mutableStateOf(1) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }

    // State cho DatePicker
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf(Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply { set(Calendar.YEAR, 2025) }.time) } // Giá trị mặc định ban đầu

    // State for booking status and dialog
    var bookingStatus by remember { mutableStateOf<BookingStatus?>(null) }
    var showBookingSuccessDialog by remember { mutableStateOf(false) }
    var bookingSuccessMessage by remember { mutableStateOf("") }

    // Load place details
    LaunchedEffect(placeId) {
        place = travelRepository.getPlaceById(placeId)
        placeDetails = travelRepository.getPlaceDetails(placeId)
        isLoading = false
    }

    val scope = rememberCoroutineScope()

    // UI Content
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (place == null || placeDetails == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Không tìm thấy thông tin địa điểm")
        }
    } else {
        // Sử dụng biến val để tránh smart cast issue
        val currentPlace = place!!
        val placeDetails = placeDetails!!

        LazyColumn(
            modifier = Modifier.fillMaxSize()
//                .fillMaxSize()
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
        ) {
            item {
                // Hiển thị thông tin địa điểm
                AsyncImage(
                    model = currentPlace.imageURL,
                    contentDescription = currentPlace.name,
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                // Hiển thị các ảnh phụ từ Map
                if (currentPlace.images.isNotEmpty()) {
//                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Hình ảnh khác",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .padding(horizontal = 16.dp) // Áp dụng padding ngang ở đây
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
                    ) {
                        items(currentPlace.images.values.toList()) { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Hình ảnh ${currentPlace.name}",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            item {
                // Phần header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentPlace.name,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.weight(1f)
                        )

                        // Đánh giá
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFC107)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "%.1f".format(currentPlace.rating),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }

                    // Địa chỉ
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = currentPlace.location,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Divider
                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                }

                // Phần mô tả ngắn
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Giới thiệu",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Luôn hiển thị mô tả ngắn
                    val shortDescription = placeDetails?.shortDescription ?: "Không có mô tả"
                    Text(
                        text = shortDescription,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // Hiển thị mô tả đầy đủ chỉ khi isDescriptionExpanded là true
                    if (isDescriptionExpanded) {
                        val fullDescription = placeDetails?.shortDescription ?: "Không có mô tả"
                        // Chỉ hiển thị phần còn lại của mô tả
                        val remainingDescription = placeDetails?.description ?: "không có mô tả"
//                        val remainingDescription = if (fullDescription.length > 150) {
//                            fullDescription.substring(150)
//                        } else {
//                            "" // Không có phần còn lại nếu mô tả không dài hơn 150
//                        }
                        if (remainingDescription.isNotEmpty()) {
                            Text(
                                text = remainingDescription,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp) // Khoảng cách giữa mô tả ngắn và dài
                            )
                        }
                    }

                    // "Read More" button (nếu mô tả dài hơn 150 ký tự và chưa được mở rộng)
                    if (placeDetails?.description?.length ?: 0 > 150 && !isDescriptionExpanded) {
                        TextButton(
                            onClick = { isDescriptionExpanded = true },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Đọc thêm")
                        }
                    }
                    // "Hide" button (nếu mô tả đã được mở rộng)
                    else if (isDescriptionExpanded) {
                        TextButton(
                            onClick = { isDescriptionExpanded = false },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Ẩn bớt") // Hoặc "Thu gọn"
                        }
                    }
                }

                // Phần tiện nghi

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Tiện Ích",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Grid tiện nghi
                    val facilities = placeDetails.facilities.entries.toList()

                    // Hiển thị từng tiện nghi như một item riêng
                    placeDetails.facilities.entries.forEach { (facility, available) ->
                        FacilityItem(name = facility, available = available)
                    }
                }

                // Phần đánh giá (reviews)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Đánh giá",
                            style = MaterialTheme.typography.titleLarge
                        )

                        TextButton(onClick = { /* Xem tất cả đánh giá */ }) {
                            Text("Xem tất cả (${placeDetails.reviewCount})")
                        }
                    }

                    if (placeDetails.reviews.isNotEmpty()) {
                        placeDetails.reviews.forEach { review ->
                            ReviewItem(review = review.value)
                        }
                    } else {
                        Text("Chưa có đánh giá nào", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Text(
                    text = "Giá: ${"%,d".format(currentPlace.price)} VND",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier
                        .fillMaxWidth() // Mở rộng hết chiều rộng
                        .wrapContentWidth(Alignment.End) // Căn chỉnh nội dung về phía cuối
                        .padding(horizontal = 16.dp) // Giữ padding ngang nhất quán
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Chọn ngày đi
                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Chọn ngày đi: ${SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(selectedDate)}")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Chọn số lượng người
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
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
                        val userId = AuthViewModel().getCurrentUser()?.uid
                        if (userId == null) {
                            // Hiển thị thông báo yêu cầu đăng nhập
                            bookingSuccessMessage = "Vui lòng đăng nhập để đặt tour"
                            showBookingSuccessDialog = true
                            return@Button
                        }

                        bookingStatus = BookingStatus.LOADING

                        scope.launch {
                            val success = travelRepository.bookTour(
                                tourId = "TOUR_${System.currentTimeMillis()}",
                                placeId = currentPlace.placeId,
                                userId = userId,
                                bookingDate = Date(),
                                travelDate = selectedDate,
                                numberOfPeople = numberOfPeople,
                                cast = currentPlace.price,
                                onSuccess = {
                                    // Xử lý khi đặt tour thành công
                                    bookingStatus = BookingStatus.SUCCESS
                                    bookingSuccessMessage = "Đặt tour thành công!"
                                    showBookingSuccessDialog = true
                                },
                                onError = { errorMessage ->
                                    // Xử lý khi đặt tour thất bại
                                    bookingStatus = BookingStatus.FAILURE
                                    bookingSuccessMessage = errorMessage
                                    showBookingSuccessDialog = true
                                }
                            )

                            bookingStatus =
                                if (success) BookingStatus.SUCCESS else BookingStatus.FAILURE
                            if (success) {
                                bookingSuccessMessage = "Đặt tour thành công!"
                                showBookingSuccessDialog = true
                            } else {
                                bookingSuccessMessage = "Đặt tour thất bại. Vui lòng thử lại."
                                showBookingSuccessDialog = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ,
                    enabled = currentUser != null
                ) {
                    when (bookingStatus) {
                        BookingStatus.LOADING -> CircularProgressIndicator(color = Color.White)
                        else -> Text("ĐẶT TOUR NGAY")
                    }
                }

                if (currentUser == null) {
                    Text(
                        text = "Vui lòng đăng nhập để đặt tour",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }


                // DatePicker Dialog đơn giản
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // Lấy ngày được chọn từ state của date picker
                                    val selectedDateMillis = datePickerState.selectedDateMillis
                                    if (selectedDateMillis != null) {
                                        selectedDate = Date(selectedDateMillis)
                                    }
                                    showDatePicker = false
                                }
                            ) {
                                Text("Xác nhận")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                }
                            ) {
                                Text("Hủy")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                // Hiển thị kết quả đặt tour
                if (showBookingSuccessDialog) {
                    AlertDialog(
                        onDismissRequest = { showBookingSuccessDialog = false },
                        title = { Text(if (bookingStatus == BookingStatus.SUCCESS) "Thành công" else "Thông báo") },
                        text = { Text(bookingSuccessMessage) },
                        confirmButton = {
                            Button(onClick = {
                                showBookingSuccessDialog = false
                                // Có thể điều hướng đi nơi khác sau khi đặt tour thành công
                                if (bookingStatus == BookingStatus.SUCCESS) {
                                    navController.popBackStack() // Quay lại màn hình trước đó
                                }
                                bookingStatus = null // Reset trạng thái booking
                            }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}

enum class BookingStatus { LOADING, SUCCESS, FAILURE }

@Composable
fun FacilityItem(name: String, available: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            painter = painterResource(
                if (available) R.drawable.ic_tour else R.drawable.ic_tour
            ),
            contentDescription = null,
            tint = if (available) Color(0xFF4CAF50) else Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = if (available) Color.Black else Color.Gray.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun ReviewItem(review: Review) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.LightGray, CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = review.userName,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "%.1f".format(review.rating),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Text(
                    text = SimpleDateFormat("dd/MM/yyyy").format(review.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        var navController = rememberNavController()
        DetailPlacedInMapScreen(navController,"NT02")
    }
}