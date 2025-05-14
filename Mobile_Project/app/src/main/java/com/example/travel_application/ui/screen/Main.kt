package com.example.travel_application.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travel_application.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx. compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Icon
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.items
import com.example.travel_application.accessibility.rememberMessageBoxState
import androidx.compose.ui.text.style.TextOverflow
import com.google.android.gms.maps.model.LatLng
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.travel_application.accessibility.TravelRepository
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.currentComposer
import com.example.travel_application.accessibility.ServiceLocator
import androidx.compose.ui.platform.LocalContext
import com.example.travel_application.db.TravelDeal
import com.example.travel_application.db.TravelPlace
import com.example.travel_application.db.TravelCategory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travel_application.ui.navigation.SearchBar
import com.example.travel_application.accessibility.AuthViewModel
import com.example.travel_application.ui.resources.AppTheme
import okhttp3.internal.filterList
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.verticalScroll

@Composable
fun MainScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    var showAppInfoDialog by remember { mutableStateOf(false) }
    var showUserInfoDialog by remember { mutableStateOf(false) }
    val currentUser = authViewModel.getCurrentUser() // Lấy thông tin user hiện tại
    val messageBox = rememberMessageBoxState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        authViewModel.initializeAuth(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(scrollState)
    ) {
        if (currentUser == null){
           navController.navigate("login") {
               popUpTo(navController.graph.startDestinationId) {
                   inclusive = true
               }
           }
        }

        // Thêm Dialog hiển thị thông tin user
        if (showUserInfoDialog) {
            UserInfoDialog(
                user = currentUser,
                onDismiss = { showUserInfoDialog = false },
                onSignOut = {
                    authViewModel.signOut()
                    showUserInfoDialog = false
                    // Có thể thêm navigation về màn hình login nếu cần
                }
            )
        }
        // --- Phần Header (Tiêu đề + Avatar) ---

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_avatar_home),
                contentDescription = "User Avatar",
                modifier = Modifier.size(40.dp)
                    .clickable { showUserInfoDialog = true }
            )

            Text(
                text = if (currentUser != null){
                    "Xin chào ${currentUser.displayName?:" "}!"
                } else {
                    "Xin chào người đẹp!"
                },

                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = {
                            // Xử lý khi nhấn avatar
                            showAppInfoDialog = true
//                            messageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
                        }
                    )
            )
        }

        // --- Thanh tìm kiếm ---
        SearchBar(
            textBar = "Tìm kiếm địa điểm du lịch"
        )

        // --- Danh mục du lịch ---
        TravelCategories()

        Text(
            text = "Danh sách",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        AllPlacesSection(navController)

        // --- Địa điểm nổi bật ---
        Text(
            text = "Địa điểm nổi bật",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        PopularPlaces(navController= navController)

        // --- Ưu đãi đặc biệt ---
        Text(
            text = "Ưu đãi hôm nay",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        SpecialDeals()
    }
}

@Composable
fun PopularPlaces(navController: NavController) {
    // Lấy repository từ ServiceLocator
    val travelRepository = remember {
        ServiceLocator.get(TravelRepository::class.java)
    }
    var places by remember { mutableStateOf<List<TravelPlace>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            places = travelRepository.getTravelPlaces().filter { it.rating == 5f }
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
        Text("Hiện chưa có địa điểm nổi bật", modifier = Modifier.padding(16.dp))
    } else {
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(places, key = { it.id }) { place ->
                PlaceCard(
                    place = place,
                    isSelected = false,
                    onItemClick = { navController.navigate("place_detail/${place.id}") }
                )
            }
        }
    }
}

@Composable
fun AllPlacesSection(navController: NavController) {
    val travelRepository = remember { ServiceLocator.get(TravelRepository::class.java) }
    var places by remember { mutableStateOf<List<TravelPlace>>(emptyList()) }
    var sortOption by remember { mutableStateOf("default") }

    LaunchedEffect(Unit) {
        places = travelRepository.getTravelPlaces()
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SortChip("Mặc định", sortOption == "default") { sortOption = "default" }
            SortChip("Giá tăng dần", sortOption == "price_asc") { sortOption = "price_asc" }
            SortChip("Giá giảm dần", sortOption == "price_desc") { sortOption = "price_desc" }
            SortChip("Đánh giá cao", sortOption == "rating") { sortOption = "rating" }
        }

        // Danh sách đã sắp xếp
        val sortedPlaces = remember(places, sortOption) {
            when (sortOption) {
                "price_asc" -> places.sortedBy { it.price }
                "price_desc" -> places.sortedByDescending { it.price }
                "rating" -> places.sortedByDescending { it.rating }
                else -> places
            }
        }

// Hiển thị 3 hàng ngang
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hàng 1
            if (sortedPlaces.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(sortedPlaces.take(3)) { place ->
                        PlaceCard(
                            place = place,
                            isSelected = false,
                            onItemClick = { navController.navigate("place_detail/${place.id}") }
                        )
                    }
                }
            }

            // Hàng 2
            if (sortedPlaces.size > 3) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(sortedPlaces.drop(3).take(3)) { place ->
                        PlaceCard(
                            place = place,
                            isSelected = false,
                            onItemClick = { navController.navigate("place_detail/${place.id}") }
                        )
                    }
                }
            }

            // Hàng 3
            if (sortedPlaces.size > 6) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(sortedPlaces.drop(6).take(3)) { place ->
                        PlaceCard(
                            place = place,
                            isSelected = false,
                            onItemClick = { navController.navigate("place_detail/${place.id}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SortChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

// --- Danh mục du lịch (Tour, Khách sạn, Ẩm thực...) ---
@Composable
fun TravelCategories() {
    val messageBox = rememberMessageBoxState()

    val categories = listOf(
        TravelCategory(1, "Tour", R.drawable.ic_tour),
        TravelCategory(2, "Khách sạn", R.drawable.ic_hotel),
        TravelCategory(3, "Ẩm thực", R.drawable.ic_restaurant),
        TravelCategory(4, "Vé máy bay", R.drawable.ic_ticket)
    )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        categories.forEach { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0F7FA))
                        .clickable {
                            messageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = category.icon),
                        contentDescription = category.name,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = category.name,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

// --- Ưu đãi đặc biệt ---
@Composable
fun SpecialDeals() {
    val deals = remember { mutableListOf<TravelDeal>() }

    LaunchedEffect(Unit) {

    }

    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Thêm khoảng cách giữa các item
    ) {
        items(
            items = deals,
            key = { it.id }
        ) { deal ->
            DealCard(deal = deal)
        }
    }
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(navController = rememberNavController())
}