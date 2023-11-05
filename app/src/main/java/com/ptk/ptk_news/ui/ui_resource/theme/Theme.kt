package com.ptk.ptk_news.ui.ui_resource.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorSchemeOrange = darkColorScheme(
    primary = LightOrange,
    primaryContainer = Orange,
    secondary = LightPink,
    secondaryContainer = Pink,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black
)

val LightColorSchemeOrange = lightColorScheme(
    primary = Orange,
    primaryContainer = DarkOrange,
    secondary = Pink,
    secondaryContainer = Pink,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black

)

val DarkColorSchemeYellow = darkColorScheme(
    primary = LightYellow,
    primaryContainer = Yellow,
    secondary = LightGreen,
    secondaryContainer = Green,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    onBackground = Color.Black
)

val LightColorSchemeYellow = lightColorScheme(
    primary = Yellow,
    primaryContainer = DarkYellow,
    secondary = Green,
    secondaryContainer = DarkGreen,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    onBackground = Color.Black

)

val DarkColorSchemeBlue = darkColorScheme(
    primary = LightBlue,
    primaryContainer = Blue,
    secondary = LightViolet,
    secondaryContainer = Violet,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black
)

val LightColorSchemeBlue = lightColorScheme(
    primary = Blue,
    primaryContainer = DarkBlue,
    secondary = Violet,
    secondaryContainer = DarkViolet,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black

)

val DarkColorSchemePurple = darkColorScheme(
    primary = LightPurple,
    primaryContainer = Purple,
    secondary = LightRed,
    secondaryContainer = Red,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black
)

val LightColorSchemePurple = lightColorScheme(
    primary = Purple,
    primaryContainer = DarkPurple,
    secondary = Red,
    secondaryContainer = DarkRed,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black

)

@Composable
fun MyTheme(
    lightColorScheme: ColorScheme,
    darkColorScheme: ColorScheme,
    typography: androidx.compose.material3.Typography,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primaryContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                darkTheme
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}