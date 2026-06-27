package com.example.seapedia.presentation.seller.product.update.image

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.toCreateProductType
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.GalleryImageItem
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.seller.product.detail.ProductDetailSection
import com.example.seapedia.presentation.seller.product.detail.ProductHeaderSection
import com.example.seapedia.presentation.seller.product.detail.ProductImageDetail
import com.example.seapedia.presentation.seller.product.detail.ProductSellerDetailViewModel
import com.example.seapedia.presentation.seller.product.detail.shimmer.ProductSellerDetailShimmer
import com.example.seapedia.presentation.seller.product.update.shimmer.ProductSellerImageShimmer
import com.example.seapedia.presentation.seller.product.widgets.MainImage
import com.example.seapedia.presentation.seller.product.widgets.MultipleImageCustom
import com.example.seapedia.presentation.seller.product.widgets.ProductTypeSection
import com.example.seapedia.presentation.seller.widgets.SellerBody
import kotlin.collections.emptyList

@Composable
fun ProductSellerUpdateImage(
    sellerNavController : NavController,
    viewModel: ProductSellerUpdateImageViewModel = hiltViewModel<ProductSellerUpdateImageViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.onImageSelected(uris)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.navigateToAllProduct.collect {
            sellerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_product",true)
            sellerNavController.popBackStack()
        }
    }
    SellerBody(scrollState, enableRefresh = false){
        TopAppBarCustom(
            onBackClick = {
                sellerNavController.popBackStack()
            },
            title = "Update Product Image"
        )
        Text("Don't Worry if you accidentally delete or add image as long you don't confirm it,", style = MaterialTheme.typography.bodyMedium)
        when(val result = state.listImage){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = result.message)
            }
            is CommonState.Loading<*> -> {
                Box{}
            }
            is CommonState.Success -> {
                val galleryImages = remember(
                        state.listImage,
                        state.listImageAdd
                    ) {
                        val urls =
                            state.listImage.data.mapIndexed {index, entity ->
                                    GalleryImageItem.Url(
                                        id = entity.id,
                                        imageUrl = entity.imageUrl,
                                        index = index
                                    )
                                }
                        val locals = state.listImageAdd.mapIndexed {
                            index, uri ->
                                GalleryImageItem.Local(uri,index)
                            }
                        urls + locals
                    }

                MultipleImageCustom(
                    Modifier,
                    enableLaunchImage = false,
                    images = galleryImages,
                    maxItems = 10,
                    onDeleteImage = {
                            imageItem ->
                        when(imageItem){
                            is GalleryImageItem.Local ->{
                                viewModel.onDeleteImageUri(imageItem.index)
                            }
                            is GalleryImageItem.Url -> {
                                viewModel.onDeleteImageUrl(imageItem.index,imageItem.id)
                            }
                        }
                    }
                )
                ButtonCustom(
                    modifier = Modifier,
                    enabled = !state.loading,
                    isNotLoading = !state.loading,
                    title = "Add Image",
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
                ButtonCustom(
                    modifier = Modifier,
                    enabled = !state.loading,
                    isNotLoading = !state.loading,
                    title = "Confirm",
                    onClick = viewModel::confirmUpdateImage
                )
            }
        }
    }
}