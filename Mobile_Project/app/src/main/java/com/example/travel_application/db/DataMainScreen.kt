package com.example.travel_application.db

import com.example.travel_application.R

data class TravelPlace(
    val id: Int,
    val name: String,
    val location: String,
    val image: Int,
    val rating: Float,
    val price: String
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