package pokitmons.pokit.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class PokitTypography(
    val title1: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp,
        letterSpacing = (-1.1).sp
    ),
    val title2: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp,
        letterSpacing = (-3).sp
    ),
    val title3: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
        letterSpacing = (-1.1).sp
    ),
    val body1Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp,
        letterSpacing = (-3).sp
    ),
    val body1Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
        letterSpacing = (-1.1).sp
    ),
    val body2Bold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp,
        letterSpacing = (-1.1).sp
    ),
    val body2Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = (-1.1).sp
    ),
    val body3Medium: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 18.sp,
        letterSpacing = (-1.1).sp
    ),
    val body3Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 24.sp,
        letterSpacing = (-3).sp
    ),
    val detail1: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = (-1.1).sp
    ),
    val detail2: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 16.sp,
        letterSpacing = (-1.1).sp
    ),
    val label1SemiBold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        letterSpacing = (-1.1).sp
    ),
    val label1Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 18.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 24.sp,
        letterSpacing = (-1.1).sp
    ),
    val label2SemiBold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = (-1.1).sp
    ),
    val label2Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 20.sp,
        letterSpacing = (-1.1).sp
    ),
    val label3SemiBold: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16.sp,
        letterSpacing = (-1.1).sp
    ),
    val label3Regular: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 16.sp,
        letterSpacing = (-1.1).sp
    ),
    val label4: TextStyle = TextStyle(
        fontFamily = pretendard,
        fontSize = 10.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 12.sp,
        letterSpacing = (-1.1).sp
    ),
)

internal val LocalPokitTypography = staticCompositionLocalOf { PokitTypography() }
