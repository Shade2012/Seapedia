package com.example.seapedia.presentation.buyer.product


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.cart.add.CartItemBottomSheet
import com.example.seapedia.presentation.buyer.product.shimmer.ProductDetailBuyerShimmer
import com.example.seapedia.presentation.buyer.product.widgets.ProductDetailBody
import com.example.seapedia.presentation.buyer.product.widgets.ProductDetailImages
import com.example.seapedia.presentation.buyer.product.widgets.StoreOverviewWidget
import com.example.seapedia.presentation.buyer.widgets.AddToCartSection
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun ProductDetailBuyerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    productDetailBuyerViewModel: ProductDetailBuyerViewModel = hiltViewModel<ProductDetailBuyerViewModel>(),
    isGuest: Boolean,
) {
    val state = productDetailBuyerViewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    var addProduct by remember {
        mutableStateOf<Product?>(null)
    }
    val cartState by productDetailBuyerViewModel.cartItemStateGlobal.collectAsStateWithLifecycle()
    val cartItems = when (val productState = state.product) {
        is CommonState.Success -> {
            cartState.cartItems.filter {
                it.product.id == productState.data.id
            }
        }

        else -> emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(Dimens.InnerPadding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        TopAppBarCustom(
            title = "Product Detail",
            onBackClick = {
                navController.popBackStack()
            }
        )
        when(state.product){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = state.product.message)
            }
            is CommonState.Loading<*> -> {
                ProductDetailBuyerShimmer()
            }
            is CommonState.Success<Product> -> {
                val product = state.product.data
                ProductDetailSection(
                    modifier,
                    state.selectedImage,
                    product,
                    productDetailBuyerViewModel::onClickDetailImage
                )
                if(!isGuest){
                    val quantity = productDetailBuyerViewModel.quantityCheckInCart(product.id)
                    AddToCartSection(
                        quantity = quantity,
                        onClick = {
                            if(quantity < 1){
                                navController.navigate(BuyerRoute.CartItemCreate.createRoute(product.id))
                            }else{
                                addProduct = product
                            }
                        },
                    )
                }
            }
        }
    }
    CartItemBottomSheet(
        isVisible = addProduct != null,
        isLoading = state.bottomSheetLoading ?: false,
        onDismiss = {
            addProduct = null
        },
        cartItemList = cartItems,
        product = addProduct ?: Product.EMPTY,
        onDecrement = { cartItemId, quantity ->
            productDetailBuyerViewModel.onDecrement(quantity, cartItemId)
        },
        onIncrement = { cartItemId, quantity ->
            productDetailBuyerViewModel.onIncrement(quantity, cartItemId, addProduct!!.stock)
        },
        onUpdateProduct = {

        },
        onAddCartItem = {
            addProduct?.let {
                navController.navigate(BuyerRoute.CartItemCreate.createRoute(addProduct!!.id))
            }
            addProduct = null
        },
    )
}

@Composable
fun ProductDetailSection(
    modifier: Modifier = Modifier,
    selectedImage : String? = null,
    product: Product,
    onClick: (String) -> Unit,
) {
    if(selectedImage != null)
        ProductDetailImages(
            modifier,
            productImages = product.listImages,
            onClick = onClick,
            primaryImageUrl = selectedImage
        )
    StoreOverviewWidget(store = product.store!!)
    ProductDetailBody(modifier,product)
}






