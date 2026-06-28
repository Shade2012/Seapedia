package com.example.seapedia.presentation.seller.product.update

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.seller.product.widgets.ProductForm
import com.example.seapedia.presentation.seller.widgets.SellerBody

@Composable
fun ProductSellerUpdateScreen(
    sellerNavController: NavController,
    viewModel: ProductSellerUpdateViewModel = hiltViewModel<ProductSellerUpdateViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
            viewModel.navigateToProductAll.collect {
                sellerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_product",true)
                sellerNavController.popBackStack()
            }
        }
    SellerBody(scrollState,enableRefresh = false) {
        TopAppBarCustom(onBackClick = {
            sellerNavController.popBackStack()
        },title = "Update Product")
        ProductForm(
            state = state,
            onChangeName = viewModel::onChangeName,
            onChangePrice = viewModel::onChangePrice,
            onChangeStock = viewModel::onChangeStock,
            onDeleteImage = { _ -> },
            onImagesSelected = { _ -> },
            onAddType = viewModel::onAddType,
            onDeleteType = viewModel::onDeleteType,
            onChangeTypeName = viewModel::onChangeTypeName,
            onChangeTypeRequired = viewModel::onChangeTypeRequired,
            onChangeTypeMultiple = viewModel::onChangeTypeMultiple,
            onAddItem = viewModel::onAddItem,
            onDeleteItem = viewModel::onDeleteItem,
            onChangeItemName = viewModel::onChangeItemName,
            onChangeItemPrice = viewModel::onChangeItemPrice,
            onChangeItemStock = viewModel::onChangeItemStock,
        )
        ButtonCustom(
            enabled = !state.nameError and !state.priceError and !state.stockError &&
                    state.types.all { type ->
                        type.name.isNotBlank() &&
                                type.items.isNotEmpty() &&
                                type.items.all { item ->
                                    item.name.isNotBlank()
                                }
                    },
            isNotLoading = !state.isLoading,
            title = "Update Product",
            onClick = viewModel::updateProduct
        )
    }
}

//Button(
//onClick = {
//    Log.d(
//        "ProductCreate",
//        """
//            name=${state.name}
//            price=${state.price}
//            stock=${state.stock}
//            images=${state.images.size}
//            types=${state.types}
//            isLoading=${state.isLoading}
//            nameError=${state.nameError}
//            priceError=${state.priceError}
//            stockError=${state.stockError}
//            typesError=${state.types.all { type ->
//            type.name.isNotBlank() &&
//                    type.items.isNotEmpty() &&
//                    type.items.all { item ->
//                        item.name.isNotBlank()
//                    }
//        }}
//            """.trimIndent()
//    )
//}
//) {
//    Text("Log State")
//}