package com.example.myhotelapp.model.entitiies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myhotelapp.model.Description

@Entity
data class ProductListEntity(
    val description: Description,
    @PrimaryKey
    val id: String,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val likeState: Boolean = false,
    val time: Long?
)
