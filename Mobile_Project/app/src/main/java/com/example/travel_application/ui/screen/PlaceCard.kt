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
import androidx.compose.material.icons.Icons
import com.example.travel_application.accessibility.rememberMessageBoxState
import com.example.travel_application.db.TravelPlace
import com.google.android.gms.maps.model.LatLng
import coil.compose.AsyncImage
import kotlin.String
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.StarOutline

@Composable
fun PlaceCard(
    place: TravelPlace,
    isSelected: Boolean,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable(onClick = onItemClick)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            AsyncImage(
                model = place.imageURL,
                contentDescription = place.name,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.app_logo),
                error = painterResource(R.drawable.ic_avatar_home)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = place.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = place.location,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )

                // Rating với Material Icons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < place.rating.toInt()) Icons.Filled.Star
                            else Icons.Outlined.StarOutline,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = " ${place.rating}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Text(
                    text = "${place.price} VND",
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
fun PreviewPlaceCard() {
    PlaceCard(
        place = TravelPlace(
            id = "1",
            name = "Vịnh Hạ Long",
            location = "Quảng Ninh",
            imageURL = "",
            rating = 4.8f,
            price = 1200000,
            coordinates = LatLng(0.0, 0.0),
            placeId = "",
            description = ""
        ),
        isSelected = false,
        onItemClick = {}
    )
}