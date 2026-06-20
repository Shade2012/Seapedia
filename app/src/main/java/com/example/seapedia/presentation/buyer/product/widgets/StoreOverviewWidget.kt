package com.example.seapedia.presentation.buyer.product.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seapedia.R
import com.example.seapedia.domain.entities.StoreEntity
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White

@Composable
fun StoreOverviewWidget(
    modifier: Modifier = Modifier,
    store: StoreEntity
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(
                color = MaterialTheme.colorScheme.tertiary
            )
            .padding(Dimens.InnerPadding)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onTertiary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = ImageVector.vectorResource(R.drawable.store_icon),
                contentDescription = "Store Icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.padding(horizontal = Dimens.SpacePadding))
        Column (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(store.name, style = MaterialTheme.typography.bodyMedium.copy(
                color = White,
                fontWeight = FontWeight.Medium
            ))
            Spacer(Modifier.padding(Dimens.SpacePadding))
            Text(store.phoneNumber, style = MaterialTheme.typography.bodySmall.copy(
                color = White
            ))
        }
    }
}