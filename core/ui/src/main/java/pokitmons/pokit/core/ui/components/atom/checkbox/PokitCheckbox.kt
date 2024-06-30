package pokitmons.pokit.core.ui.components.atom.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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

@Composable
fun PokitCheckbox(
    checked: Boolean,
    onClick: (Boolean) -> Unit,
    style: PokitCheckboxStyle = PokitCheckboxStyle.STROKE,
    shape: PokitCheckboxShape = PokitCheckboxShape.RECTANGLE,
    enabled: Boolean = true,
) {
    val checkboxShape = getShape(shape = shape)
    val backgroundColor = getBackgroundColor(style = style, checked = checked, enabled = enabled)
    val iconTintColor = getIconTintColor(style = style, checked = checked, enabled = enabled)
    val strokeColor = getStrokeColor(style = style, checked = checked, enabled = enabled)

    Image(
        painter = painterResource(id = R.drawable.icon_24_check),
        contentDescription = null,
        colorFilter = ColorFilter.tint(iconTintColor),
        modifier = Modifier
            .size(24.dp)
            .clip(
                shape = checkboxShape,
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = enabled,
                onClick = {
                    onClick(!checked)
                },
            )
            .background(
                color = backgroundColor,
            )
            .border(
                width = 1.dp,
                color = strokeColor,
                shape = checkboxShape,
            ),
    )
}

@Composable
private fun getShape(
    shape: PokitCheckboxShape,
): Shape {
    return when (shape) {
        PokitCheckboxShape.RECTANGLE -> {
            RoundedCornerShape(4.dp)
        }

        PokitCheckboxShape.CIRCLE -> {
            CircleShape
        }
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

        !checked -> {
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

        !checked -> {
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
