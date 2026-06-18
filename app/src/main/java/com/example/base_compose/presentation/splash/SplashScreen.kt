package com.example.base_compose.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.base_compose.ui.theme.Base_ComposeTheme

@Composable
fun SplashScreen(
        modifier: Modifier = Modifier
    ) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Splash")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Base_ComposeTheme {
        SplashScreen()
    }
}