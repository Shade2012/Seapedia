package com.example.base_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.base_compose.global.navigation.NavGraph
import com.example.base_compose.global.utils.ui.AppEventBus
import com.example.base_compose.global.utils.ui.CustomSnackbarVisuals
import com.example.base_compose.global.utils.ui.SnackbarType
import com.example.base_compose.global.utils.ui.UiEvent
import com.example.base_compose.presentation.common.SnackBarCustom
import com.example.base_compose.ui.theme.Base_ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Base_ComposeTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot(modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LaunchedEffect(Unit) {
            AppEventBus.events.collect { event ->
                when(event){
                    is UiEvent.ShowSnackbar ->{
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(event.data)
                        }
                    }
                }
            }
        }
        NavGraph()
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter)
                .padding(bottom = 40.dp),
            snackbar = {
                data -> SnackBarCustom(data)
            }
        )
    }
}
