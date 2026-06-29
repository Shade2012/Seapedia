package com.example.seapedia.presentation.add_role

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.R
import com.example.seapedia.global.utils.ALL_USER_ROLES
import com.example.seapedia.presentation.auth.login.widgets.DropdownCustom
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.presentation.common.RefreshCommon
import com.example.seapedia.ui.theme.Dimens

@Composable
fun AddRoleScreen(
    rootNavController: NavController,
    viewModel: AddRoleViewModel = hiltViewModel<AddRoleViewModel>()
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.isSuccess.collect {
            rootNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_profile",true)
            rootNavController.popBackStack()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
            .padding(Dimens.InnerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageCustom(
            contentDescription = "<a href=\"https://storyset.com/app\">App illustrations by Storyset</a>",
            vector = ImageVector.vectorResource(R.drawable.topup)
        )
        Spacer(Modifier.height(Dimens.SpacePadding))
        DropdownCustom(
            list = ALL_USER_ROLES,
            selectedItem = state.role,
            onItemSelected = viewModel::onSelectedRole,
            itemLabel = {
                    role -> role.name.lowercase().replaceFirstChar{it.uppercase()}
            },
            label = "Role")
        Spacer(Modifier.height(Dimens.SpacePadding))
        ButtonCustom(
            enabled = !state.isLoading,
            isNotLoading = !state.isLoading,
            title = "Add Role",
            onClick =  viewModel::addRole
        )
    }
}

