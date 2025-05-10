package com.example.travel_application.db

import android.R
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class TravelPlace(
    val id: String,
    val name: String,
    val location: String,
    val imageURL: String,
    val rating: Float,
    val price: Long,
    val coordinates: LatLng, // tọa độ bản đồ
    val placeId: String,
    val description: String,
    val facilities: List<String> = emptyList(),
){
    constructor()  : this ("", "", "", "", 0f, 0, LatLng(0.0, 0.0), "", "", emptyList())

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