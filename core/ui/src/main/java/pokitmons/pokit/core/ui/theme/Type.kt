package pokitmons.pokit.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import pokitmons.pokit.core.ui.theme.typography.PokitTypo

@Immutable
data class PokitTypography(
    private val _title1: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 24,
        fontWeight = FontWeight.Bold,
        lineHeight = 32,
        letterSpacing = (-0.26f)
    ),
    private val _title2: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 20,
        fontWeight = FontWeight.Bold,
        lineHeight = 28,
        letterSpacing = (-0.6f)
    ),
    private val _title3: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 18,
        fontWeight = FontWeight.Medium,
        lineHeight = 24
    ),
    private val _body1Bold: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 18,
        fontWeight = FontWeight.Bold,
        lineHeight = 24,
        letterSpacing = (-0.54f)
    ),
    private val _body1Medium: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 18,
        fontWeight = FontWeight.Medium,
        lineHeight = 24,
        letterSpacing = (-0.54f)
    ),
    private val _body2Bold: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 16,
        fontWeight = FontWeight.Bold,
        lineHeight = 20,
        letterSpacing = (-0.176f)
    ),
    private val _body2Medium: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 16,
        fontWeight = FontWeight.Medium,
        lineHeight = 20,
        letterSpacing = (-0.176f)
    ),
    private val _body3Medium: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 14,
        fontWeight = FontWeight.Medium,
        lineHeight = 18,
        letterSpacing = (-0.154f)
    ),
    private val _body3Regular: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 14,
        fontWeight = FontWeight.W400,
        lineHeight = 24,
        letterSpacing = (-0.42f)
    ),
    private val _detail1: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 14,
        fontWeight = FontWeight.Medium,
        lineHeight = 20,
        letterSpacing = (-0.154f)
    ),
    private val _detail2: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 12,
        fontWeight = FontWeight.W400,
        lineHeight = 16,
        letterSpacing = (-0.132f)
    ),
    private val _label1SemiBold: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 18,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24,
        letterSpacing = (-0.2f)
    ),
    private val _label1Regular: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 18,
        fontWeight = FontWeight.W400,
        lineHeight = 24,
        letterSpacing = (-0.2f)
    ),
    private val _label2SemiBold: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 16,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20,
        letterSpacing = (-0.18f)
    ),
    private val _label2Regular: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 16,
        fontWeight = FontWeight.W400,
        lineHeight = 20,
        letterSpacing = (-0.18f)
    ),
    private val _label3SemiBold: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 14,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16,
        letterSpacing = (-0.154f)
    ),
    private val _label3Regular: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 14,
        fontWeight = FontWeight.W400,
        lineHeight = 16,
        letterSpacing = (-0.154f)
    ),
    private val _label4: PokitTypo = PokitTypo(
        fontFamily = pretendard,
        fontSize = 10,
        fontWeight = FontWeight.W400,
        lineHeight = 12,
        letterSpacing = (-0.11f)
    ),
) {
    val title1: TextStyle @Composable get() = _title1.toDpTextStyle
    val title2: TextStyle @Composable get() = _title2.toDpTextStyle
    val title3: TextStyle @Composable get() = _title3.toDpTextStyle
    val body1Bold: TextStyle @Composable get() = _body1Bold.toDpTextStyle
    val body1Medium: TextStyle @Composable get() = _body1Medium.toDpTextStyle
    val body2Bold: TextStyle @Composable get() = _body2Bold.toDpTextStyle
    val body2Medium: TextStyle @Composable get() = _body2Medium.toDpTextStyle
    val body3Medium: TextStyle @Composable get() = _body3Medium.toDpTextStyle
    val body3Regular: TextStyle @Composable get() = _body3Regular.toDpTextStyle
    val detail1: TextStyle @Composable get() = _detail1.toDpTextStyle
    val detail2: TextStyle @Composable get() = _detail2.toDpTextStyle
    val label1SemiBold: TextStyle @Composable get() = _label1SemiBold.toDpTextStyle
    val label1Regular: TextStyle @Composable get() = _label1Regular.toDpTextStyle
    val label2SemiBold: TextStyle @Composable get() = _label2SemiBold.toDpTextStyle
    val label2Regular: TextStyle @Composable get() = _label2Regular.toDpTextStyle
    val label3SemiBold: TextStyle @Composable get() = _label3SemiBold.toDpTextStyle
    val label3Regular: TextStyle @Composable get() = _label3Regular.toDpTextStyle
    val label4: TextStyle @Composable get() = _label4.toDpTextStyle
}

internal val LocalPokitTypography = staticCompositionLocalOf { PokitTypography() }
