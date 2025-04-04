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
        TravelPlace(1, "Vịnh Hạ Long", "Quảng Ninh", R.drawable.travel_background, 4.8f, "1.200.000 VND"),
        TravelPlace(2, "Phố cổ Hội An", "Quảng Nam", R.drawable.travel_background_nt, 4.7f, "800.000 VND"),
        TravelPlace(3, "Khu du lịch Tam Cốc-Bích Động", "Ninh Bình", R.drawable.ninhbinh_background, 4.6f, "1.500.000 VND"),
        TravelPlace(4, "Đà Lạt", "Lâm Đồng", R.drawable.ninhbinh_background, 4.6f, "1.500.000 VND")
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

@Composable
fun PlaceCard(place: TravelPlace) {
    val messageBox = rememberMessageBoxState()

    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(end = 16.dp)
            .clickable(
                onClick = {
                    // Xử lý khi nhấn avatar
                    messageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
                }
            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = place.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, // Giới hạn chỉ 1 dòng
                    modifier = Modifier.fillMaxWidth(), // Giới hạn chiều rộng tối đa
                    overflow = TextOverflow.Ellipsis // Thêm "..." nếu vượt quá
                )
                Text(
                    text = place.location,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1, // Giới hạn chỉ 1 dòng
                    modifier = Modifier.fillMaxWidth(), // Giới hạn chiều rộng tối đa
                    overflow = TextOverflow.Ellipsis // Thêm "..." nếu vượt quá
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_blue),
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = place.rating.toString(),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Text(
                    text = place.price,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DealCard(deal: TravelDeal) {
    val messageBox = rememberMessageBoxState()
    Box(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    // Xử lý khi nhấn avatar
                    messageBox.show("Xin lỗi", "chức năng này chưa được cài đặt")
                }
            )
    ) {
        Image(
            painter = painterResource(id = deal.image),
            contentDescription = deal.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = deal.discount,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1, // Giới hạn chỉ 1 dòng
                modifier = Modifier.fillMaxWidth(), // Giới hạn chiều rộng tối đa
                overflow = TextOverflow.Ellipsis // Thêm "..." nếu vượt quá
            )
            Text(
                text = deal.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1, // Giới hạn chỉ 1 dòng
                modifier = Modifier.fillMaxWidth(), // Giới hạn chiều rộng tối đa
                overflow = TextOverflow.Ellipsis // Thêm "..." nếu vượt quá
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(navController = rememberNavController())
}