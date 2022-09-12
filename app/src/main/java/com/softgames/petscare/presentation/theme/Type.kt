package com.softgames.petscare.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.softgames.petscare.R

val Poppins = FontFamily(
    Font(resId = R.font.poppins_thin, weight = FontWeight.Thin),
    Font(resId = R.font.poppins_extra_light, weight = FontWeight.ExtraLight),
    Font(resId = R.font.poppins_light, weight = FontWeight.Light),
    Font(resId = R.font.poppins_regular, weight = FontWeight.Normal),
    Font(resId = R.font.poppins_medium, weight = FontWeight.Medium),
    Font(resId = R.font.poppins_semi_bold, weight = FontWeight.SemiBold),
    Font(resId = R.font.poppins_bold, weight = FontWeight.Bold),
    Font(resId = R.font.poppins_extra_bold, weight = FontWeight.ExtraBold),
    Font(resId = R.font.poppins_black, weight = FontWeight.Black),
)

val SamsungSharp = FontFamily(
    Font(resId = R.font.samsung_regular, weight = FontWeight.Normal),
    Font(resId = R.font.samsung_medium, weight = FontWeight.Medium),
    Font(resId = R.font.samsung_bold, weight = FontWeight.Bold),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 64.sp,
        fontSize = 57.sp,
        fontWeight = FontWeight.W400,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 52.sp,
        fontSize = 45.sp,
        fontWeight = FontWeight.W400,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 44.sp,
        fontSize = 36.sp,
        fontWeight = FontWeight.W400,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 40.sp,
        fontSize = 32.sp,
        fontWeight = FontWeight.W400,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 38.sp,
        fontSize = 30.sp,
        fontWeight = FontWeight.W400,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 32.sp,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 28.sp,
        fontSize = 22.sp,
        fontWeight = FontWeight.W400,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.1.sp
    ),
    //buttons
    labelLarge = TextStyle(
        fontFamily = Poppins,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Poppins,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.5.sp
    ),
    //Text
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
    ),
    bodySmall = TextStyle(
        fontFamily = Poppins,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
    )
)

