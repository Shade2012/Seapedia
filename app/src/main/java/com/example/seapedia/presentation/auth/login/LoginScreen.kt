package com.example.seapedia.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import clearFocusOnTap
import com.example.seapedia.global.utils.EmailSupportingText
import com.example.seapedia.global.utils.PasswordSupportingText
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.ui.theme.SeapediaTheme
import com.example.seapedia.ui.theme.Black
import com.example.seapedia.ui.theme.Dimens

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    Column (
        modifier
            .fillMaxSize()
            .padding(Dimens.InnerPadding)
            .clearFocusOnTap(LocalFocusManager.current)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)

    ){
        Button(
            onClick = {
                viewModel.testSnackbar()
            }
        ) {
            Text("Testing")
        }
        Text("Login")
        TextFieldCustom(
            enabled = !state.loading,
            title = "Email",
            hint = "Input your email",
            keyboardType = KeyboardType.Email,
            text = state.email,
            supportingText = EmailSupportingText,
            imeAction = ImeAction.Next
        ) {
            viewModel.onEmailChange(it)
        }

        TextFieldCustom(
            enabled = !state.loading,
            title = "Password",
            hint = "Input your password",
            keyboardType = KeyboardType.Password,
            supportingText = PasswordSupportingText,
            visualTransformation = PasswordVisualTransformation(),
            text = state.password,
            imeAction = ImeAction.Done
        ) {
            viewModel.onPasswordChange(it)
        }

        ButtonCustom(
            enabled = !state.passwordError and !state.emailError,
            loading = !state.loading,
            title = "Login",
            onClick = {
                if (!state.loading) {
                    viewModel.login()
                }
            }
        )

        repeat(10,){
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                color = Black
            ) {  }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    SeapediaTheme {
        Scaffold { innerPadding ->
            LoginScreen(modifier.padding(innerPadding))
        }
    }
}