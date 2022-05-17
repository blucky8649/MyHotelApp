package com.example.myhotelapp.data.remote

import com.example.myhotelapp.model.Hotel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface HotelApi {
    @GET("App/json/{pageNum}.json")
    suspend fun getHotelInfo(
        @Path("pageNum") page: Int
    ): Hotel
}