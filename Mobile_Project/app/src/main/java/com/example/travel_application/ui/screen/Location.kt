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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import com.google.android.gms.maps.CameraUpdateFactory
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.clip
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.example.travel_application.db.TravelPlace
import kotlinx.coroutines.coroutineScope
import java.nio.file.WatchEvent
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.LatLngBounds
import com.example.travel_application.accessibility.rememberMessageBoxState
import com.example.travel_application.ui.navigation.SearchBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_application.accessibility.ServiceLocator
import com.example.travel_application.accessibility.TravelRepository
import com.example.travel_application.viewmodel.TravelPlaceViewModel

@Composable
fun LocationScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val messageBox = rememberMessageBoxState()
    val travelPlaces = remember { mutableStateListOf<TravelPlace?>() }

    var selectedPlace by remember { mutableStateOf<TravelPlace?>(null) }

    // Thêm state để theo dõi place đang được chọn từ map
    var mapSelectedPlace by remember { mutableStateOf<TravelPlace?>(null) }

    // Biến trạng thái để điều khiển hiển thị bản đồ
    var isMapDisplayed by remember { mutableStateOf(false) }

    // Biến trạng thái để lưu trữ truy vấn tìm kiếm
    var searchQuery by remember { mutableStateOf("") }

    // Thêm biến để theo dõi các marker
    val markerStates = remember { mutableStateMapOf<String, MarkerState>() }

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

    Spacer(modifier = Modifier.height(24.dp))

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = "Locations",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Tìm kiếm Tour",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Thanh tìm kiếm
        SearchBar(
            textBar = "Tìm kiếm địa điểm du lịch"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Places list
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Danh sách địa điểm",
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
                items(travelPlaces.filterNotNull()) { place ->
                    PlaceCard(
                        place = place,
                        isSelected = place.id == selectedPlace?.id,
                        onItemClick = { selectedPlace = place }
                    )
                }
            }
            // Các danh mục gợi ý
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Button/Text cho Khu vực
                CategoryChip(
                    text = "Khu vực",
                    isSelected = !isMapDisplayed, // Mặc định chọn Khu vực khi bản đồ không hiển thị
                    onClick = { isMapDisplayed = false } // Khi chọn Khu vực, ẩn bản đồ
                )
                // Button/Text cho Tìm trên map
                CategoryChip(
                    text = "Tìm trên map",
                    isSelected = isMapDisplayed,
                    onClick = { isMapDisplayed = true } // Khi chọn Tìm trên map, hiển thị bản đồ
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Hiển thị Bản đồ HOẶC Danh sách địa điểm dựa vào isMapDisplayed
        if (isMapDisplayed) {
            // Bản đồ Google Maps
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                        zoomControlsEnabled = false,
                        myLocationButtonEnabled = hasLocationPermission
                )
            ) {
                // Thêm Marker cho mỗi địa điểm TỪ DANH SÁCH HIỂN THỊ
                // Hiển thị tất cả marker
                travelPlaces.filterNotNull().forEach { place ->
                    val markerState = markerStates.getOrPut(place.id) {
                        rememberMarkerState(position = place.coordinates)
                    }
                        Marker(
                            state = markerState,
//                            state = rememberMarkerState(position = place.coordinates),
                            title = place.name,
                            snippet = place.location,
                            onClick = {
                                mapSelectedPlace = place
                                selectedPlace = null // Chọn địa điểm khi nhấn vào marker
                                true // Trả về false để không tiêu thụ sự kiện click
                            }
                        )
                    }
                // Tự động zoom để hiển thị tất cả marker khi có dữ liệu
                if (travelPlaces.isNotEmpty() && !cameraPositionState.isMoving) {
                    LaunchedEffect(travelPlaces) {
                        val bounds = LatLngBounds.Builder()
                        travelPlaces.filterNotNull().forEach {
                            bounds.include(it.coordinates)
                        }
                        try {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngBounds(bounds.build(), 100),
                                1000
                            )
                        } catch (e: Exception) {
                            // Fallback nếu không đủ zoom
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    travelPlaces.first()?.coordinates ?: LatLng(16.0, 108.0),
                                    10f
                                ),
                                500
                            )
                        }
                    }
                }
            }
        }

        // Places list (LazyRow) - Hiển thị khi bản đồ không hiển thị
        Column(modifier = Modifier.fillMaxWidth()) {

            Spacer(modifier = Modifier.height(8.dp))

            AllPlaces(
                navController = navController,
                selectedPlace = selectedPlace ?: mapSelectedPlace,
                onPlaceSelected = { place ->
                    selectedPlace = place
                    mapSelectedPlace = null // Reset selection từ map
                }
            )
        }

        // Continue button
        Button(
            onClick = {
                selectedPlace?.let {
                    // navController.navigate("booking/${it.id}")
                    messageBox.show("Thông báo", "Chức năng đặt tour sẽ được cập nhật sớm")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(52.dp),
            enabled = selectedPlace != null,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE0F7FA),
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Tiếp tục", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
        contentColor = if (isSelected) Color.White else Color.Black,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp) // Khoảng cách trên/dưới
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun AllPlaces(
    navController: NavController,
    selectedPlace: TravelPlace?,
    onPlaceSelected: (TravelPlace) -> Unit
) {
    // Lấy repository từ ServiceLocator
    val travelRepository = remember {
        ServiceLocator.get(TravelRepository::class.java)
    }
    var places by remember { mutableStateOf<List<TravelPlace>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            places = travelRepository.getTravelPlaces()
        } catch (e: Exception) {
            error = "Failed to load places: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        // Hiển thị loading indicator
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else if (error != null) {
        // Hiển thị thông báo lỗi
        Text(text = error!!, color = Color.Red)
    } else if (places.isEmpty()) {
        // Hiển thị khi không có dữ liệu
        Text("No places available", modifier = Modifier.padding(16.dp))
    } else {
        // Hiển thị lưới PlaceCard
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(places) { place ->
                PlaceCard(
                    place = place,
                    isSelected = place.id == selectedPlace?.id,
                    onItemClick = {
                        onPlaceSelected(place)
                        navController.navigate("place_detail/${place.id}")
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocationScreen() {
    LocationScreen(navController = rememberNavController())
}