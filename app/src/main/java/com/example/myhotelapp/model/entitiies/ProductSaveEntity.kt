package com.example.myhotelapp.model.entitiies

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myhotelapp.model.Description
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ProductSaveEntity(
    val description: Description,
    @PrimaryKey
    val id: Int,
    val name: String,
    val rate: Double,
    val thumbnail: String,
    val likeState: Boolean = false,
    val time: Long?
): Parcelable