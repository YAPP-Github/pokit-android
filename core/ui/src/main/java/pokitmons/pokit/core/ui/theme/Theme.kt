package pokitmons.pokit.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import pokitmons.pokit.core.ui.theme.color.LocalPokitColorDarkScheme
import pokitmons.pokit.core.ui.theme.color.LocalPokitColorScheme
import pokitmons.pokit.core.ui.theme.color.PokitColors

object PokitTheme {
    val colors: PokitColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPokitColorScheme.current

    val typography: PokitTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalPokitTypography.current
}

@Composable
fun PokitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: PokitTypography = PokitTheme.typography,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> LocalPokitColorDarkScheme.current
        else -> LocalPokitColorScheme.current
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.backgroundBase.toArgb()
            // dark theme 지원시 true 대신 !darkTheme를 할당하도록 수정 예정
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    CompositionLocalProvider(
        LocalPokitColorScheme provides colorScheme,
        LocalPokitTypography provides typography
    ) {
        content()
    }
}
