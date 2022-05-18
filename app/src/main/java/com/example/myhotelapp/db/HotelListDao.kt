package com.example.myhotelapp.db

import androidx.room.*
import com.example.myhotelapp.model.entitiies.ProductListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ProductListEntity>)

    @Update
    suspend fun update(item: ProductListEntity)

    @Query("SELECT * FROM productlistentity")
    fun getHotelLIst(): Flow<List<ProductListEntity>>

    @Query("SELECT likeState FROM productlistentity WHERE id = :id")
    suspend fun getSavedState(id: Int): Boolean

    @Query("DELETE FROM productlistentity")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM productlistentity")
    suspend fun getCount(): Int

}