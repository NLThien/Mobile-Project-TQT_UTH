package com.example.travel_application.db

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import java.text.SimpleDateFormat
import java.util.*

data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val type: NotificationType,
    val time: String = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        .format(Date())
)

enum class NotificationType {
    TOUR_REMINDER, PAYMENT_SUCCESS, THANK_YOU, URGENT
}