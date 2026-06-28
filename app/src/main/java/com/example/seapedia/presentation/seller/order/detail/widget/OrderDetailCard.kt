package com.example.seapedia.presentation.seller.order.detail.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.example.seapedia.ui.theme.Dimens

@Composable
fun OrderDetailCard(
    title:String,
    content: @Composable () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        if (expanded) 90f else 0f,
        label = "Arror Rotation"
    )
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.InnerPadding)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.InnerPadding)
                .clickable {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                modifier = Modifier.rotate(rotation),
                contentDescription = null
            )
        }
        AnimatedVisibility(
            visible = expanded,
        ){
            Column(
                modifier = Modifier.padding(Dimens.InnerPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
            ) {
                content()
            }
        }
    }
}