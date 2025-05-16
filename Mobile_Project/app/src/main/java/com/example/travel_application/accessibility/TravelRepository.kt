package com.example.travel_application.accessibility

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log
import androidx.compose.foundation.layout.add
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Singleton
import com.google.firebase.firestore.GeoPoint // Import GeoPoint
import com.google.firebase.firestore.auth.User
import com.example.travel_application.db.TravelPlace
import kotlin.text.get
import kotlin.text.toFloat
import com.google.android.gms.tasks.Tasks
import java.util.Date
import java.text.SimpleDateFormat
import com.google.firebase.firestore.FieldValue
import java.util.Locale
import com.example.travel_application.db.TravelDetail
import com.example.travel_application.db.Review
import com.example.travel_application.db.Notification
import com.example.travel_application.db.NotificationType
import java.util.Calendar
import com.google.firebase.firestore.Query
import com.example.travel_application.db.Booking
import com.example.travel_application.db.BookingStatus
import kotlinx.coroutines.launch
import kotlin.text.format
import kotlin.text.get
import kotlin.text.toInt

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
        // Lấy dữ liệu hình ảnh dưới dạng Map
        val imagesMap = get("Images") as? Map<String, String> ?: emptyMap()

        return TravelPlace(
            id = id,
            name = getString("Name") ?: "",
            location = getString("Located") ?: "",
            imageURL = getString("Image") ?: "",
            images = imagesMap,
            rating = getDouble("Rate")?.toFloat() ?: 0f,
            price = getLong("Price") ?.toLong() ?: 0,
            coordinates = latLng,
            placeId = getString("PlaceId") ?: "",
            description = getString("Description") ?: ""
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

    suspend fun getPlaceDetails(placeId: String): TravelDetail? {
        return try {
            val document = firestore.collection("DetailPlace").document(placeId).get().await()
            if (document.exists()) {
                TravelDetail(
                    id = document.id,
                    description = document.getString("Detail") ?: "",
                    shortDescription = document.getString("shortDescription") ?: "",
                    facilities = document.get("facilities") as? Map<String, Boolean> ?: emptyMap(),
                    reviewCount = document.getLong("reviewCount")?.toInt() ?: 0,
                    reviews = document.get("Review") as? Map<String, Review> ?: emptyMap()
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("TravelRepository", "Error getting place details", e)
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
        travelDate: Date,    // Nhận kiểu Date thay vì String
        numberOfPeople: Int,
        cast: Long,
        onSuccess: () -> Unit, // Thêm callback thành công
        onError: (String) -> Unit // Thêm callback lỗi
    ): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

            val bookingData = hashMapOf(
                "TourId" to tourId,
                "PlaceId" to placeId,
                "UserId" to userId,
                "DayBook" to dateFormat.format(bookingDate), // Chuyển Date -> String
                "DayGo" to dateFormat.format(travelDate),    // Chuyển Date -> String
                "CreatedAt" to FieldValue.serverTimestamp(),
                "numberOfPeople" to numberOfPeople,
                "cast" to  cast,
            )

            // Sử dụng await() để đảm bảo chờ cho hoạt động hoàn tất
            val documentReference = firestore.collection("transaction").add(bookingData).await()
            Log.d("TravelRepository", "Tour booked successfully with ID: ${documentReference.id}")
            onSuccess() // Gọi callback thành công

            Log.d("TravelRepository", "Tour booked successfully")
            true
        } catch (e: Exception) {
            Log.e("TravelRepository", "Error booking tour: ${e}", e)
            false
        }
    }

    suspend fun getUserNotifications(userId: String): List<Notification> {
        // Thêm validation
        if (userId.isBlank()) {
            Log.e("DEBUG", "Invalid UserId: blank or null")
            return emptyList()
        }

        return try {
            val querySnapshot = firestore.collection("transaction")
                .whereEqualTo("UserId", userId.trim())
//                .orderBy("DayBook", Query.Direction.DESCENDING)
                .get()
                .await()
            Log.d("DEBUG", "Found ${querySnapshot.size()} documents")

            querySnapshot.documents.mapNotNull { doc ->
                val dayBook = doc.getString("DayBook") ?: "".also {
                    Log.w("DEBUG", "DayBook field missing in doc ${doc.id}")
                }

                Notification(
                    id = doc.id,
                    title = "Đặt tour thành công",
                    message = "Bạn đã đặt tour vào ngày $dayBook",
                    type = NotificationType.TOUR_REMINDER,
                    time = dayBook
                )
            }
        } catch (e: Exception) {
            Log.e("TravelRepository", "Error getting notifications", e)
            emptyList()
        }
    }

    suspend fun getTransactionDocument(transactionId: String): DocumentSnapshot? {
        return try {
            firestore.collection("transaction")
                .document(transactionId)
                .get()
                .await()
        } catch (e: Exception) {
            null
        }
    }

    private fun parseFirestoreDate(dateString: String?): Date {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateString) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }
}
