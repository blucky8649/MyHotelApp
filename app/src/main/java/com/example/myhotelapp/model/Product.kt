package com.example.myhotelapp.model

data class Product(
    val description: Description,
    val id: Int,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val time: Long?
)