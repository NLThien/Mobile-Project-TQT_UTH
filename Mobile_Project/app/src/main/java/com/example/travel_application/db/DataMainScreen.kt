package com.example.travel_application.db

import java.util.Date
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class TravelPlace(
    val id: String,
    val name: String,
    val location: String,
    val imageURL: String,
    val images: Map<String, String> = emptyMap(),
    val rating: Float,
    val price: Long,
    val coordinates: LatLng, // tọa độ bản đồ
    val placeId: String,
    val description: String,
){
    constructor()  : this ("", "", "", "", emptyMap() , 0f, 0, LatLng(0.0, 0.0), "", "")

    companion object {
        fun fromGeoPoint(geoPoint: GeoPoint) : LatLng {
            return LatLng(geoPoint.latitude, geoPoint.longitude)
        }
    }
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
    val imageURL: String
)

data class TravelDetail(
    val id: String,
    val facilities: Map<String, Boolean> = emptyMap(),
    val reviewCount: Int = 0,
    val shortDescription: String = "",
    val description: String = "",
    val reviews: Map<String, Review> = emptyMap()
)

data class Review(
    val userId: String = "",
    val userName: String = "",
    val rating: Float = 0f,
    val comment: String = "",
    val date: Date = Date()
)