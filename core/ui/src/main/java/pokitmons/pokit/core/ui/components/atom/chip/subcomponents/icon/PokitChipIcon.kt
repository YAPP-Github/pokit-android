package pokitmons.pokit.core.ui.components.atom.chip.subcomponents.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitChipIcon(
    state : PokitChipState,
    size : PokitChipSize,
    onClick : () -> Unit,
) {
    val iconTintColor = getIconTintColor(state = state)
    val iconSize = getIconSize(size = size)

    Icon(
        painter = painterResource(id = R.drawable.icon_24_x),
        contentDescription = null,
        tint = iconTintColor,
        modifier = Modifier
            .size(iconSize)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
                enabled = (state != PokitChipState.DISABLED)
            )
    )
}

@Composable
private fun getIconTintColor(
    state : PokitChipState,
) : Color {
    return when(state) {
        PokitChipState.DEFAULT -> {
            PokitTheme.colors.iconSecondary
        }
        PokitChipState.FILLED -> {
            PokitTheme.colors.inverseWh
        }
        PokitChipState.STROKE -> {
            PokitTheme.colors.iconPrimary
        }
        PokitChipState.DISABLED -> {
            PokitTheme.colors.iconDisable
        }
    }
}

@Composable
private fun getIconSize(
    size : PokitChipSize,
) : Dp {
    return when(size) {
        PokitChipSize.SMALL -> {
            16.dp
        }
        PokitChipSize.MEDIUM -> {
            22.dp
        }
    }
}
