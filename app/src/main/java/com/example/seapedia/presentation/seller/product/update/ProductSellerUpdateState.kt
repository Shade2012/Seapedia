package com.example.seapedia.presentation.seller.product.update

import android.net.Uri
import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.presentation.seller.product.state.ProductFormState

data class ProductSellerUpdateState(

    override val name: String = "Baju Castle",
    override val price: String = "200000",
    override val stock: String = "20",

    override val types: List<CreateProductType> = listOf<CreateProductType>(),
    val images : List<Uri> = listOf<Uri>(),
    override val isLoading: Boolean = false,
    override val error: String = "",

    override val nameError: Boolean = true,
    override val priceError: Boolean = true,
    override val stockError: Boolean = true,
) : ProductFormState