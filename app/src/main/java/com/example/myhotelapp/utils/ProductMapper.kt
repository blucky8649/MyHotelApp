package com.example.myhotelapp.utils

import com.example.myhotelapp.model.Product
import com.example.myhotelapp.model.entitiies.ProductSaveEntity
import com.example.myhotelapp.model.entitiies.ProductListEntity

fun ProductListEntity.toProduct(): Product {
    return Product(
        description = this.description,
        id = this.id.toInt(),
        name = this.name,
        rate = this.rate,
        thumbnail = this.thumbnail,
        likeState = this.likeState,
        time = this.time
    )
}
fun Product.toProductListEntity(): ProductListEntity {
    return ProductListEntity(
        description = this.description,
        id = this.id.toString(),
        name = this.name,
        rate = this.rate,
        thumbnail = this.thumbnail,
        likeState = this.likeState,
        time = this.time
    )
}
fun ProductSaveEntity.toProduct(): Product {
    return Product(
        description = this.description,
        id = this.id,
        name = this.name,
        rate = this.rate,
        thumbnail = this.thumbnail,
        likeState = this.likeState,
        time = this.time
    )
}
fun Product.toProductSaveEntity(): ProductSaveEntity {
    return ProductSaveEntity(
        description = this.description,
        id = this.id,
        name = this.name,
        rate = this.rate,
        thumbnail = this.thumbnail,
        likeState = this.likeState,
        time = this.time
    )
}