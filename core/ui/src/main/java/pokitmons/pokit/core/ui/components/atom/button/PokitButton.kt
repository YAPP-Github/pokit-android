package pokitmons.pokit.core.ui.components.atom.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
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
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitButton(
    text : String?,
    icon: PokitButtonIcon?,
    onClick : () -> Unit,
    modifier : Modifier = Modifier,
    shape: PokitButtonShape = PokitButtonShape.RECTANGLE,
    state: PokitButtonState = PokitButtonState.DEFAULT,
    style: PokitButtonStyle = PokitButtonStyle.FILLED,
    size: PokitButtonSize = PokitButtonSize.MIDDLE,
    type: PokitButtonType = PokitButtonType.PRIMARY,
) {
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

@Preview(showBackground = true)
@Composable
fun PokitButtonPreview() {
    val scrollState = rememberScrollState()
    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            enumValues<PokitButtonSize>().forEach { size ->
                enumValues<PokitButtonShape>().forEach { shape ->
                    enumValues<PokitButtonType>().forEach { type ->
                        enumValues<PokitButtonStyle>().forEach { style ->
                            enumValues<PokitButtonState>().forEach { state ->
                                PokitButton(
                                    text = "버튼",
                                    icon = PokitButtonIcon(resourceId = R.drawable.icon_24_search, position = PokitButtonIconPosition.LEFT),
                                    onClick = {},
                                    state = state,
                                    size = size,
                                    shape = shape,
                                    style = style,
                                    type = type
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
