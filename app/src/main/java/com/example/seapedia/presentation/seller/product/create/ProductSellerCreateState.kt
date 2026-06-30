package com.example.seapedia.presentation.seller.product.create

import android.net.Uri
import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.presentation.seller.product.state.ProductFormState

data class ProductSellerCreateState(

    override val name: String = "",
    override val price: String = "",
    override val stock: String = "",

    override val types: List<CreateProductType> = listOf<CreateProductType>(),
    val images : List<Uri> = listOf<Uri>(),
    override val isLoading: Boolean = false,
    override val error: String = "",

    override val nameError: Boolean = true,
    override val priceError: Boolean = true,
    override val stockError: Boolean = true,
) : ProductFormState