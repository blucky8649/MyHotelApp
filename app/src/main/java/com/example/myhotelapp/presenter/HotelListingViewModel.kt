package com.example.myhotelapp.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myhotelapp.data.repository.HotelRepository
import com.example.myhotelapp.data.repository.HotelRepositoryImpl
import com.example.myhotelapp.model.Hotel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelListingViewModel @Inject constructor(
    private val repository: HotelRepository
) : ViewModel() {

    fun fetchHotelList() = repository.letHotelList().cachedIn(viewModelScope)
}