package com.example.seapedia.domain.entities

data class SellerProductEntity(
    val total:Int,
    val countAvailable: Int,
    val productsAvailable: List<ProductEntity>,
    val countUnAvailable: Int,
    val productsUnavailable: List<ProductEntity>
)