package com.example.seapedia.presentation.buyer.product.widgets

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.ProductImage
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.ui.theme.Dimens
import kotlin.collections.forEach

@Composable
fun ProductDetailImages(
    modifier: Modifier = Modifier,
    productImages: List<ProductImage>,
    onClick: (String) -> Unit,
    primaryImageUrl: String
) {
    val scrollState: ScrollState = rememberScrollState()
    Column {
        ImageCustom(
            Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(
                    shape = RoundedCornerShape(16.dp)
                ),
            imageUrl = primaryImageUrl,
            contentDescription = "Primary Image Product Detail"
        )
        Spacer(Modifier.padding(vertical = Dimens.SpacePadding))
        Row(
            modifier.horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {
            productImages.forEach {
                ImageCustom(
                    Modifier
                        .then(
                            if(it.imageUrl == primaryImageUrl){
                                Modifier.border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            } else {
                                Modifier
                            }
                        )
                        .size(150.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(
                            onClick = {
                                onClick(it.imageUrl)
                            }
                        ),
                    imageUrl = it.imageUrl,
                    contentDescription = "Product Image ${it.id}"
                )
            }
        }
    }
}