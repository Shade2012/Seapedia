package com.example.base_compose.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.base_compose.ui.theme.Base_ComposeTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Base_ComposeTheme {
        HomeScreen()
    }
}