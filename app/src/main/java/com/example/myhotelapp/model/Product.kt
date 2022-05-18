package com.example.myhotelapp.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val description: Description,
    val id: Int,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val likeState: Boolean = false,
    val time: Long? = null
):Parcelable