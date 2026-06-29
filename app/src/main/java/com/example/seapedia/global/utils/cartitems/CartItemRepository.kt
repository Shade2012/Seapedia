package com.example.seapedia.global.utils.cartitems

import android.util.Log
import com.example.seapedia.data.remote.responses.carts.CartItemResponse
import com.example.seapedia.data.remote.responses.carts.DiscountResponse
import com.example.seapedia.data.remote.responses.carts.ProductImageResponse
import com.example.seapedia.data.remote.responses.carts.ProductResponse
import com.example.seapedia.data.remote.responses.carts.ProductTypeResponse
import com.example.seapedia.data.remote.responses.carts.PromoResponse
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.usecases.carts.GetCartUseCase
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartItemRepository @Inject constructor(
    private val getCartUseCase: GetCartUseCase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _cartItemsState = MutableStateFlow(CartItemsState())
    val cartItemsState = _cartItemsState.asStateFlow()

    fun onReload(){
        scope.launch{
            getCartUseCase.run().collect { result ->
                if(result is CommonState.Success){
                    updateState {
                        copy(
                            cartItems = result.data.cartItems
                        )
                    }
                }
            }
        }
    }

    fun checkIsExistInCartItem(productId: Int) : Int {
        return cartItemsState.value.cartItems.filter {
            it.product.id == productId
        }.sumOf { it.quantity }
    }

    fun addQuantity(cartItemId: Int) {
        updateState {
            copy(
                cartItems = cartItems.map { cartItem ->
                    if (cartItem.id == cartItemId) {

                        val unitPrice =
                            cartItem.product.price +
                                    cartItem.cartProductTypes.sumOf { type ->
                                        type.cartProductTypeItems.sumOf {
                                            it.productTypeItem.price
                                        }
                                    }

                        val maxStock = buildList {
                            add(cartItem.product.stock)

                            cartItem.cartProductTypes.forEach { type ->
                                type.cartProductTypeItems.forEach {
                                    add(it.productTypeItem.stock)
                                }
                            }
                        }.min()

                        val newQuantity =
                            (cartItem.quantity + 1).coerceAtMost(maxStock)

                        cartItem.copy(
                            quantity = newQuantity,
                            subTotal = unitPrice * newQuantity
                        )
                    } else {
                        cartItem
                    }
                }
            )
        }
    }

    fun decrementQuantity(cartItemId: Int) {
        updateState {
            copy(
                cartItems = cartItems.mapNotNull { cartItem ->
                    if (cartItem.id == cartItemId) {

                        val newQuantity = cartItem.quantity - 1

                        if (newQuantity <= 0) {
                            null // Remove this item from the list
                        } else {
                            val unitPrice =
                                cartItem.product.price +
                                        cartItem.cartProductTypes.sumOf { type ->
                                            type.cartProductTypeItems.sumOf {
                                                it.productTypeItem.price
                                            }
                                        }

                            cartItem.copy(
                                quantity = newQuantity,
                                subTotal = unitPrice * newQuantity
                            )
                        }
                    } else {
                        cartItem
                    }
                }
            )
        }
    }

    fun addToCart(cartItem: CartItemResponse): List<CartItemResponse> {
        updateState {
            val index = cartItems.indexOfFirst {
                it.isSameVariant(cartItem)
            }

            if (index == -1) {
                copy(
                    cartItems = cartItems + cartItem
                )
            } else {
                val updated = cartItems.toMutableList()

                val current = updated[index]

                updated[index] = current.copy(
                    quantity = current.quantity + cartItem.quantity,

                    subTotal = current.subTotal + cartItem.subTotal
                )

                copy(
                    cartItems = updated
                )
            }
        }
        return cartItemsState.value.cartItems.filter {
            it.product.id == cartItem.product.id
        }
    }

    fun replaceCartItems(replaceCartItems: List<CartItemResponse>){
        updateState {
            copy(cartItems = replaceCartItems)
        }
    }

    fun removeCardItem(cartItem: CartItemResponse){
        val newCartItems = cartItemsState.value.cartItems.filterNot {
            it.id == cartItem.id
        }
        updateState {
            copy(cartItems = newCartItems)
        }
    }

    fun clearCart(){
        updateState {
            copy(
                cartItems = listOf()
            )
        }
    }

    fun CartItemResponse.isSameVariant(other: CartItemResponse): Boolean {

        if (product.id != other.product.id) return false

        if (cartProductTypes.size != other.cartProductTypes.size) return false

        val thisTypes = cartProductTypes
            .sortedBy { it.productType.id }

        val otherTypes = other.cartProductTypes
            .sortedBy { it.productType.id }

        return thisTypes.zip(otherTypes).all { (first, second) ->

            if (first.productType.id != second.productType.id) {
                return@all false
            }

            first.cartProductTypeItems
                .map { it.productTypeItem.id }
                .sorted() ==
                    second.cartProductTypeItems
                        .map { it.productTypeItem.id }
                        .sorted()
        }
    }

    private fun updateState(
        updateState: CartItemsState.() -> CartItemsState
    ){
        _cartItemsState.update {
            it.updateState()
        }
    }
}