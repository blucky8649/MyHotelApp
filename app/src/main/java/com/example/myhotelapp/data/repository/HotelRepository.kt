package com.example.myhotelapp.data.repository

import androidx.paging.PagingData
import com.example.myhotelapp.model.Product
import kotlinx.coroutines.flow.Flow

interface HotelRepository {

    fun letHotelList(): Flow<PagingData<Product>>
    suspend fun like(keyword: Product)
    fun getSavedHotelList(categoryCode: Int, orderCode: Int): Flow<List<Product>>
    suspend fun unLike(keyword: Product)
    suspend fun getLikeState(id: Int): Boolean
}