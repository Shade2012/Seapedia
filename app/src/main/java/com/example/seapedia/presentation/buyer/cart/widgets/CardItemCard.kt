package com.example.seapedia.presentation.buyer.cart.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.seapedia.R
import com.example.seapedia.data.remote.responses.carts.CartItemResponse
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.Grey

@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    cartItem: CartItemResponse,
    onIncrease: (cartItemId: Int, quantity:Int) -> Unit,
    onDecrease: (cartItemId: Int, quantity:Int) -> Unit,
    onClick: (productId:Int) -> Unit = {}
) {
    val originalUnitPrice =
        cartItem.product.price +
                cartItem.cartProductTypes
                    .flatMap { it.cartProductTypeItems }
                    .sumOf { it.productTypeItem.price }

    val originalSubtotal = originalUnitPrice * cartItem.quantity
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClick(cartItem.product.id)
            }),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {

        Row {
            ImageCustom(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(12.dp)),
                imageUrl = cartItem.product.images.firstOrNull()?.imageUrl,
                contentDescription = cartItem.product.name
            )

            Spacer(modifier = Modifier.width(Dimens.SpacePadding))

            Column(
                modifier = Modifier.weight(1f)
            )
            {

                Text(
                    text = cartItem.product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = Formatting().formatRupiah(cartItem.subTotal.toString()) ,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(8.dp))

                    if (cartItem.product.promo != null &&
                        originalSubtotal > cartItem.subTotal
                    ) {
                        Text(
                            text =  Formatting().formatRupiah(originalSubtotal.toString()),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                if (cartItem.product.promo != null) {

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${cartItem.product.promo.discount.discountPercentage}% OFF",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.error
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                cartItem.cartProductTypes.forEach { type ->

                    Text(
                        text = buildString {

                            append(type.productType.name)

                            append(" : ")

                            append(
                                type.cartProductTypeItems.joinToString {
                                    it.productTypeItem.name
                                }
                            )
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp,
                        Grey,
                        RoundedCornerShape(10.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            )
            {

                IconButton(
                    onClick = {
                        onDecrease(cartItem.id,cartItem.quantity)
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.minus_icon),
                        contentDescription = "Decrease quantity"
                    )
                }

                Text(
                    text = cartItem.quantity.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = {
                        onIncrease(cartItem.id,cartItem.quantity)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase quantity"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.End
            )
            {

                Text(
                    text = "Subtotal",
                    style = MaterialTheme.typography.labelSmall
                )

                Text(
                    text = Formatting().formatRupiah(cartItem.subTotal.toString()),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}