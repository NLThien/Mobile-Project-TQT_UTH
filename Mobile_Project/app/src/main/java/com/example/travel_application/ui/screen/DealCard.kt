package com.example.travel_application.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.travel_application.db.TravelDeal
import com.example.travel_application.db.TravelPlace
import com.google.android.gms.maps.model.LatLng

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
fun PreviewDealCard(){
    DealCard(deal = TravelDeal(1, "Giảm 30% tour Đà Nẵng", "30% OFF", R.drawable.travel_background))
}