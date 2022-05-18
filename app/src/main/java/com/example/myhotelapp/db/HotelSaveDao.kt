package com.example.myhotelapp.db

import androidx.room.*
import com.example.myhotelapp.model.entitiies.ProductSaveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelSaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun like(item: ProductSaveEntity)

    @Delete
    suspend fun unLike(item: ProductSaveEntity)

    @Query("""
        SELECT * FROM productsaveentity ORDER BY
            CASE WHEN :categoryCode = 0 AND :orderCode = 0 THEN time END ASC,
            CASE WHEN :categoryCode = 1 AND :orderCode = 0 THEN rate END ASC,
            CASE WHEN :categoryCode = 0 AND :orderCode = 1 THEN time END DESC,
            CASE WHEN :categoryCode = 1 AND :orderCode = 1 THEN rate END DESC
    """)
    fun getHotelList(categoryCode: Int, orderCode: Int): Flow<List<ProductSaveEntity>>

    @Query("SELECT likeState FROM productsaveentity WHERE id = :id")
    fun getSavedState(id: Int): Boolean

    @Query("SELECT time FROM productsaveentity WHERE id = :id")
    fun getTime(id: Int): Long

}