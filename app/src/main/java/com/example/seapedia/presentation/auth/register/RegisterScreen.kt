package com.example.seapedia.presentation.auth.register

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Person
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
import com.example.seapedia.R
import com.example.seapedia.global.navigation.auth.AuthRoute
import com.example.seapedia.global.utils.ALL_USER_ROLES
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
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect {
            navController.navigate(AuthRoute.Login.route)
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
            enabled = !state.isLoading,
            title = "Full name",
            hint = "Input your full name",
            keyboardType = KeyboardType.Text,
            text = state.fullName,
            imeAction = ImeAction.Next,
            leadingIcon = {
                IconCustom(id = 0, icon = Icons.Default.Person, contentDescription = "Fullname")
            },
        ) {
            viewModel.onFullNameChange(it)
        }

        TextFieldCustom(
            enabled = !state.isLoading,
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
            enabled = !state.isLoading,
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
            ALL_USER_ROLES,
            selectedItem = state.selectedRole,
            onItemSelected = {
                viewModel.onRoleChange(it)
            },
            itemLabel = {
                    role -> role.name.lowercase().replaceFirstChar{it.uppercase()}
            },
            label = "Role")
        Text(text = "*${stringResource(R.string.information_role_register)}", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(5.dp))
        ButtonCustom(
            enabled = !state.passwordError and !state.emailError && state.selectedRole != null,
            isNotLoading = !state.isLoading,
            title = "Register",
            onClick = {
                viewModel.register()
            }
        )
        Spacer(Modifier.height(5.dp))
        Text(
            modifier = Modifier.clickable(
                onClick = {
                    navController.navigate(AuthRoute.Login.route)
                }
            ),
            text = stringResource(R.string.have_an_account_login_now),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ))
    }
}