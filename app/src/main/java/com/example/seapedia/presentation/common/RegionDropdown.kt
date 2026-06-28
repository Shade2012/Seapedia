package com.example.seapedia.presentation.common

import androidx.compose.runtime.Composable
import com.example.seapedia.domain.entities.Region

@Composable
fun RegionDropdown(
    province: Region?,
    provinceLoading: Boolean,

    city: Region?,
    cityLoading: Boolean,

    district: Region?,
    districtLoading: Boolean,

    village: Region?,
    villageLoading: Boolean,

    isLoading: Boolean,

    provinceList:List<Region>,
    cityList:List<Region>,
    districtList:List<Region>,
    villageList:List<Region>,

    onProvinceSelected: (Region) -> Unit,
    onCitySelected: (Region) -> Unit,
    onDistrictSelected: (Region) -> Unit,
    onVillageSelected: (Region) -> Unit
) {
    SearchableDropdown(
        label = "Province",
        list = provinceList,
        selectedItem = province,
        isLoading = provinceLoading or isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onProvinceSelected(it)
        },
    )

    SearchableDropdown(
        label = "City",
        list = cityList,
        selectedItem = city,
        isLoading = cityLoading or isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onCitySelected(it)
        },
    )

    SearchableDropdown(
        label = "District",
        list = districtList,
        selectedItem = district,
        isLoading = districtLoading or isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onDistrictSelected(it)
        }
    )

    SearchableDropdown(
        label = "Village",
        list = villageList,
        selectedItem = village,
        isLoading = villageLoading or isLoading,
        itemLabel = { it.name },
        onItemSelected = {
            onVillageSelected(it)
        }
    )
}