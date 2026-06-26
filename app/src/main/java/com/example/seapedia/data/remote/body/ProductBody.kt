package com.example.seapedia.data.remote.body

data class CreateProductBody(
    val name: String,
    val price: Int,
    val stock: Int,
    val isAvailable: Boolean,
    val types: List<CreateProductType>
)

data class CreateProductType(
    val name: String,
    val isMultiple: Boolean,
    val isRequired: Boolean,
    val items: List<CreateProductTypeItem>
)

data class CreateProductTypeItem(
    val name: String,
    val stock: Int,
    val price: Int
)