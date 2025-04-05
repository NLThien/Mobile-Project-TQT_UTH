package com.example.travel_application.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travel_application.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import com.google.android.gms.maps.CameraUpdateFactory
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.clip

import com.example.travel_application.db.TravelPlace
import kotlinx.coroutines.coroutineScope
import java.nio.file.WatchEvent
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.launch
import com.example.travel_application.accessibility.rememberMessageBoxState

@Composable
fun LocationScreen(navController: NavController) {
    val context = LocalContext.current
    val mesageBox = rememberMessageBoxState()
    val travelPlaces = remember {
        listOf(
            TravelPlace(
                1,
                "Vịnh Hạ Long",
                "Quảng Ninh",
                R.drawable.travel_background,
                4.8f,
                "1.200.000 VND",
                LatLng(20.9101, 107.1839)
            ),
            TravelPlace(
                2,
                "Phố cổ Hội An",
                "Quảng Nam",
                R.drawable.travel_background,
                4.7f,
                "800.000 VND",
                LatLng(15.8801, 108.3380)
            ),
            TravelPlace(
                3,
                "Tam Cốc-Bích Động",
                "Ninh Bình",
                R.drawable.travel_background,
                4.6f,
                "1.500.000 VND",
                LatLng(20.2120, 105.9222)
            ),
            TravelPlace(
                4,
                "Đà Lạt",
                "Lâm Đồng",
                R.drawable.travel_background,
                4.6f,
                "1.500.000 VND",
                LatLng(11.9404, 108.4583)
            )
        )
    }

    var selectedPlace by remember { mutableStateOf<TravelPlace?>(null) }

    // logic kiểm tra quyền truy cập Internet từ người dùng
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher để yêu cầu quyền
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
    }

    // Camera position state được tối ưu
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            selectedPlace?.coordinates ?: LatLng(16.0, 108.0),
            if (selectedPlace != null) 12f else 5f
        )
    }

    // Xử lý khi selectedPlace thay đổi
    LaunchedEffect(selectedPlace) {
        selectedPlace?.let { place ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(place.coordinates, 12f),
                500 // Thời gian animation 1 giây
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Thêm nền trắng cho đẹp
    ) {
        // Header với khoảng cách hợp lý
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Chọn điểm du lịch",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            color = Color(0xFF1A237E) // Màu chữ đẹp hơn
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Google Maps với shadow và bo góc
        Box(
            modifier = Modifier
                .height(280.dp) // Tăng chiều cao chút
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp)) // Bo góc map
        ) {
            val coroutineScope = rememberCoroutineScope() // Thêm scope cho animate
            val mapProperties by remember {
                derivedStateOf {
                    MapProperties(
                        isMyLocationEnabled = hasLocationPermission,
                        mapType = MapType.NORMAL
                    )
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = hasLocationPermission
                )
            ) {
                travelPlaces.forEach { place ->
                    Marker(
                        state = remember { MarkerState(position = place.coordinates) },
                        title = place.name,
                        snippet = place.price,
                        onClick = {
                            if (selectedPlace?.id != place.id) {
                                selectedPlace = place
                                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(place.coordinates, 12f))
                            }
//                            selectedPlace = place
                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        place.coordinates,
                                        12f
                                    ),
                                    500
                                )
                            }
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(place.coordinates, 12f)
                            true
                            true
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Danh sách địa điểm dạng LazyRow
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Địa điểm nổi bật",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Color(0xFF1A237E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(end = 16.dp)
            ) {
                items(travelPlaces) { place ->
                    PlaceCard(
                        place = place
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới

        // Nút xác nhận được thiết kế đẹp hơn
        Button(
            onClick = {
//                selectedPlace?.let {
//                    navController.navigate("booking/${it.id}")
//                }
                mesageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(52.dp), // Cao hơn chút
            enabled = selectedPlace != null,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE0F7FA), // Màu nút
                disabledContainerColor = Color.LightGray // Màu khi disabled
            ),
            shape = RoundedCornerShape(12.dp) // Bo góc đẹp
        ) {
            Text(
                "Tiếp tục",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewLocationScreen() {
    val navController = NavController(LocalContext.current)
    LocationScreen(navController = navController)
}