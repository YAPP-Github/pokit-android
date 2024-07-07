package pokitmons.pokit.core.ui.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PokitTypo(
    val fontWeight: FontWeight,
    val fontFamily: FontFamily,
    val fontSize: Int,
    val lineHeight: Int,
    val letterSpacing: Float = 0f,
) {
    val toDpTextStyle: TextStyle
        @Composable get() =
            TextStyle(
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                fontSize = fontSize.textDp,
                letterSpacing = letterSpacing.textDp,
                lineHeight = lineHeight.textDp
            )

    val toSpTextStyle: TextStyle
        @Composable get() =
            TextStyle(
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                fontSize = fontSize.sp,
                letterSpacing = letterSpacing.sp,
                lineHeight = lineHeight.sp
            )
}

private fun Int.textDp(density: Density): TextUnit =
    with(density) {
        this@textDp.dp.toSp()
    }

private val Int.textDp: TextUnit
    @Composable get() = this.textDp(density = LocalDensity.current)

private fun Float.textDp(density: Density): TextUnit =
    with(density) {
        this@textDp.dp.toSp()
    }

private val Float.textDp: TextUnit
    @Composable get() = this.textDp(density = LocalDensity.current)
