package com.example.seapedia.presentation.buyer.product


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.product.shimmer.ProductDetailBuyerShimmer
import com.example.seapedia.presentation.buyer.product.widgets.ProductDetailBody
import com.example.seapedia.presentation.buyer.product.widgets.ProductDetailImages
import com.example.seapedia.presentation.buyer.product.widgets.StoreOverviewWidget
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
            is CommonState.Success<ProductEntity> -> {
                val product = state.product.data
                if(state.selectedImage != null)
                    ProductDetailImages(
                        modifier,
                        productImages = product.listImages,
                        onClick = {
                            productDetailBuyerViewModel.onClickDetailImage(it)
                        },
                        primaryImageUrl = state.selectedImage
                    )
                StoreOverviewWidget(store = product.store)
                ProductDetailBody(modifier,product)
                if(!isGuest)
                    ButtonCustom(
                        enabled = true,
                        title = "Add to Cart",
                        onClick = {},
                        loading = true
                    )
            }
        }
    }
}






