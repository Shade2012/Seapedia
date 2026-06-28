package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.data.remote.body.CreateProductTypeItem

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int,
    val isAvailable: Boolean,
    val listImages: List<ProductImage>,
    val store: Store? = null,
    val category: ProductCategoryEntity? = null,
    val types : List<ProductType>
)
data class ProductImage(
    val id: Int,
    val imageUrl: String
)
data class ProductType(
    val id: Int,
    val name: String,
    val isMultiple: Boolean,
    val isRequired: Boolean,
    val listItems: List<ProductTypeItem>
)
data class ProductTypeItem(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int
)
data class ProductCategoryEntity(
    val id: Int,
    val name: String
)

fun ProductType.toCreateProductType() = CreateProductType(
    name = name,
    isMultiple = isMultiple,
    isRequired = isRequired,
    items = listItems.map { it.toCreateProductTypeItem() }
)

fun ProductTypeItem.toCreateProductTypeItem() = CreateProductTypeItem(
    name = name,
    price = price,
    stock = stock
)