package com.example.seapedia.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seapedia.R
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White

@Composable
fun BalanceSection(
    modifier: Modifier = Modifier,
    amount:Int,
    title:String,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(Dimens.InnerPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = White
                    )
                )

                Text(
                    text = Formatting().formatRupiah(amount.toString()),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = White
                    ),
                    fontWeight = FontWeight.Bold
                )

                content()
            }

            Spacer(modifier = Modifier.width(Dimens.SpacePadding))

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.money_bag),
                contentDescription = "Money Icon",
                tint = White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}