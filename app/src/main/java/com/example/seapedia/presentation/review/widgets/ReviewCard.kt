package com.example.seapedia.presentation.review.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.seapedia.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.utils.TimeFormatting
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.Grey
import com.example.seapedia.ui.theme.Yellow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    review: Review,
    daySystem: Instant
) {
    Box(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .border(2.dp, Grey, RoundedCornerShape(16.dp))
            .padding(Dimens.InnerPadding)
        ,
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = modifier
                            .clip(shape = CircleShape)
                            .size(50.dp),
                        painter = painterResource(R.drawable.default_image_person),
                        contentDescription = "Review Person Image"
                    )
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = review.reviewerName, style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ))
                        Text(text = TimeFormatting.formatRelativeTime(daySystem,review.createdAt), style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ))
                    }
                }
                RatingBar(
                    readOnly = true,
                    ratingState = review.rating,
                )
            }
            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    readOnly: Boolean,
    size: Dp = 12.dp,
    ratingState: Int,
    onClick: (Int) -> Unit = {},
) {
    Row(
        modifier = modifier.wrapContentSize()
    ) {
        for (value in 1..5){
            StarIcon(
                readOnly = readOnly,
                ratingState = ratingState,
                size = size,
                value = value,
                onClick = onClick
            )
        }
    }
}

@Composable
fun StarIcon(
    size: Dp,
    readOnly: Boolean,
    ratingState : Int = 0,
    value: Int,
    selectedColor: Color = Yellow,
    unselectedColor : Color = Grey,
    onClick:(Int) -> Unit
) {
    val condition = ratingState >= value
    val tint by animateColorAsState(
        targetValue = if(condition) selectedColor else unselectedColor
    )
    val icon = ImageVector.vectorResource(if(condition) R.drawable.star_selected else R.drawable.star_unselected)
    Column {
        Icon(
            modifier = Modifier
                .size(size)
                .clickable {
                    if (!readOnly) {
                        onClick(value)
                    }
                },
            imageVector = icon,
            tint = tint,
            contentDescription = "Star Icon"
        )
    }
}