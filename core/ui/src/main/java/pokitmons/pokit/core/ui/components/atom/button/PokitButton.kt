package pokitmons.pokit.core.ui.components.atom.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonState
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.components.atom.button.subcomponents.container.PokitButtonContainer
import pokitmons.pokit.core.ui.components.atom.button.subcomponents.icon.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.subcomponents.text.PokitButtonText

@Composable
fun PokitButton(
    text : String?,
    icon: PokitButtonIcon?,
    onClick : () -> Unit,
    modifier : Modifier = Modifier,
    enable : Boolean = true,
    shape: PokitButtonShape = PokitButtonShape.RECTANGLE,
    style: PokitButtonStyle = PokitButtonStyle.FILLED,
    size: PokitButtonSize = PokitButtonSize.MIDDLE,
    type: PokitButtonType = PokitButtonType.PRIMARY,
) {
    val state = remember(enable) { getState(enable) }

    PokitButtonContainer(
        hasText = text != null,
        iconPosition = icon?.position,
        modifier = modifier,
        shape = shape,
        state = state,
        style = style,
        size = size,
        type = type,
        onClick = onClick
    ) {
        if (icon?.position == PokitButtonIconPosition.LEFT) {
            PokitButtonIcon(size = size, state = state, type = type, style = style, resourceId = icon.resourceId)
        }

        text?.let {
            PokitButtonText(
                text = text,
                size = size,
                state = state,
                type = type,
                style = style
            )
        }

        if (icon?.position == PokitButtonIconPosition.RIGHT) {
            PokitButtonIcon(size = size, state = state, type = type, style = style, resourceId = icon.resourceId)
        }
    }
}

private fun getState(enable : Boolean) : PokitButtonState {
    return if (enable) {
        PokitButtonState.DEFAULT
    } else {
        PokitButtonState.DISABLE
    }
}
