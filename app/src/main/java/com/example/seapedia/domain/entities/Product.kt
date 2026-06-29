package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.data.remote.body.CreateProductTypeItem
import com.example.seapedia.data.remote.responses.carts.CartItemResponse
import com.example.seapedia.data.remote.responses.carts.ProductImageResponse
import com.example.seapedia.data.remote.responses.carts.ProductResponse
import com.example.seapedia.data.remote.responses.carts.PromoResponse
import com.example.seapedia.global.utils.TempIdGenerator
import com.example.seapedia.presentation.buyer.cart.state.CartItemFormState
import com.example.seapedia.presentation.buyer.cart.state.CartProductTypeForm

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int,
    val isAvailable: Boolean,
    val listImages: List<ProductImage>,
    val promo: PromoResponse? = null,
    val store: Store? = null,
    val category: ProductCategoryEntity? = null,
    val types : List<ProductType>,

){
    companion object {
        val EMPTY = Product(
            id = 0,
            name = "",
            price = 0,
            stock = 0,
            isAvailable = false,
            listImages = emptyList(),
            promo = null,
            store = null,
            category = null,
            types = emptyList()
        )
    }
}

data class ProductImage(
    val id: Int,
    val imageUrl: String
)
data class ProductType(
    val id: Int,
    val name: String,
    val isMultiple: Boolean,
    val isRequired: Boolean,
    val listItems: List<ProductTypeItem>?
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
    items = listItems?.map { it.toCreateProductTypeItem() } ?: listOf()
)

fun ProductTypeItem.toCreateProductTypeItem() = CreateProductTypeItem(
    name = name,
    price = price,
    stock = stock
)

fun Product.toCartItemResponse(): CartItemResponse {
    return CartItemResponse(
        id = TempIdGenerator.nextId(), // temporary id
        quantity = 1,
        product = ProductResponse(
            id = id,
            name = name,
            price = price,
            stock = stock,
            isAvailable = isAvailable,
            images = listImages.map {
                ProductImageResponse(
                    id = it.id,
                    imageUrl = it.imageUrl,
                )
            },
            promo = promo,
        ),
        subTotal = price,
        cartProductTypes = emptyList(),
    )
}

fun Product.toCartItemFormState(): CartItemFormState {
    return CartItemFormState(
        productId = id,
        quantity = 1,
        selectedTypes = types.map {
            CartProductTypeForm(
                productTypeId = it.id,
                isRequired = it.isRequired,
                isMultiple = it.isMultiple,
                selectedItemIds = emptyList()
            )
        }
    )
}