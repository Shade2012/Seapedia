package com.example.seapedia.presentation.buyer.address.update

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.domain.entities.Region
import com.example.seapedia.domain.usecases.address.GetDetailAddressUseCase
import com.example.seapedia.domain.usecases.address.UpdateAddressUseCase
import com.example.seapedia.domain.usecases.region.GetAllCityUseCase
import com.example.seapedia.domain.usecases.region.GetAllDistrictUseCase
import com.example.seapedia.domain.usecases.region.GetAllProvinceUseCase
import com.example.seapedia.domain.usecases.region.GetAllVillageUseCase
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BuyerAddressUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllProvinceUseCase: GetAllProvinceUseCase,
    private val getAllCityUseCase: GetAllCityUseCase,
    private val getAllDistrictUseCase: GetAllDistrictUseCase,
    private val getAllVillageUseCase: GetAllVillageUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val getDetailAddressUseCase: GetDetailAddressUseCase,
) : ViewModel(
) {
    private val addressId: Int = checkNotNull(savedStateHandle[BuyerRoute.BuyerUpdateAddress.ADDRESS_ID])
    private val _state = MutableStateFlow(BuyerAddressUpdateState())
    val state = _state.asStateFlow()
    private val _navigateToAddressAll = MutableSharedFlow<Unit>()
    val navigateToAddressAll = _navigateToAddressAll.asSharedFlow()


    init {
        onInit()
    }

    private fun onInit(){
        viewModelScope.launch{
            updateState {
                copy(
                    isLoading = true,
                    provinceLoading = true,
                    cityLoading = true,
                    districtLoading = true,
                    villageLoading = true)
            }
            try {
                val address = getDetailAddressUseCase.run(id = addressId)
                    .filterIsInstance<CommonState.Success<Address>>()
                    .first()
                    .data

                val provinces = getAllProvinceUseCase.run()
                    .filterIsInstance<CommonState.Success<List<Region>>>()
                    .first()
                    .data

                val cities = getAllCityUseCase.run(address.province!!.id)
                    .filterIsInstance<CommonState.Success<List<Region>>>()
                    .first()
                    .data


                val districts = getAllDistrictUseCase.run(address.city!!.id)
                    .filterIsInstance<CommonState.Success<List<Region>>>()
                    .first()
                    .data


                val villages = getAllVillageUseCase.run(address.district!!.id)
                    .filterIsInstance<CommonState.Success<List<Region>>>()
                    .first()
                    .data

                val addressNew = address.addressDetail.split(", ").dropLast(4).joinToString(", ")

                updateState {
                    copy(
                        isLoading = false,
                        name =address.name,
                        addressDetail = addressNew,
                        receiverName = address.receiverName,
                        nameError = false,
                        addressDetailError = false,
                        receiverNameError = false,

                        provinceList = provinces,
                        province = address.province,
                        provinceError = false,

                        cityList = cities,
                        city = address.city,
                        cityError = false,

                        districtList = districts,
                        district = address.district,
                        districtError = false,

                        villageList = villages,
                        village = address.village,
                        villageError = false,

                        provinceLoading = false,
                        cityLoading = false,
                        districtLoading = false,
                        villageLoading = false
                    )
                }
            } catch (e: Exception){
                updateState {
                    copy(
                        isLoading = false,
                        error = e.message ?: "Error",
                        provinceLoading = false,
                        cityLoading = false,
                        districtLoading = false,
                        villageLoading = false
                    )
                }
            }
        }
    }

    fun updateAddress(){
        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }
            delay(1000)
            val value = state.value
            val addressBody = AddressBody(
                village = value.village!!.name,
                name = value.name,
                addressDetail = value.addressDetail,
                receiverName = value.receiverName,
                province = value.province!!.name,
                city = value.city!!.name,
                district = value.district!!.name,
                provinceId = value.province.id.toIntOrNull() ?: 0,
                cityId = value.city.id.toIntOrNull() ?: 0,
                districtId = value.district.id.toIntOrNull() ?: 0,
                villageId = value.village.id.toIntOrNull() ?: 0,
            )
            updateAddressUseCase.run(
                id = addressId,
                body = addressBody,
            ).collect {
                    result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = result.message,
                                type = SnackbarType.ERROR
                            ))
                        )
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                isLoading = true
                            )
                        }
                    }
                    is CommonState.Success -> {
                        updateState {
                            copy(
                                isLoading = false
                            )
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Successfully update address",
                                type = SnackbarType.SUCCESS
                            ))
                        )
                        _navigateToAddressAll.emit(Unit)
                    }
                }
            }
        }
    }
    fun onChangeName(value: String){
        updateState {
            copy(
                name = value,
                nameError= !NormalSupportingText.validate(value)
            )
        }
    }

    fun onChangeAddressDetail(value: String){
        updateState {
            copy(
                addressDetail = value,
                addressDetailError = !NormalSupportingText.validate(value)
            )
        }
    }

    fun onChangeReceiverName(value: String){
        updateState {
            copy(
                receiverName = value,
                receiverNameError = !NormalSupportingText.validate(value)
            )
        }
    }

    fun onChangeProvince(value: Region){
        viewModelScope.launch {
            updateState {
                copy(
                    province = value,
                )
            }
            updateStateLoadingCity()
            val cities = getAllCityUseCase.run(value.id)
                .filterIsInstance<CommonState.Success<List<Region>>>()
                .first()
                .data
            val city = cities.first()

            val districts = getAllDistrictUseCase.run(city.id)
                .filterIsInstance<CommonState.Success<List<Region>>>()
                .first()
                .data
            val district = districts.first()

            val villages = getAllVillageUseCase.run(district.id)
                .filterIsInstance<CommonState.Success<List<Region>>>()
                .first()
                .data
            val village = villages.first()

            updateState {
                copy(
                    province = value,

                    cityLoading = false,
                    cityError = false,
                    city = city,
                    cityList = cities,

                    districtLoading = false,
                    districtError = false,
                    district = district,
                    districtList = districts,

                    villageLoading = false,
                    villageError = false,
                    village = village,
                    villageList = villages,
                )
            }
        }
    }

    fun onChangeCity(value: Region){
        viewModelScope.launch {
            updateState {
                copy(
                    city = value,
                )
            }
            updateStateLoadingDistrict()
            val districts = getAllDistrictUseCase.run(value.id)
                .filterIsInstance<CommonState.Success<List<Region>>>()
                .first()
                .data
            val district = districts.first()

            val villages = getAllVillageUseCase.run(district.id)
                .filterIsInstance<CommonState.Success<List<Region>>>()
                .first()
                .data
            val village = villages.first()

            updateState {
                copy(
                    city = value,

                    districtLoading = false,
                    districtError = false,
                    district = district,
                    districtList = districts,

                    villageLoading = false,
                    villageError = false,
                    village = village,
                    villageList = villages,
                )
            }
        }
    }

    fun onChangeDistrict(value: Region){
        viewModelScope.launch {
            updateState {
                copy(
                    district = value,
                )
            }
            updateStateLoadingVillage()
            val villages = getAllVillageUseCase.run(value.id)
                .filterIsInstance<CommonState.Success<List<Region>>>()
                .first()
                .data
            val village = villages.first()

            updateState {
                copy(
                    district = value,

                    villageLoading = false,
                    villageError = false,
                    village = village,
                    villageList = villages,
                )
            }
        }
    }

    fun onChangeVillage(value: Region){
        updateState {
            copy(
                village = value,
                villageError = false,
            )
        }
    }

    private fun updateStateLoadingCity(){
        updateState {
            copy(
                cityLoading = true,
                cityError = true,

                districtLoading = true,
                districtError = true,

                villageLoading = true,
                villageError = true
            )
        }
    }

    private fun updateStateLoadingDistrict(){
        updateState {
            copy(
                districtLoading = true,
                districtError = true,

                villageLoading = true,
                villageError = true
            )
        }
    }

    private fun updateStateLoadingVillage(){
        updateState {
            copy(
                villageLoading = true,
                villageError = true
            )
        }
    }

    private fun updateState(
        update: BuyerAddressUpdateState.() -> BuyerAddressUpdateState
    ){
        _state.update {
            it.update()
        }
    }
}