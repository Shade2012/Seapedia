package com.example.seapedia.presentation.seller.store.widgets

import androidx.compose.runtime.Composable
import com.example.seapedia.domain.entities.Region
import com.example.seapedia.presentation.common.SearchableDropdown
import com.example.seapedia.presentation.seller.store.state.StoreFormState

@Composable
fun RegionDropdown(
    state: StoreFormState,
    onProvinceSelected: (Region) -> Unit,
    onCitySelected: (Region) -> Unit,
    onDistrictSelected: (Region) -> Unit,
    onVillageSelected: (Region) -> Unit
) {
    SearchableDropdown(
        label = "Province",
        list = state.provinceList,
        selectedItem = state.province,
        isLoading = state.provinceLoading or state.isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onProvinceSelected(it)
        },
    )

    SearchableDropdown(
        label = "City",
        list = state.cityList,
        selectedItem = state.city,
        isLoading = state.cityLoading or state.isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onCitySelected(it)
        },
    )

    SearchableDropdown(
        label = "District",
        list = state.districtList,
        selectedItem = state.district,
        isLoading = state.districtLoading or state.isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onDistrictSelected(it)
        }
    )

    SearchableDropdown(
        label = "Village",
        list = state.villageList,
        selectedItem = state.village,
        isLoading = state.villageLoading or state.isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onVillageSelected(it)
        }
    )
}