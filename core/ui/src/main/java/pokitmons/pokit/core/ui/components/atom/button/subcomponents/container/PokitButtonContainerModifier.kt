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
            shape = buttonShape
        )
        .clickable(
            enabled = (state != PokitButtonState.DISABLE),
            onClick = onClick
        )
        .background(
            shape = buttonShape,
            color = backgroundColor
        )
        .border(
            shape = buttonShape,
            width = strokeWidth,
            color = strokeColor
        )
        .padding(
            paddingValues = padding
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
        PokitButtonStyle.FILLED -> 0.dp
        PokitButtonStyle.STROKE -> 1.dp
    }
}

private fun getPadding(
    size: PokitButtonSize,
    hasText: Boolean,
    iconPosition: PokitButtonIconPosition?,
): PaddingValues {
    return when (size) {
        PokitButtonSize.SMALL -> {
            getPaddingBySize(
                hasText = hasText,
                iconPosition = iconPosition,
                verticalPaddingSize = 8.dp,
                textSidePaddingSize = 12.dp,
                iconSidePaddingSize = 8.dp,
                textOnlyPadding = 12.dp
            )
        }

        PokitButtonSize.MIDDLE -> {
            getPaddingBySize(
                hasText = hasText,
                iconPosition = iconPosition,
                verticalPaddingSize = 10.dp,
                textSidePaddingSize = 20.dp,
                iconSidePaddingSize = 16.dp,
                textOnlyPadding = 16.dp
            )
        }

        PokitButtonSize.LARGE -> {
            getPaddingBySize(
                hasText = hasText,
                iconPosition = iconPosition,
                verticalPaddingSize = 12.dp,
                textSidePaddingSize = 24.dp,
                iconSidePaddingSize = 20.dp,
                textOnlyPadding = 20.dp
            )
        }
    }
}

private fun getPaddingBySize(
    hasText: Boolean,
    iconPosition: PokitButtonIconPosition?,
    verticalPaddingSize: Dp,
    textSidePaddingSize: Dp,
    iconSidePaddingSize: Dp,
    textOnlyPadding: Dp,
): PaddingValues {
    return if (!hasText) { // icon only
        PaddingValues(horizontal = iconSidePaddingSize, vertical = verticalPaddingSize)
    } else if (iconPosition == PokitButtonIconPosition.LEFT) {
        PaddingValues(start = iconSidePaddingSize, top = verticalPaddingSize, bottom = verticalPaddingSize, end = textSidePaddingSize)
    } else if (iconPosition == PokitButtonIconPosition.RIGHT) {
        PaddingValues(start = textSidePaddingSize, top = verticalPaddingSize, bottom = verticalPaddingSize, end = iconSidePaddingSize)
    } else { // text only
        PaddingValues(horizontal = textOnlyPadding, vertical = verticalPaddingSize)
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
