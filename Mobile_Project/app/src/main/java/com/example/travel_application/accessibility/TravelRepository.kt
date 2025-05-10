package com.example.travel_application.accessibility

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log
import javax.inject.Inject
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Singleton
import com.google.firebase.firestore.GeoPoint // Import GeoPoint
import com.google.firebase.firestore.auth.User
import com.example.travel_application.db.TravelPlace
import kotlin.text.get
import kotlin.text.toFloat
import java.util.Date
import java.text.SimpleDateFormat
import com.google.firebase.firestore.FieldValue
import java.util.Locale

@Singleton
class TravelRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getTravelPlaces(): List<TravelPlace> {
        return try {
            Log.d("TravelRepository", "Fetching travel places from Firestore")
            val snapshot = firestore.collection("dataPlace").get().await()
            Log.d("TravelRepository", "Fetched ${snapshot.documents.size} travel places")

            snapshot.documents.mapNotNull { document ->
                try {
                    document.toTravelPlace()
                } catch (e: Exception) {
                    Log.e("TravelRepository", "Error parsing document ${document.id}", e)
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("TravelRepository", "Error getting travel places", e)
            emptyList()
        }
    }

    private fun DocumentSnapshot.toTravelPlace(): TravelPlace {
        // Lấy GeoPoint từ trường "Duration"
        val geoPoint = getGeoPoint("Duration")

        // Chuyển GeoPoint thành LatLng
        val latLng = geoPoint?.toLatLng() ?: LatLng(0.0, 0.0) // Sử dụng default LatLng nếu geoPoint là null

        return TravelPlace(
            id = id,
            name = getString("Name") ?: "",
            location = getString("Located") ?: "",
            imageURL = getString("Image") ?: "",
            rating = getDouble("Rate")?.toFloat() ?: 0f,
            price = getLong("Price") ?.toLong() ?: 0,
            coordinates = latLng,
            placeId = getString("PlaceId") ?: "",
            description = getString("Description") ?: "",
            facilities = get("Facilities") as? List<String> ?: emptyList()
        )
    }

        suspend fun getPlaceById(placeId: String): TravelPlace? {
        return try {
            val document = firestore.collection("dataPlace").document(placeId).get().await()
            document.toTravelPlace()
        } catch (e: Exception) {
            Log.e("TravelRepository", "Error getting place by ID: $placeId", e)
            null
        }
    }

    // Helper function để chuyển GeoPoint thành LatLng
    private fun GeoPoint.toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

    suspend fun bookTour(
        tourId: String,
        placeId: String,
        userId: String,
        bookingDate: Date,  // Nhận kiểu Date thay vì String
        travelDate: Date    // Nhận kiểu Date thay vì String
    ): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

            val bookingData = hashMapOf(
                "TourId" to tourId,
                "PlaceId" to placeId,
                "UserId" to userId,
                "DayBook" to dateFormat.format(bookingDate), // Chuyển Date -> String
                "DayGo" to dateFormat.format(travelDate),    // Chuyển Date -> String
                "CreatedAt" to FieldValue.serverTimestamp()
            )

            firestore.collection("transaction")
                .add(bookingData)
                .await()

            true
        } catch (e: Exception) {
            Log.e("TravelRepository", "Error booking tour", e)
            false
        }
    }
}