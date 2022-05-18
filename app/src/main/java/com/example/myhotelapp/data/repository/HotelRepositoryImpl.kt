package com.example.myhotelapp.data.repository

import com.example.myhotelapp.data.remote.HotelApi
import com.example.myhotelapp.db.HotelDatabase
import com.example.myhotelapp.model.Hotel
import com.example.myhotelapp.model.Product
import com.example.myhotelapp.utils.toProduct
import com.example.myhotelapp.utils.toProductListEntity
import com.example.myhotelapp.utils.toProductSaveEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HotelRepositoryImpl(
    private val api: HotelApi,
    private val db: HotelDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): HotelRepository {

    override suspend fun like(keyword: Product) {
        withContext(ioDispatcher) {
            launch { db.listDao.update(keyword.toProductListEntity()) }
            launch { db.saveDao.like(keyword.toProductSaveEntity()) }
        }
    }
    override suspend fun unLike(keyword: Product) {
        withContext(ioDispatcher) {
            launch { db.listDao.update(keyword.toProductListEntity()) }
            launch { db.saveDao.unLike(keyword.toProductSaveEntity()) }
        }
    }

    override fun getSavedHotelList(categoryCode: Int, orderCode: Int): Flow<List<Product>> {
        return db.saveDao.getHotelList(categoryCode, orderCode).map { flow ->
            flow.map { productSaveEntity ->
                productSaveEntity.toProduct()
            }
        }
    }


    override suspend fun getLikeState(id: Int): Boolean {
        return db.saveDao.getSavedState(id)
    }

    override suspend fun getRemoteHotelList(page: Int): Hotel {
        return api.getHotelInfo(page)
    }

    override suspend fun clear() {
        db.listDao.clearAll()
    }

    override suspend fun insertAll(item: List<Product>) {
        db.listDao.insertAll(item.map { it.toProductListEntity() })
    }

    override fun getProductListFlow(): Flow<List<Product>> {
        return db.listDao.getHotelLIst()
            .map { flow ->
                flow.map { productListEntity ->
                    productListEntity.toProduct()
                }
            }
    }

    override suspend fun getCount(): Int {
        return db.listDao.getCount()
    }

}