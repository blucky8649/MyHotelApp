package com.example.myhotelapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myhotelapp.model.Product

@Database(
    entities = [Product::class],
    version = 7
)
@TypeConverters(Converters::class)
abstract class HotelDatabase: RoomDatabase() {
    abstract val dao: HotelDao
}