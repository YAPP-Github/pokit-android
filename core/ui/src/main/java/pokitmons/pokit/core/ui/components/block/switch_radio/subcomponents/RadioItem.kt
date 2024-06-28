package pokitmons.pokit.core.ui.components.block.switch_radio.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.switch_radio.attributes.PokitSwitchRadioStyle
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun<T> SwitchRadioItem(
    text : String,
    data : T,
    onClickItem: (T) -> Unit,
    style: PokitSwitchRadioStyle,
    selected: Boolean,
    enabled: Boolean,
    modifier : Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)
    val backgroundColor = getBackgroundColor(style = style, selected = selected, enabled = enabled)
    val strokeColor = getStrokeColor(style = style, selected = selected, enabled = enabled)
    val textColor = getTextColor(style = style, selected = selected, enabled = enabled)

    Text(
        text = text,
        modifier = modifier
            .clip(shape = shape)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = enabled,
                onClick = { onClickItem(data) },
            )
            .background(
                shape = shape,
                color = backgroundColor
            )
            .border(
                width = 1.dp,
                shape = shape,
                color = strokeColor
            )
            .padding(all = 16.dp),
        style = PokitTheme.typography.body2Medium.copy(color = textColor),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun getBackgroundColor(
    style: PokitSwitchRadioStyle,
    selected: Boolean,
    enabled : Boolean,
) : Color {
    return when {
        !enabled -> {
            PokitTheme.colors.backgroundDisable
        }
        style == PokitSwitchRadioStyle.STROKE -> {
            PokitTheme.colors.backgroundBase
        }
        style == PokitSwitchRadioStyle.FILLED && selected -> {
            PokitTheme.colors.brand
        }
        style == PokitSwitchRadioStyle.FILLED && !selected -> {
            PokitTheme.colors.backgroundPrimary
        }
        else -> {
            Color.Unspecified
        }
    }
}

@Composable
private fun getStrokeColor(
    style: PokitSwitchRadioStyle,
    selected: Boolean,
    enabled: Boolean,
) : Color {
    return when {
        enabled && selected && style == PokitSwitchRadioStyle.STROKE -> {
            PokitTheme.colors.brand
        }
        else -> {
            Color.Unspecified
        }
    }
}

@Composable
private fun getTextColor(
    style: PokitSwitchRadioStyle,
    selected: Boolean,
    enabled: Boolean,
) : Color {
    return when {
        !enabled -> {
            PokitTheme.colors.textDisable
        }
        !selected -> {
            PokitTheme.colors.textTertiary
        }
        style == PokitSwitchRadioStyle.STROKE -> {
            PokitTheme.colors.brand
        }
        style == PokitSwitchRadioStyle.FILLED -> {
            PokitTheme.colors.inverseWh
        }
        else -> {
            Color.Unspecified
        }
    }
}
