package com.example.travel_application.db

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class TravelPlace(
    val id: String,
    val name: String,
    val location: String,
    val image: Int,
    val rating: Float,
    val price: String,
    val coordinates: LatLng // tọa độ bản đồ
){
    // Hàm tiện ích để chuyển sang LatLng của Maps SDK
    fun toLatLng(): LatLng = LatLng(coordinates.latitude, coordinates.longitude)
}

data class TravelCategory(
    val id: Int,
    val name: String,
    val icon: Int
)

data class TravelDeal(
    val id: Int,
    val title: String,
    val discount: String,
    val image: Int
)