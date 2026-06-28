package com.example.seapedia.presentation.seller.product.state
import com.example.seapedia.data.remote.body.CreateProductType

interface ProductFormState {
    val name: String
    val price: String
    val stock: String
    val types : List<CreateProductType>

    val isLoading: Boolean
    val error: String

    val nameError: Boolean
    val priceError: Boolean
    val stockError: Boolean
}