package com.example.seapedia.presentation.seller.product.all

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.home.SearchBar
import com.example.seapedia.presentation.common.ConfirmationDialogCustom
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.seller.product.all.shimmer.ProductSellerScreenShimmer
import com.example.seapedia.presentation.seller.product.all.widgets.ProductListRowSection
import com.example.seapedia.presentation.seller.product.all.widgets.ProductModalBottomSheet
import com.example.seapedia.presentation.seller.product.all.widgets.productGridSection
import com.example.seapedia.presentation.seller.widgets.SellerLazyBody
import com.example.seapedia.ui.theme.ErrorColor

@Composable
fun ProductSellerScreen(
    sellerNavController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductSellerViewModel = hiltViewModel<ProductSellerViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var productToDelete by rememberSaveable {
        mutableStateOf<Product?>(null)
    }
    var productToEdit by rememberSaveable {
        mutableStateOf<Product?>(null)
    }

    val refresh = sellerNavController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_product",false)
    LaunchedEffect(Unit) {
        refresh?.collect {
                shouldRefresh ->
            if(shouldRefresh){
                viewModel.getProducts()
                sellerNavController.currentBackStackEntry?.savedStateHandle?.set("refresh_product",false)
            }
        }
    }
    SellerLazyBody(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::refreshProduct
    ) {
        item {
            SearchBar(
                text = state.searchName,
                enabled = true,
                onChanged = viewModel::onSearchNameChange
            )
        }
        when (val result = state.data) {
            is CommonState.Loading -> {
                item {
                    ProductSellerScreenShimmer()
                }
            }
            is CommonState.Error -> {
                item {
                    FailedCommonCustom(text = result.message)
                }
            }
            is CommonState.Success -> {
                if(state.searchName.isBlank()){
                    item {
                        if(result.data.productsUnavailable.isNotEmpty())
                        ProductListRowSection(
                            title = "Unavailable Product",
                            titleColor = ErrorColor,
                            products = result.data.productsUnavailable,
                            onDelete = {
                                productToDelete = it
                            },
                            onDetail = {
                                sellerNavController.navigate(SellerRoute.ProductDetail.createRoute(it.id))
                            },
                            onEdit = {
                                productToEdit = it
                            }
                        )
                    }
                    item {
                        ProductListRowSection(
                            title = "Available Product",
                            products = result.data.productsAvailable,
                            onDelete = {
                                productToDelete = it
                            },
                            onDetail = {
                                sellerNavController.navigate(SellerRoute.ProductDetail.createRoute(it.id))
                            },
                            onEdit = {
                                productToEdit = it
                            }
                        )
                    }
                }
                else{
                    item {
                        Text(
                            text = "Search Result for : ${state.searchName}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    productGridSection(
                        products = result.data.productsAvailable + result.data.productsUnavailable,
                        onDelete = {
                            productToDelete = it
                        },
                        onDetail = {
                            sellerNavController.navigate(SellerRoute.ProductDetail.createRoute(it.id))
                        },
                        onEdit = {
                            productToEdit = it
                        }
                    )
                }
                if((result.data.productsAvailable + result.data.productsUnavailable).isEmpty()){
                    item {
                        EmptyCommonCustom(
                            text = "No Product yet.."
                        )
                    }
                }
            }
        }
    }
    ConfirmationDialogCustom(
        visible = productToDelete != null,
        title = "Delete Product",
        message = "Are you sure want to delete this product? this action cannot be undone.",
        confirmText = "Delete",
        onDismiss = {
            productToDelete = null
        },
        onConfirm = {
            productToDelete?.let {
                viewModel.deleteProduct(it.id)
            }
            productToDelete = null
        }
    )
    ProductModalBottomSheet(
        isVisible = productToEdit != null,
        onDismiss = {
            productToEdit = null
        },
        onNavigateUpdateProduct = {
            productToEdit?.let {
                productEntity ->
                sellerNavController.navigate(
                    SellerRoute.ProductUpdate.createRoute(productEntity.id)
                )
            }
            productToEdit = null
        },
        onNavigateUpdateProductImage = {
            productToEdit?.let {
                productEntity ->
                sellerNavController.navigate(
                    SellerRoute.ProductImageUpdate.createRoute(productEntity.id)
                )
            }
            productToEdit = null
        }
    )
}

