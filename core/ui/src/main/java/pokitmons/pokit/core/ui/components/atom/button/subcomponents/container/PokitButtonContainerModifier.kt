package pokitmons.pokit.core.ui.components.atom.button.subcomponents.container

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonState
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun Modifier.pokitButtonContainerModifier(
    hasText: Boolean,
    iconPosition: PokitButtonIconPosition?,
    shape: PokitButtonShape,
    state: PokitButtonState,
    style: PokitButtonStyle,
    size: PokitButtonSize,
    type: PokitButtonType,
    onClick: () -> Unit = {},
): Modifier {
    val buttonShape = getShape(shape, size)
    val strokeWidth = getBorderWidth(style)
    val padding = getPadding(size, hasText, iconPosition)
    val backgroundColor = getBackgroundColor(type, style, state)
    val strokeColor = getStrokeColor(type, state)

    return this then Modifier
        .clip(
            shape = buttonShape,
        )
        .clickable(
            enabled = (state != PokitButtonState.DISABLE),
            onClick = onClick,
        )
        .background(
            shape = buttonShape,
            color = backgroundColor,
        )
        .border(
            shape = buttonShape,
            width = strokeWidth,
            color = strokeColor,
        )
        .padding(
            paddingValues = padding,
        )
}

private fun getShape(
    shape: PokitButtonShape,
    size: PokitButtonSize,
): Shape {
    return when {
        shape == PokitButtonShape.RECTANGLE && size == PokitButtonSize.SMALL -> {
            RoundedCornerShape(4.dp)
        }

        shape == PokitButtonShape.RECTANGLE && size == PokitButtonSize.MIDDLE -> {
            RoundedCornerShape(8.dp)
        }

        shape == PokitButtonShape.RECTANGLE && size == PokitButtonSize.LARGE -> {
            RoundedCornerShape(8.dp)
        }

        else -> { // rounded button
            RoundedCornerShape(9999.dp)
        }
    }
}

private fun getBorderWidth(
    style: PokitButtonStyle,
): Dp {
    return when (style) {
        PokitButtonStyle.FILLED -> {
            0.dp
        }

        PokitButtonStyle.STROKE -> {
            1.dp
        }
    }
}

private fun getPadding(
    size: PokitButtonSize,
    hasText: Boolean,
    iconPosition: PokitButtonIconPosition?,
): PaddingValues {
    return when (size) {
        PokitButtonSize.SMALL -> {
            if (!hasText) { // icon only
                PaddingValues(all = 8.dp)
            } else if (iconPosition == PokitButtonIconPosition.LEFT) {
                PaddingValues(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 12.dp)
            } else if (iconPosition == PokitButtonIconPosition.RIGHT) {
                PaddingValues(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
            } else { // text only
                PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            }
        }

        PokitButtonSize.MIDDLE -> {
            if (!hasText) { // icon only
                PaddingValues(all = 10.dp)
            } else if (iconPosition == PokitButtonIconPosition.LEFT) {
                PaddingValues(start = 16.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
            } else if (iconPosition == PokitButtonIconPosition.RIGHT) {
                PaddingValues(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 16.dp)
            } else { // text only
                PaddingValues(horizontal = 26.dp, vertical = 10.dp)
            }
        }

        PokitButtonSize.LARGE -> {
            if (!hasText) { // icon only
                PaddingValues(all = 12.dp)
            } else if (iconPosition == PokitButtonIconPosition.LEFT) {
                PaddingValues(start = 20.dp, top = 12.dp, bottom = 12.dp, end = 24.dp)
            } else if (iconPosition == PokitButtonIconPosition.RIGHT) {
                PaddingValues(start = 24.dp, top = 12.dp, bottom = 12.dp, end = 20.dp)
            } else { // text only
                PaddingValues(horizontal = 20.dp, vertical = 12.dp)
            }
        }
    }
}

@Composable
private fun getBackgroundColor(
    type: PokitButtonType,
    style: PokitButtonStyle,
    state: PokitButtonState,
): Color {
    return when {
        state == PokitButtonState.DISABLE -> {
            PokitTheme.colors.backgroundDisable
        }

        style == PokitButtonStyle.STROKE -> {
            PokitTheme.colors.backgroundBase
        }

        type == PokitButtonType.PRIMARY && style == PokitButtonStyle.FILLED -> {
            PokitTheme.colors.brand
        }

        type == PokitButtonType.SECONDARY && style == PokitButtonStyle.FILLED -> {
            PokitTheme.colors.backgroundTertiary
        }

        else -> {
            PokitTheme.colors.backgroundBase
        }
    }
}

@Composable
private fun getStrokeColor(
    type: PokitButtonType,
    state: PokitButtonState,
): Color {
    return when {
        state == PokitButtonState.DISABLE -> {
            Color.Unspecified
        }

        type == PokitButtonType.PRIMARY -> {
            PokitTheme.colors.brand
        }

        type == PokitButtonType.SECONDARY -> {
            PokitTheme.colors.borderSecondary
        }

        else -> {
            Color.Unspecified
        }
    }
}
