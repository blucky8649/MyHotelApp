package com.example.myhotelapp.model

import com.google.gson.annotations.SerializedName

data class Hotel(
    val code: Int,
    @SerializedName("data") val data: Data,
    val msg: String
)