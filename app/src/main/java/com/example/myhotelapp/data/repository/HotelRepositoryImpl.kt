package com.example.myhotelapp.data.repository

import android.nfc.tech.MifareUltralight
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhotelapp.data.remote.HotelApi
import com.example.myhotelapp.data.source.HotelPagingSource
import com.example.myhotelapp.model.Hotel
import com.example.myhotelapp.model.Product
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class HotelRepositoryImpl(
    private val api: HotelApi
): HotelRepository {
    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 20, enablePlaceholders = false)
    }
    override fun letHotelList(): Flow<PagingData<Product>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { HotelPagingSource(api) }
        ).flow
    }
}