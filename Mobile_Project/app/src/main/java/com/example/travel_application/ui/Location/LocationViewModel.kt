package com.example.travel_application.ui.Location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Đây là trang tìm địa điểm"
    }
    val text: LiveData<String> = _text
}