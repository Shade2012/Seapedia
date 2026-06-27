package com.example.seapedia.presentation.seller.product.widgets

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.seapedia.R
import com.example.seapedia.global.utils.GalleryImageItem
import com.example.seapedia.presentation.common.ImageCustom

@Composable
fun MultipleImageCustom(
    modifier : Modifier = Modifier,
    images: List<GalleryImageItem> = listOf<GalleryImageItem>(),
    maxItems: Int = 10,
    enableDelete: Boolean = true,
    enableLaunchImage: Boolean = true,
    onImagesSelected: (List<Uri>) -> Unit = {},
    onDeleteImage : (GalleryImageItem) -> Unit = {}
) {
    val first = images.firstOrNull()
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = maxItems)
    ) {
        uris ->
        if (uris.isNotEmpty()) {
            onImagesSelected(uris)
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MainImage(
            image = (first as? GalleryImageItem.Local)?.uri,
            imageUrl = (first as? GalleryImageItem.Url)?.imageUrl,
            onClick = {
                if (images.isEmpty()) {
                    if(enableLaunchImage){
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                } else {
                    selectedIndex = 0
                    showDialog = true
                }
            }
        )
        val thumbnails = images.drop(1)
        thumbnails
            .chunked(2)
            .take(1)
            .forEachIndexed {rowIndex, rowImages ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowImages.forEachIndexed { index, image ->
                        val actualIndex = 1 + rowIndex * 2 + index
                        val image = rowImages[index]
                        ThumbnailImage(
                            modifier = Modifier.weight(1f),
                            image = (image as? GalleryImageItem.Local)?.uri,
                            imageUrl = (image as? GalleryImageItem.Url)?.imageUrl,
                            onClick = {
                                selectedIndex = actualIndex
                                showDialog = true
                            }
                        )
                    }
                    if (rowImages.size == 1) {
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

        val remaining = thumbnails.drop(2)
        if (remaining.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                RemainingImage(
                    remaining = remaining.size,
                    onClick = {
                        selectedIndex = 3
                        showDialog = true

                    }
                )
            }
        }
    }

    if (showDialog) {
        ImageGalleryDialog(
            images = images,
            selectedIndex = selectedIndex,
            enable = enableDelete,
            onDismiss = {
                showDialog = false
            },
            onDeleteImage = onDeleteImage
        )
    }
}

@Composable
fun MainImage(
    modifier: Modifier = Modifier,
    image: Uri? = null,
    imageUrl: String? = null,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        when {
            image != null -> {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            imageUrl?.isNotBlank() == true -> {
                ImageCustom(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = imageUrl,
                    contentDescription = "Image Main Product"
                )
            }

            else -> {
                Image(
                    painter = painterResource(R.drawable.default_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun ThumbnailImage(
    modifier: Modifier = Modifier,
    image: Uri? = null,
    imageUrl: String? = null,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {

        when {

            image != null -> {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            imageUrl?.isNotBlank() == true -> {
                ImageCustom(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = imageUrl,
                    contentDescription = "null"
                )
            }

            else -> {
                Image(
                    painter = painterResource(R.drawable.default_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun RemainingImage(
    remaining: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+$remaining",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun ImageGalleryDialog(
    images: List<GalleryImageItem>,
    selectedIndex: Int,
    enable: Boolean,
    onDismiss: () -> Unit,
    onDeleteImage: ((GalleryImageItem) -> Unit)? = null
) {

    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex) {
        listState.scrollToItem(selectedIndex)
    }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Box {
                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    if (images.isNotEmpty()) {
                        itemsIndexed(images) { _, image ->
                            GalleryImage(
                                image = (image as? GalleryImageItem.Local)?.uri,
                                imageUrl = (image as? GalleryImageItem.Url)?.imageUrl,
                                onDelete = {
                                    onDeleteImage?.invoke(image)
                                },
                                enable = enable
                            )

                        }

                    }
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp),
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun GalleryImage(
    image: Uri? = null,
    imageUrl: String? = null,
    onDelete: (() -> Unit)? = null,
    enable: Boolean = true,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        when {
            image != null -> {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }
            imageUrl?.isNotBlank() == true -> {
                ImageCustom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    imageUrl = imageUrl,
                    contentDescription = "null"
                )
            }

        }
        if(onDelete != null && enable){
            FilledIconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    }

}