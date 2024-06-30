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
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme


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
                            for (enable in arrayOf(false, true)) {
                                PokitButton(
                                    text = "버튼",
                                    icon = PokitButtonIcon(resourceId = R.drawable.icon_24_search, position = PokitButtonIconPosition.LEFT),
                                    onClick = {},
                                    enable = enable,
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
