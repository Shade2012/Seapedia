package com.example.seapedia.presentation.seller.store.state

import android.net.Uri
import com.example.seapedia.domain.entities.RegionEntity

interface StoreFormState {
    val name: String
    val address: String
    val phoneNumber: String

    val provinceList: List<RegionEntity>
    val cityList: List<RegionEntity>
    val districtList: List<RegionEntity>
    val villageList: List<RegionEntity>

    val image: Uri?
    val imageUrl: String?

    val province: RegionEntity?
    val city: RegionEntity?
    val district: RegionEntity?
    val village: RegionEntity?

    val loading: Boolean

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