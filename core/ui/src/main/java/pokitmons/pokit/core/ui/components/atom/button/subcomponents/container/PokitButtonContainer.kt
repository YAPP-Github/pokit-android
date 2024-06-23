package pokitmons.pokit.core.ui.components.atom.button.subcomponents.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonState
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType

@Composable
internal fun PokitButtonContainer(
    hasText : Boolean,
    iconPosition : PokitButtonIconPosition?,
    modifier : Modifier = Modifier,
    onClick : () -> Unit = {},
    shape: PokitButtonShape = PokitButtonShape.RECTANGLE,
    state: PokitButtonState = PokitButtonState.DEFAULT,
    style: PokitButtonStyle = PokitButtonStyle.FILLED,
    size: PokitButtonSize = PokitButtonSize.MIDDLE,
    type: PokitButtonType = PokitButtonType.PRIMARY,
    content: @Composable RowScope.() -> Unit,
) {
    val space = getItemSpace(size)

    Row(
        modifier = Modifier
            .pokitButtonContainerModifier(
                hasText = hasText,
                iconPosition = iconPosition,
                shape = shape,
                state = state,
                style = style,
                size = size,
                type = type,
                onClick = onClick
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(
                space = space,
                alignment = Alignment.CenterHorizontally
            ),
    ) {
        content()
    }
}

private fun getItemSpace(size: PokitButtonSize) : Dp {
    return when (size) {
        PokitButtonSize.SMALL -> {
            4.dp
        }
        PokitButtonSize.MIDDLE -> {
            8.dp
        }
        PokitButtonSize.LARGE -> {
            12.dp
        }
    }
}
