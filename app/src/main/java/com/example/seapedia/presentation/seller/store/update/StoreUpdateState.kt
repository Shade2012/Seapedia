package com.example.seapedia.presentation.seller.store.update

import android.net.Uri
import com.example.seapedia.domain.entities.RegionEntity
import com.example.seapedia.presentation.seller.store.state.StoreFormState

data class StoreSellerUpdateState(

    override val name: String = "",
    override val address: String = "",
    override val phoneNumber: String = "",

    override val provinceList: List<RegionEntity> = emptyList(),
    override val cityList: List<RegionEntity> = emptyList(),
    override val districtList: List<RegionEntity> = emptyList(),
    override val villageList: List<RegionEntity> = emptyList(),

    override val image: Uri? = null,
    override val imageUrl: String? = null,

    override val province: RegionEntity? = null,
    override val city: RegionEntity? = null,
    override val district: RegionEntity? = null,
    override val village: RegionEntity? = null,

    override val loading: Boolean = false,

    override val provinceLoading: Boolean = true,
    override val cityLoading: Boolean = true,
    override val districtLoading: Boolean = true,
    override val villageLoading: Boolean =true,

    override val error: String = "",

    override val nameError: Boolean = true,
    override val addressError: Boolean = true,
    override val phoneNumberError: Boolean = true,
    override val provinceError: Boolean = true,
    override val cityError: Boolean = true,
    override val districtError: Boolean = true,
    override val villageError: Boolean = true

) : StoreFormState