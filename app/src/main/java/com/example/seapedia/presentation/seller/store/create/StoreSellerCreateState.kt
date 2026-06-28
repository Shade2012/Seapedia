package com.example.seapedia.presentation.seller.store.create

import android.net.Uri
import com.example.seapedia.domain.entities.Region
import com.example.seapedia.presentation.seller.store.state.StoreFormState

data class StoreSellerCreateState(

    override val name: String = "Toko Shade",
    override val address: String = "Jalan Garuda A9",
    override val phoneNumber: String = "+6282124654352",

    override val provinceList: List<Region> = emptyList(),
    override val cityList: List<Region> = emptyList(),
    override val districtList: List<Region> = emptyList(),
    override val villageList: List<Region> = emptyList(),

    override val image: Uri? = null,
    override val imageUrl: String? = null,

    override val province: Region? = null,
    override val city: Region? = null,
    override val district: Region? = null,
    override val village: Region? = null,

    override val isLoading: Boolean = false,

    override val provinceLoading: Boolean = true,
    override val cityLoading: Boolean = true,
    override val districtLoading: Boolean = true,
    override val villageLoading: Boolean = true,

    override val error: String = "",

    override val nameError: Boolean = true,
    override val addressError: Boolean = true,
    override val phoneNumberError: Boolean = true,
    override val provinceError: Boolean = true,
    override val cityError: Boolean = true,
    override val districtError: Boolean = true,
    override val villageError: Boolean = true

) : StoreFormState