package com.example.travel_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.travel_application.accessibility.TravelRepository
import com.example.travel_application.db.Notification
import com.example.travel_application.db.Booking
import com.example.travel_application.db.NotificationType
import com.example.travel_application.db.BookingStatus
import com.google.firebase.firestore.DocumentSnapshot
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: TravelRepository
) : ViewModel() {

    // StateFlow để giữ danh sách thông báo (có thể sử dụng ở màn hình danh sách)
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    // StateFlow để giữ chi tiết thông báo đang xem (đối tượng Notification)
    private val _notificationDetail = MutableStateFlow<Notification?>(null)
    val notificationDetail: StateFlow<Notification?> = _notificationDetail

    // StateFlow để giữ toàn bộ DocumentSnapshot của transaction liên quan
    // Chúng ta cần DocumentSnapshot để lấy các trường booking
    private val _transactionDocument = MutableStateFlow<DocumentSnapshot?>(null)
    val transactionDocument: StateFlow<DocumentSnapshot?> = _transactionDocument

    // Load danh sách thông báo cho người dùng
    fun loadNotifications(userId: String) {
        viewModelScope.launch {
            try {
                // Hàm getUserNotifications trong Repository cần đọc từ collection "transaction"
                // và ánh xạ mỗi document sang Notification
                val notifications = repository.getUserNotifications(userId)
                _notifications.value = notifications
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Helper function để tạo đối tượng Booking từ DocumentSnapshot
    // Hàm này có thể được gọi từ Composable để tạo đối tượng Booking khi transactionDocument có dữ liệu
    fun createBookingFromDocument(doc: DocumentSnapshot): Booking? {
        return try {
            Booking(
                id = doc.id,
                tourId = doc.getString("TourId") ?: "",
                placeId = doc.getString("PlacedId") ?: "",
                bookingDate = parseFirestoreDate(doc.getString("DayBook")),
                travelDate = parseFirestoreDate(doc.getString("DayGo")),
                cast = doc.getLong("numberOfPeople")?.toInt() ?: 0,
                status = BookingStatus.UPCOMING,
                createdAt = doc.getTimestamp("CreatedAt")?.toDate() ?: Date()
            )
        } catch (e: Exception) {
            null
        }
    }

    fun loadNotificationDetail(transactionId: String) {
        viewModelScope.launch {
            try {
                val transactionDoc = repository.getTransactionDocument(transactionId)
                _transactionDocument.value = transactionDoc

                if (transactionDoc != null && transactionDoc.exists()) {
                    // Tạo thông báo từ transaction
                    val notification = Notification(
                        id = transactionDoc.id,
                        title = "Thông báo đặt tour", // Có thể customize
                        message = "Bạn đã đặt tour ${transactionDoc.getString("TourId")} thành công",
                        type = NotificationType.PAYMENT_SUCCESS, // Giả sử có enum này
                        time = transactionDoc.getTimestamp("CreatedAt")?.toDate()?.let {
                            SimpleDateFormat("HH:mm dd/MM/yyyy").format(it)
                        } ?: "Unknown time"
                    )
                    _notificationDetail.value = notification
                }
            } catch (e: Exception) {
                Log.e("NotificationViewModel", "Error loading notification detail", e)
            }
        }
    }

    init {
        Log.d("HiltDebug", "NotificationViewModel created with repository: $repository")
        Log.d("ViewModelDebug", "Notifications initial value: ${_notifications.value}")
        Log.d("ViewModelDebug", "NotificationDetail initial value: ${_notificationDetail.value}")
        Log.d("ViewModelDebug", "TransactionDocument initial value: ${_transactionDocument.value}")
    }


    // Hàm parseFirestoreDate để chuyển String sang Date (copy từ Repository hoặc gọi từ Repository nếu là public)
    private fun parseFirestoreDate(dateString: String?): Date {
        return try {
            // Đảm bảo format này khớp với format bạn dùng khi lưu Date vào Firestore
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateString) ?: Date()
        } catch (e: Exception) {
            // Log lỗi nếu parse thất bại
            // Log.e("NotificationViewModel", "Error parsing date string: $dateString", e)
            Date() // Trả về ngày hiện tại hoặc một ngày mặc định nếu parse thất bại
        }
    }
}