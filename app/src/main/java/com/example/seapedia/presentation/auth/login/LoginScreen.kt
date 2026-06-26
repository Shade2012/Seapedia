package com.example.seapedia.presentation.auth.login

import androidx.compose.foundation.clickable
import com.example.seapedia.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import clearFocusOnTap
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.auth.AuthRoute
import com.example.seapedia.global.utils.ALL_LOGIN_ROLES
import com.example.seapedia.global.utils.EmailSupportingText
import com.example.seapedia.global.utils.PasswordSupportingText
import com.example.seapedia.presentation.auth.login.widgets.DropdownCustom
import com.example.seapedia.presentation.auth.widgets.TopIconAppAuth
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.PasswordIcon
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.navigateToSplash.collect {
            navController.navigate(NavGraph.AUTH){
                popUpTo(0)
            }
        }
    }
    Column (
        modifier
            .fillMaxSize()
            .padding(Dimens.InnerPadding)
            .padding(top = Dimens.TopPadding)
            .clearFocusOnTap(LocalFocusManager.current)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)

    ){
        TopIconAppAuth()
        TextFieldCustom(
            enabled = !state.loading,
            title = "Email",
            hint = "Input your email",
            keyboardType = KeyboardType.Email,
            text = state.email,
            supportingText = EmailSupportingText,
            imeAction = ImeAction.Next,
            leadingIcon = {
                IconCustom(id = 0, icon = Icons.Default.MailOutline, contentDescription = "Email")
            },
        ) {
            viewModel.onEmailChange(it)
        }

        TextFieldCustom(
            enabled = !state.loading,
            title = "Password",
            hint = "Input your password",
            keyboardType = KeyboardType.Password,
            supportingText = PasswordSupportingText,
            visualTransformation = if(state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation() ,
            text = state.password,
            imeAction = ImeAction.Done,
            leadingIcon = {
                IconCustom(id = R.drawable.lock, contentDescription = "Lock")
            },
            trailingIcon = {
                PasswordIcon(modifier,
                    isVisible = state.isPasswordVisible,
                    onClick = {
                    viewModel.onPasswordVisible()
                })
            }
        ) {
            viewModel.onPasswordChange(it)
        }

        DropdownCustom(
            modifier,
            ALL_LOGIN_ROLES,
            selectedItem = state.selectedRole,
            onItemSelected = {
                viewModel.onRoleChange(it)
            },
            itemLabel = {
                role -> role.name.lowercase().replaceFirstChar{it.uppercase()}
            },
            label = "Role")

        Text(
            modifier = Modifier.clickable(
                onClick = {
                    viewModel.onContinueAsGuest()
                }
            ),
            text = stringResource(R.string.continue_as_guest_login),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ))
        Spacer(Modifier.height(5.dp))
        ButtonCustom(
            enabled = !state.passwordError and !state.emailError && state.selectedRole != null,
            isNotLoading = !state.loading,
            title = "Login",
            onClick = {
                viewModel.login()
            }
        )
        Spacer(Modifier.height(5.dp))
        Text(
            modifier = Modifier.clickable(
                onClick = {
                    navController.navigate(AuthRoute.Register.route)
                }
            ),
            text = stringResource(R.string.dont_have_an_account_register_now),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ))
    }
}