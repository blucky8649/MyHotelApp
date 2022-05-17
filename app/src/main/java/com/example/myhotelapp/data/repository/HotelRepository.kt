package com.example.myhotelapp.data.repository

import androidx.paging.PagingData
import com.example.myhotelapp.model.Product
import kotlinx.coroutines.flow.Flow

interface HotelRepository {

    fun letHotelList(): Flow<PagingData<Product>>
}