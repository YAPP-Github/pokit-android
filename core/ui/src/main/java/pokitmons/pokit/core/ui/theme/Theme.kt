package pokitmons.pokit.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
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

    CompositionLocalProvider(
        LocalPokitColorScheme provides colorScheme,
        LocalPokitTypography provides typography
    ) {
        content()
    }
}
