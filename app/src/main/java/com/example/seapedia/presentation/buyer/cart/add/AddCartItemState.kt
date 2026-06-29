package com.example.seapedia.presentation.buyer.cart.add

import android.net.Uri
import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.cart.state.CartItemFormState
import com.example.seapedia.presentation.seller.product.state.ProductFormState



data class AddCartItemState(
    val state: CartItemFormState = CartItemFormState(),
    val product: CommonState<Product> = CommonState.Loading(),
    val isLoading: Boolean = false
)

//    override val name: String = "Baju Castle",
//    override val price: String = "200000",
//    override val stock: String = "20",
//
//    override val types: List<CreateProductType> = listOf<CreateProductType>(),
//    val images : List<Uri> = listOf<Uri>(),
//    override val isLoading: Boolean = false,
//    override val error: String = "",
//
//    override val nameError: Boolean = true,
//    override val priceError: Boolean = true,
//    override val stockError: Boolean = true,
