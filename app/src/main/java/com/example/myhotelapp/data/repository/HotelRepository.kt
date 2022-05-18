package com.example.myhotelapp.data.repository

import androidx.paging.PagingData
import com.example.myhotelapp.model.Hotel
import com.example.myhotelapp.model.Product
import com.example.myhotelapp.model.entitiies.ProductListEntity
import com.example.myhotelapp.model.entitiies.ProductSaveEntity
import kotlinx.coroutines.flow.Flow

interface HotelRepository {
    suspend fun like(keyword: Product)
    fun getSavedHotelList(categoryCode: Int, orderCode: Int): Flow<List<Product>>
    suspend fun unLike(keyword: Product)
    suspend fun getLikeState(id: Int): Boolean
    suspend fun getRemoteHotelList(page: Int): Hotel
    suspend fun clear()
    suspend fun insertAll(item: List<Product>)
    fun getProductListFlow(): Flow<List<Product>>
    suspend fun getCount(): Int
}