package pokitmons.pokit.core.ui.components.atom.button.subcomponents.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonState
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
internal fun PokitButtonIcon(
    size: PokitButtonSize,
    state: PokitButtonState,
    type: PokitButtonType,
    style: PokitButtonStyle,
    resourceId: Int,
) {
    val iconColor = getColor(state = state, type = type, style = style)
    val iconSize = getSize(size = size)

    Icon(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        tint = iconColor,
        modifier = Modifier.size(iconSize)
    )
}

@Composable
private fun getColor(
    state: PokitButtonState,
    type: PokitButtonType,
    style: PokitButtonStyle,
): Color {
    return when {
        state == PokitButtonState.DISABLE -> {
            PokitTheme.colors.iconDisable
        }

        type == PokitButtonType.PRIMARY && style == PokitButtonStyle.FILLED -> {
            PokitTheme.colors.inverseWh
        }

        type == PokitButtonType.PRIMARY && style == PokitButtonStyle.STROKE -> {
            PokitTheme.colors.iconPrimary
        }

        type == PokitButtonType.SECONDARY && style == PokitButtonStyle.FILLED -> {
            PokitTheme.colors.inverseWh
        }

        type == PokitButtonType.SECONDARY && style == PokitButtonStyle.STROKE -> {
            PokitTheme.colors.iconPrimary
        }

        else -> {
            PokitTheme.colors.iconPrimary
        }
    }
}

@Composable
private fun getSize(
    size: PokitButtonSize,
): Dp {
    return when (size) {
        PokitButtonSize.SMALL -> {
            16.dp
        }

        PokitButtonSize.MIDDLE -> {
            20.dp
        }

        PokitButtonSize.LARGE -> {
            24.dp
        }
    }
}
