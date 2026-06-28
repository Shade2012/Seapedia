package com.example.seapedia.ui.theme

import com.example.seapedia.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val monserratFontFamily = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_extra_bold, FontWeight.ExtraBold),
    Font(R.font.montserrat_medium, weight = FontWeight.Medium)
)

val AppTypography = Typography(

    displayLarge = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 42.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 42.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        ),

    labelMedium = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = monserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
)

