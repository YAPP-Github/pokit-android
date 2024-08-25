package pokitmons.pokit.core.ui.components.atom.chip.subcomponents.container

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipIconPosiion
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun Modifier.pokitChipContainerModifier(
    iconPosition: PokitChipIconPosiion?,
    state: PokitChipState,
    size: PokitChipSize,
    type: PokitChipType,
    onClick: (() -> Unit)? = null,
): Modifier {
    val backgroundColor = getBackgroundColor(state = state, type = type)
    val strokeColor = getStrokeColor(state = state, type = type)
    val padding = getPadding(iconPosition = iconPosition, size = size)

    return this then Modifier
        .clip(
            shape = RoundedCornerShape(9999.dp)
        )
        .clickable(
            enabled = (onClick != null && state != PokitChipState.DISABLED),
            onClick = onClick ?: {},
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
        )
        .background(
            shape = RoundedCornerShape(9999.dp),
            color = backgroundColor
        )
        .border(
            shape = RoundedCornerShape(9999.dp),
            width = 1.dp,
            color = strokeColor
        )
        .padding(
            paddingValues = padding
        )
}

@Composable
private fun getBackgroundColor(
    state: PokitChipState,
    type: PokitChipType,
): Color {
    return when {
        state == PokitChipState.DEFAULT -> {
            PokitTheme.colors.backgroundBase
        }

        state == PokitChipState.DISABLED -> {
            PokitTheme.colors.backgroundDisable
        }

        state == PokitChipState.STROKE -> {
            PokitTheme.colors.backgroundBase
        }

        state == PokitChipState.FILLED && type == PokitChipType.PRIMARY -> {
            PokitTheme.colors.brand
        }

        state == PokitChipState.FILLED && type == PokitChipType.SECONDARY -> {
            PokitTheme.colors.backgroundTertiary
        }

        else -> {
            PokitTheme.colors.backgroundBase
        }
    }
}

@Composable
private fun getStrokeColor(
    state: PokitChipState,
    type: PokitChipType,
): Color {
    return when {
        state == PokitChipState.DEFAULT -> {
            PokitTheme.colors.borderSecondary
        }

        state == PokitChipState.DISABLED -> {
            Color.Unspecified
        }

        state == PokitChipState.FILLED -> {
            Color.Unspecified
        }

        state == PokitChipState.STROKE && type == PokitChipType.PRIMARY -> {
            PokitTheme.colors.brand
        }

        state == PokitChipState.STROKE && type == PokitChipType.SECONDARY -> {
            PokitTheme.colors.borderPrimary
        }

        else -> {
            PokitTheme.colors.borderSecondary
        }
    }
}

private fun getPadding(
    iconPosition: PokitChipIconPosiion?,
    size: PokitChipSize,
): PaddingValues {
    return when (size) {
        PokitChipSize.SMALL -> {
            if (iconPosition == PokitChipIconPosiion.LEFT) {
                PaddingValues(start = 8.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
            } else if (iconPosition == PokitChipIconPosiion.RIGHT) {
                PaddingValues(start = 12.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            } else {
                PaddingValues(all = 8.dp)
            }
        }

        PokitChipSize.MEDIUM -> {
            if (iconPosition == PokitChipIconPosiion.LEFT) {
                PaddingValues(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            } else if (iconPosition == PokitChipIconPosiion.RIGHT) {
                PaddingValues(start = 16.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
            } else {
                PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            }
        }
    }
}
