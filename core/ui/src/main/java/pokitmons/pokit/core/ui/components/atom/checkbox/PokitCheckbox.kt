package pokitmons.pokit.core.ui.components.atom.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxShape
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxStyle
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.conditional

@Composable
fun PokitCheckbox(
    checked: Boolean,
    onClick: ((Boolean) -> Unit)? = null,
    style: PokitCheckboxStyle = PokitCheckboxStyle.STROKE,
    shape: PokitCheckboxShape = PokitCheckboxShape.RECTANGLE,
    enabled: Boolean = true,
) {
    val checkboxShape = getShape(shape = shape)
    val backgroundColor = getBackgroundColor(style = style, checked = checked, enabled = enabled)
    val iconTintColor = getIconTintColor(style = style, checked = checked, enabled = enabled)
    val strokeColor = getStrokeColor(style = style, checked = checked, enabled = enabled)

    val pressedBackgroundColor = getPressedBackgroundColor(style = style)
    val pressedIconTintColor = getPressedIconTintColor(style = style)
    val pressedStrokeColor = getPressedStrokeColor(style = style)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Image(
        painter = painterResource(id = R.drawable.icon_24_check),
        contentDescription = null,
        colorFilter = ColorFilter.tint(if (isPressed) pressedIconTintColor else iconTintColor),
        modifier = Modifier
            .size(24.dp)
            .clip(
                shape = checkboxShape
            )
            .conditional(
                condition = (onClick != null)
            ) {
                clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    enabled = enabled,
                    onClick = {
                        onClick?.invoke(!checked)
                    }
                )
            }
            .background(
                color = if (isPressed) pressedBackgroundColor else backgroundColor
            )
            .border(
                width = 1.dp,
                color = if (isPressed) pressedStrokeColor else strokeColor,
                shape = checkboxShape
            )
    )
}

@Composable
private fun getShape(
    shape: PokitCheckboxShape,
): Shape {
    return when (shape) {
        PokitCheckboxShape.RECTANGLE -> RoundedCornerShape(4.dp)
        PokitCheckboxShape.CIRCLE -> CircleShape
    }
}

@Composable
private fun getIconTintColor(
    style: PokitCheckboxStyle,
    checked: Boolean,
    enabled: Boolean,
): Color {
    return when {
        !enabled -> {
            PokitTheme.colors.iconDisable
        }

        !checked -> {
            PokitTheme.colors.iconTertiary
        }

        style == PokitCheckboxStyle.FILLED -> {
            PokitTheme.colors.inverseWh
        }

        else -> {
            PokitTheme.colors.brand
        }
    }
}

@Composable
private fun getStrokeColor(
    style: PokitCheckboxStyle,
    checked: Boolean,
    enabled: Boolean,
): Color {
    return when {
        !enabled -> {
            Color.Unspecified
        }

        !checked && (style != PokitCheckboxStyle.ICON_ONLY) -> {
            PokitTheme.colors.borderSecondary
        }

        style == PokitCheckboxStyle.STROKE -> {
            PokitTheme.colors.brand
        }

        else -> {
            Color.Unspecified
        }
    }
}

@Composable
private fun getBackgroundColor(
    style: PokitCheckboxStyle,
    checked: Boolean,
    enabled: Boolean,
): Color {
    return when {
        !enabled -> {
            PokitTheme.colors.backgroundDisable
        }

        !checked && (style != PokitCheckboxStyle.ICON_ONLY) -> {
            PokitTheme.colors.backgroundBase
        }

        style == PokitCheckboxStyle.FILLED -> {
            PokitTheme.colors.brand
        }

        style == PokitCheckboxStyle.STROKE -> {
            PokitTheme.colors.backgroundBase
        }

        else -> {
            Color.Unspecified
        }
    }
}

@Composable
private fun getPressedIconTintColor(style: PokitCheckboxStyle): Color {
    return when(style) {
        PokitCheckboxStyle.FILLED -> PokitTheme.colors.inverseWh
        PokitCheckboxStyle.STROKE -> PokitTheme.colors.brandLight
        PokitCheckboxStyle.ICON_ONLY -> PokitTheme.colors.brandLight
    }
}

@Composable
private fun getPressedStrokeColor(style: PokitCheckboxStyle): Color {
    return when(style) {
        PokitCheckboxStyle.FILLED -> Color.Unspecified
        PokitCheckboxStyle.STROKE -> PokitTheme.colors.brandLight
        PokitCheckboxStyle.ICON_ONLY -> Color.Unspecified
    }
}

@Composable
private fun getPressedBackgroundColor(
    style: PokitCheckboxStyle,
): Color {
    return when(style) {
        PokitCheckboxStyle.FILLED -> PokitTheme.colors.brandLight
        PokitCheckboxStyle.STROKE -> PokitTheme.colors.backgroundBase
        PokitCheckboxStyle.ICON_ONLY -> Color.Unspecified
    }
}
