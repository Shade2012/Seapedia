package com.example.seapedia.domain.entities

data class SellerProduct(
    val total:Int,
    val countAvailable: Int,
    val productsAvailable: List<Product>,
    val countUnAvailable: Int,
    val productsUnavailable: List<Product>
)