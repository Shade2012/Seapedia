package com.example.seapedia.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.seapedia.ui.theme.SeapediaTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SeapediaTheme {
        HomeScreen()
    }
}