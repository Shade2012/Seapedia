package com.example.seapedia.domain.entities

data class Cart(
    val id: Int,
    val storeId:Int,
    val subTotal:Int,
    val cartItems: List<CartItem>
)

data class CartItem(
    val id: Int,
    val quantity : Int,
    val productCartItem: ProductCartItem,
    val subTotalCartItem: Int,
    val cartProductTypes: List<CartProductTypes>
)

data class CartProductTypes(
    val id: Int,
    val productType: ProductType,
    val cartProductTypeItems: List<CartProductTypeItem>
)

data class ProductCartItem(
    val id: Int,
    val name:String,
    val price:Int,
    val stock:Int,
    val isAvailable: Boolean,
    val promo : Promo?,
    val images: ProductImage
)

data class CartProductTypeItem(
    val id:Int,
    val productCartTypeItem: ProductCartTypeItem
)

data class ProductCartTypeItem(
    val id: Int,
    val name:String,
    val stock:Int,
    val price: Int
)