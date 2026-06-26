package com.example.seapedia.presentation.seller.store.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.StoreBody
import com.example.seapedia.domain.entities.RegionEntity
import com.example.seapedia.domain.usecases.region.GetAllCityUseCase
import com.example.seapedia.domain.usecases.region.GetAllDistrictUseCase
import com.example.seapedia.domain.usecases.region.GetAllProvinceUseCase
import com.example.seapedia.domain.usecases.region.GetAllVillageUseCase
import com.example.seapedia.domain.usecases.store.CreateStoreUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.PhoneNumberSupportingText
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
class StoreSellerCreateViewModel @Inject constructor(
    private val getAllProvinceUseCase: GetAllProvinceUseCase,
    private val getAllCityUseCase: GetAllCityUseCase,
    private val getAllDistrictUseCase: GetAllDistrictUseCase,
    private val getAllVillageUseCase: GetAllVillageUseCase,
    private val createStoreUseCase: CreateStoreUseCase
) : ViewModel(
) {
    private val _state = MutableStateFlow(StoreSellerCreateState())
    val state = _state.asStateFlow()
    private val _navigateToStoreAll = MutableSharedFlow<Unit>()
    val navigateToStoreAll = _navigateToStoreAll.asSharedFlow()


    init {
        onInit()
    }

    private fun onInit(){
        viewModelScope.launch{
            updateState {
                copy(
                    provinceLoading = true,
                    cityLoading = true,
                    districtLoading = true,
                    villageLoading = true)
            }
            try {
                val provinces = getAllProvinceUseCase.run()
                    .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
                    .first()
                    .data
                val province = provinces.first()

                val cities = getAllCityUseCase.run(province.id)
                    .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
                    .first()
                    .data
                val city = cities.first()

                val districts = getAllDistrictUseCase.run(city.id)
                    .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
                    .first()
                    .data
                val district = districts.first()

                val villages = getAllVillageUseCase.run(district.id)
                    .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
                    .first()
                    .data

                updateState {
                    copy(
                        provinceList = provinces,
                        province = province,
                        provinceError = false,

                        cityList = cities,
                        city = city,
                        cityError = false,

                        districtList = districts,
                        district = district,
                        districtError = false,

                        villageList = villages,
                        village = villages.first(),
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
    fun createStore(){
        viewModelScope.launch {
            updateState {
                copy(loading = true)
            }
            delay(1000)
            val value = state.value
            val image = value.image
            val storeBody = StoreBody(
                village = value.village!!.name,
                name = value.name,
                address = value.address,
                phoneNumber = value.phoneNumber,
                province = value.province!!.name,
                city = value.city!!.name,
                district = value.district!!.name,
                provinceId = value.province.id,
                cityId = value.city.id,
                districtId = value.district.id,
                villageId = value.village.id,
            )
            createStoreUseCase.run(
                storeBody = storeBody,
                image = image!!
            ).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(
                                error = result.message,
                                loading = false
                            )
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Failed to create store",
                                type = SnackbarType.ERROR
                            ))
                        )
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                loading = true
                            )
                        }
                    }
                    is CommonState.Success -> {
                        updateState {
                            copy(
                                loading = false
                            )
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Successfully create store",
                                type = SnackbarType.SUCCESS
                            ))
                        )
                        _navigateToStoreAll.emit(Unit)
                    }
                }
            }
        }
    }
    fun onChangeName(value: String){
        updateState {
            copy(
                name = value,
                nameError=!NormalSupportingText.validate(value)
            )
        }
    }

    fun onChangeAddress(value: String){
        updateState {
            copy(
                address = value,
                addressError = !NormalSupportingText.validate(value)
            )
        }
    }

    fun onChangePhoneNumber(value: String){
        updateState {
            copy(
                phoneNumber = value,
                phoneNumberError = !PhoneNumberSupportingText.validate(value)
            )
        }
    }

    fun onChangeProvince(value: RegionEntity){
        viewModelScope.launch {
            updateState {
                copy(
                    province = value,
                )
            }
            updateStateLoadingCity()
            val cities = getAllCityUseCase.run(value.id)
                .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
                .first()
                .data
            val city = cities.first()

            val districts = getAllDistrictUseCase.run(city.id)
                .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
                .first()
                .data
            val district = districts.first()

            val villages = getAllVillageUseCase.run(district.id)
                .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
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

    fun onChangeCity(value: RegionEntity){
        viewModelScope.launch {
            updateState {
                copy(
                    city = value,
                )
            }
            updateStateLoadingDistrict()
            val districts = getAllDistrictUseCase.run(value.id)
                .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
                .first()
                .data
            val district = districts.first()

            val villages = getAllVillageUseCase.run(district.id)
                .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
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

    fun onChangeDistrict(value: RegionEntity){
        viewModelScope.launch {
            updateState {
                copy(
                    district = value,
                )
            }
            updateStateLoadingVillage()
            val villages = getAllVillageUseCase.run(value.id)
                .filterIsInstance<CommonState.Success<List<RegionEntity>>>()
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

    fun onChangeVillage(value: RegionEntity){
        updateState {
            copy(
                village = value,
                villageError = false,
            )
        }
    }

    fun onImageSelected(uri : Uri?){
        updateState {
            copy(image = uri)
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
        update: StoreSellerCreateState.() -> StoreSellerCreateState
    ){
        _state.update {
            it.update()
        }
    }
}