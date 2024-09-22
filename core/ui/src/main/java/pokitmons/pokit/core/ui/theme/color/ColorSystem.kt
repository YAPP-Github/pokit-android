package pokitmons.pokit.core.ui.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class PokitColors(
    val textPrimary: Color = Gray900,
    val textSecondary: Color = Gray700,
    val textTertiary: Color = Gray400,
    val textDisable: Color = Gray500,
    val iconPrimary: Color = Gray800,
    val iconSecondary: Color = Gray400,
    val iconTertiary: Color = Gray200,
    val iconDisable: Color = Gray500,
    val backgroundBase: Color = White,
    val backgroundBaseIcon: Color = Color(0xCCD9D9D9),
    val backgroundPrimary: Color = Gray50,
    val backgroundSecondary: Color = Gray100,
    val backgroundTertiary: Color = Gray700,
    val backgroundDisable: Color = Gray200,
    val borderPrimary: Color = Gray700,
    val borderSecondary: Color = Gray200,
    val borderTertiary: Color = Gray100,
    val borderDisable: Color = Gray200,
    val brand: Color = Brand,
    val brandBold: Color = Orange700,
    val info: Color = Blue700,
    val warning: Color = Yellow400,
    val success: Color = Green400,
    val error: Color = Red500,
    val inverseWh: Color = White,
)

internal fun pokitColorsLight(): PokitColors = PokitColors()
internal fun pokitColorsDark(): PokitColors = PokitColors(error = Red100)

internal val LocalPokitColorScheme = staticCompositionLocalOf { pokitColorsLight() }
internal val LocalPokitColorDarkScheme = staticCompositionLocalOf { pokitColorsDark() }
