package com.example.seapedia.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seapedia.ui.theme.Black
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.Grey

@Composable
fun ButtonCustom(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    isNotLoading: Boolean,
    title: String,
    onClick : () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            vertical = Dimens.ButtonVerticalPadding
        ),
        shape = RoundedCornerShape(Dimens.ButtonCorner),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = Grey,
            disabledContentColor = Black.copy(alpha = 0.38f)
        ),
        onClick = {
            if(enabled && isNotLoading){
                onClick()
            }
        }
    ){
        if (isNotLoading){
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }else{
            CircularProgressIndicator(
                modifier = modifier.size(25.dp),
                color = Grey
            )
        }
    }
}