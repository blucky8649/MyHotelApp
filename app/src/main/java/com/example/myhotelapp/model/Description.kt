package com.example.myhotelapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Description(
    val imagePath: String,
    val price: Int,
    val subject: String
): Parcelable