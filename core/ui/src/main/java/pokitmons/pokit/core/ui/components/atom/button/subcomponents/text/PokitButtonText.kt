package pokitmons.pokit.core.ui.components.atom.button.subcomponents.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonState
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun PokitButtonText(
    text: String,
    size: PokitButtonSize,
    state: PokitButtonState,
    type: PokitButtonType,
    style: PokitButtonStyle,
) {
    val textStyle = getStyle(size = size, state = state, type = type, style = style)
    val textColor = getColor(state = state, type = type, style = style)
    Text(text = text, style = textStyle, color = textColor)
}

@Composable
private fun getStyle(
    size: PokitButtonSize,
    state: PokitButtonState,
    type: PokitButtonType,
    style: PokitButtonStyle,
): TextStyle {
    return when {
        size == PokitButtonSize.SMALL -> {
            PokitTheme.typography.label3Regular
        }

        size == PokitButtonSize.MIDDLE -> {
            PokitTheme.typography.label2Regular
        }

        (
            size == PokitButtonSize.LARGE &&
                state == PokitButtonState.DEFAULT &&
                type == PokitButtonType.PRIMARY &&
                style == PokitButtonStyle.FILLED
            ) -> {
            PokitTheme.typography.label1SemiBold
        }

        else -> {
            PokitTheme.typography.label1Regular
        }
    }
}

@Composable
private fun getColor(
    state: PokitButtonState,
    type: PokitButtonType,
    style: PokitButtonStyle,
): Color {
    return when {
        state == PokitButtonState.DISABLE -> {
            PokitTheme.colors.textDisable
        }

        type == PokitButtonType.PRIMARY && style == PokitButtonStyle.FILLED -> {
            PokitTheme.colors.inverseWh
        }

        type == PokitButtonType.PRIMARY && style == PokitButtonStyle.STROKE -> {
            PokitTheme.colors.textSecondary
        }

        type == PokitButtonType.SECONDARY && style == PokitButtonStyle.FILLED -> {
            PokitTheme.colors.inverseWh
        }

        type == PokitButtonType.SECONDARY && style == PokitButtonStyle.STROKE -> {
            PokitTheme.colors.textPrimary
        }

        else -> {
            PokitTheme.colors.textPrimary
        }
    }
}
