package com.example.seapedia.presentation.buyer.cart.state

import com.example.seapedia.data.remote.body.cart.CartItemBody
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.body.cart.CartItemTypeCartItemBodySend
import com.example.seapedia.data.remote.body.cart.ProductTypeItemCartItemBodySend

import com.example.seapedia.domain.entities.Region

data class CartItemFormState(
    val productId: Int = 0,
    val quantity: Int = 1,
    val selectedTypes: List<CartProductTypeForm> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val quantityError: Boolean = false,
) {

    val canSubmit: Boolean
        get() = quantity > 0 &&
                !quantityError &&
                selectedTypes.all { type ->
                    !type.isRequired || type.selectedItemIds.isNotEmpty()
                }
}
data class CartProductTypeForm(
    val productTypeId: Int,

    val isRequired: Boolean,

    val isMultiple: Boolean,

    val selectedItemIds: List<Int> = emptyList(),

    val error: Boolean = false
)

fun CartItemFormState.toBody(): CartItemBodySend {
    return CartItemBodySend(
        productId = productId,
        quantity = quantity,
        types = selectedTypes.map { type ->
            CartItemTypeCartItemBodySend(
                productTypeId = type.productTypeId,
                items = type.selectedItemIds.map { itemId ->
                    ProductTypeItemCartItemBodySend(
                        productTypeItemId = itemId
                    )
                }
            )
        }
    )
}

