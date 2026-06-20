package com.example.seapedia.domain.entities

data class ProductEntity(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int,
    val isAvailable: Boolean,
    val listImages: List<ProductImageEntity>,
    val store: StoreEntity,
    val category: ProductCategoryEntity,
    val types : List<ProductType>
)
data class ProductImageEntity(
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