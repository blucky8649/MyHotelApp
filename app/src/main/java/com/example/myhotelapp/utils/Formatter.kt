package com.example.myhotelapp.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.toFormattedDateString() : String {
    val format = SimpleDateFormat("yyyy-MM-dd (HH:mm:ss)")
    val date = Date(this)

    return format.format(date)
}

fun Int.toWon(): String {
    val format = DecimalFormat("#,##0")
    return format.format(this)
}