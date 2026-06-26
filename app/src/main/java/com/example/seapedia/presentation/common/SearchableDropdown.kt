package com.example.seapedia.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.seapedia.ui.theme.Black
import com.example.seapedia.ui.theme.Grey
import com.example.seapedia.ui.theme.White

@Composable
fun <T> SearchableDropdown(
    modifier: Modifier = Modifier,
    label: String,
    list: List<T>,
    selectedItem: T?,
    itemLabel: (T) -> String,
    onItemSelected: (T) -> Unit,
    placeholder: String = "Select item",
    isLoading: Boolean = false
) {
    var showDialog by remember { mutableStateOf(false) }
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = if (!isLoading)
            White
        else
            Black.copy(alpha = 0.38f)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !isLoading) {
                    showDialog = true
                },
            colors = CardDefaults.outlinedCardColors(
                containerColor = if(isLoading) Grey.copy(alpha = 0.5f)
                else MaterialTheme.colorScheme.tertiary,

            ),
            border = BorderStroke(0.dp, Color.Transparent)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = selectedItem?.let(itemLabel) ?: placeholder,
                    style = textStyle
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = White
                    )
                } else {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        }
    }

    if (showDialog) {
        SearchDialog(
            list = list,
            itemLabel = itemLabel,
            onDismiss = {
                showDialog = false
            },
            onItemSelected = {
                onItemSelected(it)
                showDialog = false
            }
        )
    }
}

@Composable
private fun <T> SearchDialog(
    list: List<T>,
    itemLabel: (T) -> String,
    onDismiss: () -> Unit,
    onItemSelected: (T) -> Unit
) {

    val dialogHeight = LocalWindowInfo.current.containerSize.height.dp * 0.2f
    val focusManager = LocalFocusManager.current
    var search by rememberSaveable {
        mutableStateOf("")
    }

    val filteredList = remember(search, list) {
        if (search.isBlank()) {
            list
        } else {
            list.filter {
                itemLabel(it).contains(search, ignoreCase = true)
            }
        }
    }

    Dialog(
        onDismissRequest = {
            focusManager.clearFocus()
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(dialogHeight)
                .clickable{
                    focusManager.clearFocus()
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Item",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            onDismiss()
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = White
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,

                        unfocusedContainerColor = White,
                        focusedContainerColor = White,

                        cursorColor = Black,
                        focusedTextColor = Black,
                        unfocusedTextColor = Black
                    ),
                    placeholder = {
                        Text(
                            "Search...",
                            color = Black.copy(alpha = 0.7f)
                        )
                    }
                )

                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    items(filteredList,
                        key = { item -> item.hashCode() }) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    focusManager.clearFocus()
                                    onItemSelected(item)
                                }
                                .padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = itemLabel(item),
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}