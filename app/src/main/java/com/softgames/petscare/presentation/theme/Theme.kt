package com.softgames.petscare.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Red80,
    onPrimary = Red20,
    primaryContainer = Red30,
    onPrimaryContainer = Red90,
    secondary = Orange80,
    onSecondary = Orange20,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange90,
    tertiary = Brown80,
    onTertiary = Brown20,
    tertiaryContainer = Brown30,
    onTertiaryContainer = Brown90,
    background = Black10,
    onBackground = White90,
    surface = Black20,
    onSurface = White90
)

private val LightColorScheme = lightColorScheme(
    primary = Red50,
    onPrimary = Red100,
    primaryContainer = Red90,
    onPrimaryContainer = Red10,
    secondary = Orange40,
    onSecondary = Orange100,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Brown40,
    onTertiary = Brown100,
    tertiaryContainer = Brown90,
    onTertiaryContainer = Brown10,
    background = Orange98,
    onBackground = Black10,
    surface = Orange95,
    onSurface = Black10
)

@Composable
fun PetscareTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    //Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = Orange98,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}