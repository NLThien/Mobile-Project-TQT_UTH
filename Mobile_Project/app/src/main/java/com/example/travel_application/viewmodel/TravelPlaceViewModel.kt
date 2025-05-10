package com.example.travel_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel_application.accessibility.TravelRepository // Import Repository của bạn
import com.example.travel_application.db.TravelPlace // Import lớp dữ liệu TravelPlace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Sử dụng @HiltViewModel nếu bạn dùng Hilt để Inject TravelRepository
@HiltViewModel
class TravelPlaceViewModel @Inject constructor(
    private val travelRepository: TravelRepository
) : ViewModel() {

    // StateFlow để giữ danh sách tất cả địa điểm
    private val _travelPlaces = MutableStateFlow<List<TravelPlace>>(emptyList())
    val travelPlaces: StateFlow<List<TravelPlace>> = _travelPlaces

    // StateFlow để giữ danh sách địa điểm được hiển thị (sau khi lọc)
    private val _displayedTravelPlaces = MutableStateFlow<List<TravelPlace>>(emptyList())
    val displayedTravelPlaces: StateFlow<List<TravelPlace>> = _displayedTravelPlaces

    // Biến để lưu trữ truy vấn tìm kiếm hiện tại
    private var currentSearchQuery: String = ""

    init {
        // Khởi tạo, tải dữ liệu khi ViewModel được tạo
        loadTravelPlaces()
    }

    private fun loadTravelPlaces() {
        viewModelScope.launch {
            try {
                val places = travelRepository.getTravelPlaces()
                _travelPlaces.value = places
                // Ban đầu, danh sách hiển thị là toàn bộ danh sách
                _displayedTravelPlaces.value = places
            } catch (e: Exception) {
                // Xử lý lỗi tải dữ liệu (ví dụ: log lỗi, hiển thị thông báo)
                e.printStackTrace()
            }
        }
    }

    // Hàm để cập nhật truy vấn tìm kiếm và lọc danh sách hiển thị
    fun updateSearchQuery(query: String) {
        currentSearchQuery = query
        filterDisplayedPlaces()
    }

    // Hàm lọc danh sách địa điểm hiển thị dựa trên truy vấn tìm kiếm
    private fun filterDisplayedPlaces() {
        _displayedTravelPlaces.value = if (currentSearchQuery.isBlank()) {
            _travelPlaces.value // Nếu query rỗng, hiển thị tất cả
        } else {
            _travelPlaces.value.filter {
                it.name.contains(currentSearchQuery, ignoreCase = true) ||
                        it.location.contains(currentSearchQuery, ignoreCase = true)
            }
        }
    }
}