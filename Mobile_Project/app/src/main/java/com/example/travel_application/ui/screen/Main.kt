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
import androidx.compose.material3.Icon
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.items
import com.example.travel_application.accessibility.rememberMessageBoxState
import androidx.compose.ui.text.style.TextOverflow
import com.google.android.gms.maps.model.LatLng

import com.example.travel_application.db.TravelDeal
import com.example.travel_application.db.TravelPlace
import com.example.travel_application.db.TravelCategory

import com.example.travel_application.ui.navigation.SearchBar

@Composable
fun MainScreen(navController: NavController) {
    val messageBox = rememberMessageBoxState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
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
                contentDescription = "Back",
                modifier = Modifier.size(40.dp)
                    .clickable(
                        onClick = {
                            // Xử lý khi nhấn avatar
                            messageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
                        }
                    )
            )

            Text(
                text = "Xin chào người đẹp!",
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
                            messageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
                        }
                    )
            )
        }

        // --- Thanh tìm kiếm ---
        SearchBar()

        // --- Danh mục du lịch ---
        TravelCategories()

        // --- Địa điểm nổi bật ---
        Text(
            text = "Địa điểm nổi bật",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        PopularPlaces()

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

// --- Danh sách địa điểm nổi bật ---
@Composable
fun PopularPlaces() {
    val places = listOf(
        TravelPlace(1, "Vịnh Hạ Long", "Quảng Ninh", R.drawable.travel_background, 4.8f, "1.200.000 VND",LatLng(20.9101, 107.1839)),
        TravelPlace(2, "Phố cổ Hội An", "Quảng Nam", R.drawable.travel_background_nt, 4.7f, "800.000 VND",LatLng(15.8801, 108.3380)),
        TravelPlace(3, "Khu du lịch Tam Cốc-Bích Động", "Ninh Bình", R.drawable.ninhbinh_background, 4.6f, "1.500.000 VND",LatLng(20.2120, 105.9222)),
        TravelPlace(4, "Đà Lạt", "Lâm Đồng", R.drawable.ninhbinh_background, 4.6f, "1.500.000 VND",LatLng(11.9404, 108.4583))
    )

    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(
            items = places,
            key = { it.id }
        ) { place ->
            PlaceCard(
                place = place
            )
        }
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

    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(
            items = categories,
            key = { it.id }
        ) { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0F7FA))
                        .clickable(
                            onClick = {
                                // Xử lý khi nhấn avatar
                                messageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
                            }
                        ),
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
    val deals = remember {
        listOf(
            TravelDeal(1, "Giảm 30% tour Đà Nẵng", "30% OFF", R.drawable.travel_background),
            TravelDeal(2, "Cửa khẩu quốc tế Mộc Bài", "Trải nghiệm CamPuChia", R.drawable.travel_background),
            TravelDeal(3, "Combo 2N1Đ Phú Quốc", "1.500.000 VND", R.drawable.travel_background),
            TravelDeal(4, "Chào mừng 30/04", "Up sọt 3que", R.drawable.travel_background),
            TravelDeal(5, "Combo 3 ngày 2 đêm", "Hạ Long Quảng Ninh", R.drawable.travel_background)
        )
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