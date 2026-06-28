package com.example.seapedia.presentation.buyer.home.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White


@Composable
fun HomeProductCard(
    modifier: Modifier,
    product: Product,
    isGuest: Boolean,
    onClick: () -> Unit,
)
{
    Card(
        modifier = modifier.clickable(
            onClick = onClick
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column (modifier.fillMaxWidth()){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ImageCustom(
                    modifier= Modifier.fillMaxWidth().height(200.dp).clip(
                        shape = RoundedCornerShape(
                            topEnd = 16.dp,
                            topStart = 16.dp
                        )
                    ),
                    imageUrl = product.listImages.firstOrNull()?.imageUrl,
                    contentDescription = "Product Image"
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(Dimens.InnerPadding),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.store!!.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                if(product.category != null)
                    Text(
                        text = product.category.name ,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                Text(
                    text = "Rp ${Formatting.rupiahFormatter.format(product.price)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Stock: ${product.stock}",
                    style = MaterialTheme.typography.bodySmall
                )
                if (!isGuest) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }
                    ) {
                        Text(text = "Add To Cart", style = MaterialTheme.typography.bodyMedium.copy(
                            color = White
                        ))
                    }
                }
            }
        }
    }
}