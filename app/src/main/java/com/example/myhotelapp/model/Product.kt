package com.example.myhotelapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    val description: Description,
    @PrimaryKey
    val id: Int,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val likeState: Boolean = false,
    val time: Long?
)