package com.example.seapedia.presentation.seller.store.state

import android.net.Uri
import com.example.seapedia.domain.entities.Region

interface StoreFormState {
    val name: String
    val address: String
    val phoneNumber: String

    val provinceList: List<Region>
    val cityList: List<Region>
    val districtList: List<Region>
    val villageList: List<Region>

    val image: Uri?
    val imageUrl: String?

    val province: Region?
    val city: Region?
    val district: Region?
    val village: Region?

    val isLoading: Boolean

    val provinceLoading: Boolean
    val cityLoading: Boolean
    val districtLoading: Boolean
    val villageLoading: Boolean

    val error: String

    val nameError: Boolean
    val addressError: Boolean
    val phoneNumberError: Boolean
    val provinceError: Boolean
    val cityError: Boolean
    val districtError: Boolean
    val villageError: Boolean
}