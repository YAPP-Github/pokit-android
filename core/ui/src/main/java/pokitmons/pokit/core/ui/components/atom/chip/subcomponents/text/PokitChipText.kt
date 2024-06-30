package pokitmons.pokit.core.ui.components.atom.chip.subcomponents.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitChipText(
    text: String,
    state: PokitChipState,
    size: PokitChipSize,
) {
    val textStyle = getTextStyle(size = size)
    val textColor = getTextColor(state = state)

    Text(text = text, style = textStyle.copy(color = textColor))
}

@Composable
private fun getTextColor(
    state: PokitChipState,
): Color {
    return when (state) {
        PokitChipState.DEFAULT -> PokitTheme.colors.textTertiary
        PokitChipState.FILLED -> PokitTheme.colors.inverseWh
        PokitChipState.STROKE -> PokitTheme.colors.textPrimary
        PokitChipState.DISABLED -> PokitTheme.colors.textDisable
    }
}

@Composable
private fun getTextStyle(
    size: PokitChipSize,
): TextStyle {
    return when (size) {
        PokitChipSize.SMALL -> PokitTheme.typography.label3Regular
        PokitChipSize.MEDIUM -> PokitTheme.typography.label2Regular
    }
}
