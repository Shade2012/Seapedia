package com.example.seapedia.presentation.buyer.address.state
import android.net.Uri
import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.domain.entities.Region

interface AddressFormState {
    val name: String
    val receiverName: String
    val addressDetail: String
    val provinceList: List<Region>
    val cityList: List<Region>
    val districtList: List<Region>
    val villageList: List<Region>

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
    val receiverNameError: Boolean
    val addressDetailError: Boolean
    val provinceError: Boolean
    val cityError: Boolean
    val districtError: Boolean
    val villageError: Boolean
}