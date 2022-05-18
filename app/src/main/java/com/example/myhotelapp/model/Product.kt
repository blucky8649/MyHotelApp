package com.example.myhotelapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Product(
    val description: Description,
    @PrimaryKey
    val id: Int,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val likeState: Boolean = false,
    val time: Long?
): Parcelable