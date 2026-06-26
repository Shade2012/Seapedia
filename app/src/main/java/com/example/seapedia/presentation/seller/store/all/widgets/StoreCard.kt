package com.example.seapedia.presentation.seller.store.all.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.StoreEntity
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White

@Composable
fun StoreCard(
    store: StoreEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(Dimens.CardCorner)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImageCustom(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(
                        shape = RoundedCornerShape(Dimens.CardCorner)
                    ),
                imageUrl = store.image,
                contentDescription = "Store Image"
            )

            TitleStoreSection(
                text = store.name,
                onClick = onClick
            )

            Text(
                text = "Phone Number",
                style = MaterialTheme.typography.labelMedium,
                color = White
            )

            PhoneNumberSection(store.phoneNumber)

            AddressSection(store.address)
        }
    }
}

@Composable
fun TitleStoreSection(
    text: String,
    onClick: () -> Unit,
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = onClick
        ) {
            Box (
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(Dimens.IconPadding)


            ){
                IconCustom(
                    contentDescription = "Edit Icon",
                    icon = Icons.Default.Edit,
                )
            }
        }
    }
}

@Composable
fun PhoneNumberSection(text : String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = White
            )
        )
    }
}

@Composable
fun AddressSection(text: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Address",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = White
                ),
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onTertiary,
                    RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = White
            )
        )
    }
}
