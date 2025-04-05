package com.example.travel_application.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travel_application.R
import com.example.travel_application.accessibility.rememberMessageBoxState
import com.example.travel_application.db.TravelPlace
import com.google.android.gms.maps.model.LatLng

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

@Preview(showBackground = true)
@Composable
fun PreviewPlaceCard(){
    PlaceCard(place = TravelPlace(1, "Vịnh Hạ Long", "Quảng Ninh", R.drawable.travel_background, 4.8f, "1.200.000 VND",LatLng(20.9101, 107.1839)))
}