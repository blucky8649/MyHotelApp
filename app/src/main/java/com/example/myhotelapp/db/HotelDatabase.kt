package com.example.myhotelapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myhotelapp.model.entitiies.ProductSaveEntity
import com.example.myhotelapp.model.entitiies.ProductListEntity

@Database(
    entities = [ProductSaveEntity::class, ProductListEntity::class],
    version = 8
)
@TypeConverters(Converters::class)
abstract class HotelDatabase: RoomDatabase() {
    abstract val saveDao: HotelSaveDao
    abstract val listDao: HotelListDao
}