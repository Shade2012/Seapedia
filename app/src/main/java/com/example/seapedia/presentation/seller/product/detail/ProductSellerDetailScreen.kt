package com.example.seapedia.presentation.seller.product.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.ProductImage
import com.example.seapedia.domain.entities.toCreateProductType
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.GalleryImageItem
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.seller.product.detail.shimmer.ProductSellerDetailShimmer
import com.example.seapedia.presentation.seller.product.widgets.ImageGalleryDialog
import com.example.seapedia.presentation.seller.product.widgets.MainImage
import com.example.seapedia.presentation.seller.product.widgets.ProductTypeSection
import com.example.seapedia.presentation.seller.product.widgets.RemainingImage
import com.example.seapedia.presentation.seller.product.widgets.ThumbnailImage
import com.example.seapedia.presentation.seller.widgets.SellerBody
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White
import kotlin.collections.chunked
import kotlin.collections.forEachIndexed

@Composable
fun ProductSellerDetailScreen(
    sellerNavController : NavController,
    viewModel: ProductSellerDetailViewModel = hiltViewModel<ProductSellerDetailViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    SellerBody(scrollState, enableRefresh = false){
        TopAppBarCustom(
            onBackClick = {
                sellerNavController.popBackStack()
            },
            title = "Detail Product"
        )
        when(val result = state.product){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = result.message)
            }
            is CommonState.Loading<*> -> {
                ProductSellerDetailShimmer()
            }
            is CommonState.Success -> {
                ProductHeaderSection(product = result.data)
                if(result.data.listImages.isNotEmpty()){
                    ProductImageDetail(result.data.listImages)
                }else{
                    MainImage()
                }
                ProductDetailSection(result.data)
                ProductTypeSection(
                    types = state.product.data.types.map { it.toCreateProductType() },
                    enable = false,
                    onDeleteType = { _ -> },
                    onChangeTypeName = { _, _ -> },
                    onChangeTypeRequired = { _, _ -> },
                    onChangeTypeMultiple = { _, _ -> },
                    onAddItem = { _ -> },
                    onDeleteItem = { _, _ -> },
                    onChangeItemName = { _, _, _ -> },
                    onChangeItemPrice = { _, _, _ -> },
                    onChangeItemStock = { _, _, _ -> },
                )
            }
        }
    }
}

@Composable
fun ProductHeaderSection(
    product: Product
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(Dimens.CardCorner))
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .padding(all = Dimens.InnerPadding)

    ) {
        Text(text = product.name, style = MaterialTheme.typography.titleMedium.copy(
            color = White
        ))
    }
}
@Composable
fun ProductDetailSection(
    product: Product
) {
    product.category?.let {
        Text("Category : ${product.category.name}", style = MaterialTheme.typography.titleMedium)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Price : ", style = MaterialTheme.typography.titleMedium)
        Text(text = Formatting().formatRupiah(product.price.toString()), style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        ))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Stock : ", style = MaterialTheme.typography.titleMedium)
        Text(text = product.stock.toString(), style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        ))
    }
}
@Composable
fun ProductImageDetail(
    images: List<ProductImage>
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MainImage(
            imageUrl= images[0].imageUrl,
            onClick = {
                selectedIndex = 0
                showDialog = true
            }
        )
        val thumbnails = images.drop(1)
        thumbnails
            .chunked(2)
            .take(1)
            .forEach { rowImages ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowImages.forEachIndexed { index, entity ->
                        ThumbnailImage(
                            modifier = Modifier.weight(1f),
                            imageUrl = entity.imageUrl,
                            onClick = {
                                selectedIndex = index + 1
                                showDialog = true
                            }
                        )
                    }
                    if (rowImages.size == 1) {
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

        val remaining = thumbnails.drop(2)
        if (remaining.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                RemainingImage(
                    remaining = remaining.size,
                    onClick = {
                        selectedIndex = 3
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog) {
        ImageGalleryDialog(
            images = images.mapIndexed { index, entity ->
                GalleryImageItem.Url(entity.id,entity.imageUrl,index)
            },
            selectedIndex = selectedIndex,
            onDismiss = {
                showDialog = false
            },
            enable = false
        )
    }
}