package com.example.myhotelapp.db

import androidx.room.*
import com.example.myhotelapp.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun like(item: Product)

    @Delete
    suspend fun unLike(item: Product)

    @Query("""
        SELECT * FROM product ORDER BY
            CASE WHEN :categoryCode = 0 AND :orderCode = 0 THEN time END ASC,
            CASE WHEN :categoryCode = 1 AND :orderCode = 0 THEN rate END ASC,
            CASE WHEN :categoryCode = 0 AND :orderCode = 1 THEN time END DESC,
            CASE WHEN :categoryCode = 1 AND :orderCode = 1 THEN rate END DESC
    """)
    fun getSavedHotelList(categoryCode: Int, orderCode: Int): Flow<List<Product>>

    @Query("SELECT likeState FROM product WHERE id = :id")
    fun getSavedState(id: Int): Boolean

}