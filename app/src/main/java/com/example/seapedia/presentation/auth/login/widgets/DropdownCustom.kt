package com.example.seapedia.presentation.auth.login.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seapedia.ui.theme.Black
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.Grey
import com.example.seapedia.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownCustom(
    modifier: Modifier = Modifier,
    list: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemLabel: (T) -> String,
    label: String,
    isLoading: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    val shape = if (expanded) {
        RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    } else {
        RoundedCornerShape(8.dp)
    }

    Column(
        modifier = Modifier.padding(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ){
        Text(label, style = MaterialTheme.typography.labelMedium)
        ExposedDropdownMenuBox(
            expanded = expanded && !isLoading,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clip(shape = shape)
                    .background(
                        if (!isLoading)
                            MaterialTheme.colorScheme.tertiary
                        else
                            Grey.copy(alpha = 0.5f)
                    )

                    .padding(horizontal = 12.dp, vertical = 20.dp)
            ) {
                Text(
                    text = selectedItem?.let(itemLabel) ?: "Select item",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (!isLoading)
                            White
                        else
                            Black.copy(alpha = 0.38f)
                    )
                )
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier.background(
                    color = MaterialTheme.colorScheme.tertiary
                )
            ) {
                list.forEach { value ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                itemLabel(value), style = MaterialTheme.typography.labelMedium.copy(
                                    color = White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        },

                        onClick = {
                            onItemSelected(value)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

}
