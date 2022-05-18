package com.example.myhotelapp.db

import androidx.room.TypeConverter
import com.example.myhotelapp.model.Description
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromDescription(item: Description): String {
        return Gson().toJson(item)
    }

    @TypeConverter
    fun toDescription(item: String): Description {
        return Gson().fromJson(item, Description::class.java)
    }
}