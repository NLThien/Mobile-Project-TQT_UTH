package com.example.travel_application.accessibility

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

import com.example.travel_application.db.TravelPlace

class TravelRepository {
    private val db = Firebase.firestore
    private val placesRef = db.collection("places")

    // Lấy danh sách địa điểm
    suspend fun getTravelPlaces(): List<TravelPlace> {
        return try {
            placesRef.get().await().documents.mapNotNull { doc ->
                doc.toObject(TravelPlace::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Lấy địa điểm theo ID
    suspend fun getPlaceById(id: String): TravelPlace? {
        return try {
            placesRef.document(id).get().await().toObject(TravelPlace::class.java)
        } catch (e: Exception) {
            null
        }
    }
}