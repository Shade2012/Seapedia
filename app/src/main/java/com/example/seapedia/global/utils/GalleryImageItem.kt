package com.example.seapedia.global.utils

import android.net.Uri

sealed interface GalleryImageItem {

    data class Url(
        val id: Int,
        val imageUrl: String,
        val index: Int,
    ) : GalleryImageItem

    data class Local(
        val uri: Uri,
        val index: Int,
    ) : GalleryImageItem

}