package com.example.travel_application.db

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val time: String = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        .format(Date()),
    val bookingId: String? = null
){
    constructor( ) : this ("", "", "", NotificationType.TOUR_REMINDER, "", null)
}

enum class NotificationType {
    TOUR_REMINDER, PAYMENT_SUCCESS, THANK_YOU, URGENT
}

enum class BookingStatus { UPCOMING, COMPLETED }

data class Booking(
    val id: String,
    val tourId: String,
    val placeId: String,
    val bookingDate: Date,
    val travelDate: Date,
    val cast: Int,
    val status: BookingStatus,
    val createdAt: Date,
)