package com.example.seapedia.presentation.buyer.address.update

import com.example.seapedia.domain.entities.Region
import com.example.seapedia.presentation.buyer.address.state.AddressFormState


data class BuyerAddressUpdateState(
    override val name: String = "",
    override val receiverName: String = "",
    override val addressDetail: String = "",

    override val provinceList: List<Region> = emptyList(),
    override val cityList: List<Region> = emptyList(),
    override val districtList: List<Region> = emptyList(),
    override val villageList: List<Region> = emptyList(),

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
    override val addressDetailError: Boolean = true,
    override val receiverNameError: Boolean = true,
    override val provinceError: Boolean = true,
    override val cityError: Boolean = true,
    override val districtError: Boolean = true,
    override val villageError: Boolean = true

) : AddressFormState