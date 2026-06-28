package com.example.seapedia.presentation.seller.product.widgets

import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.seapedia.R
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.GalleryImageItem
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.NumberSupportingText
import com.example.seapedia.global.utils.RupiahSupportingText
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.presentation.seller.product.state.ProductFormState


@Composable
fun ProductForm(
    state: ProductFormState,
    images: List<Uri>? = null,

    onImagesSelected: (List<Uri>) -> Unit,
    onDeleteImage : (Int) -> Unit,
    onChangeName: (String) -> Unit,
    onChangePrice: (String) -> Unit,
    onChangeStock: (String) -> Unit,

    onAddType: () -> Unit,
    onDeleteType: (Int) -> Unit,
    onChangeTypeName: (Int, String) -> Unit,
    onChangeTypeRequired: (Int, Boolean) -> Unit,
    onChangeTypeMultiple: (Int, Boolean) -> Unit,
    onAddItem: (Int) -> Unit,
    onDeleteItem: (Int, Int) -> Unit,
    onChangeItemName: (Int, Int, String) -> Unit,
    onChangeItemPrice: (Int, Int, String) -> Unit,
    onChangeItemStock: (Int, Int, String) -> Unit
    ) {
    if(images != null)
        MultipleImageCustom(
            Modifier,
            images = images.mapIndexed {index, uri ->
                GalleryImageItem.Local(uri,index)
            },
            maxItems = 10,
            onImagesSelected = onImagesSelected,
            onDeleteImage = {
                imageItem ->
                when(imageItem){
                    is GalleryImageItem.Local ->{
                        onDeleteImage(imageItem.index)
                    }
                    is GalleryImageItem.Url -> {
                    }
                }
            }
        )

    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Name",
        hint = "Input your product name",
        keyboardType = KeyboardType.Text,
        text = state.name,
        supportingText = NormalSupportingText,
        imeAction = ImeAction.Next,
        leadingIcon = {
            IconCustom(id = 0, icon = ImageVector.vectorResource(R.drawable.product_icon), contentDescription = "Product Name Icon")
        },
    ) {
        onChangeName(it)
    }

    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Price",
        hint = "Input your price",
        keyboardType = KeyboardType.Number,
        text = Formatting().formatRupiah(state.price),
        supportingText = RupiahSupportingText,
        imeAction = ImeAction.Next,
    ) {
        value ->
        onChangePrice(value.filter { it.isDigit() })
    }

    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Stock",
        hint = "Input your stock",
        keyboardType = KeyboardType.Number,
        minLines = 1,
        text = state.stock,
        supportingText = NumberSupportingText,
        imeAction = ImeAction.Done,
    ) {
        value ->
        onChangeStock(value.filter { it.isDigit() })
    }

    ProductTypeSection(
        types = state.types,
        onDeleteType = onDeleteType,
        onChangeTypeName = onChangeTypeName,
        onChangeTypeRequired = onChangeTypeRequired,
        onChangeTypeMultiple = onChangeTypeMultiple,
        onAddItem = onAddItem,
        onDeleteItem = onDeleteItem,
        onChangeItemName = onChangeItemName,
        onChangeItemPrice = onChangeItemPrice,
        onChangeItemStock = onChangeItemStock,
    )
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onAddType
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text("Add Type", style = MaterialTheme.typography.bodyMedium)
    }
}