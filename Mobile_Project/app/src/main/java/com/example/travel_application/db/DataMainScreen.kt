package com.example.travel_application.db

import com.google.android.gms.maps.model.LatLng

data class TravelPlace(
    val id: Int,
    val name: String,
    val location: String,
    val image: Int,
    val rating: Float,
    val price: String,
    val coordinates: LatLng // tọa độ bản đồ
)

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