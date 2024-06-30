package pokitmons.pokit.core.ui.components.atom.input

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
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIcon
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIconPosition
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputShape
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun PokitInputPreview() {
    val scrollState = rememberScrollState()
    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            enumValues<PokitInputShape>().forEach { shape ->
                for (isError in arrayOf(false, true)) {
                    for (enabled in arrayOf(false, true)) {
                        for (readOnly in arrayOf(false, true)) {
                            enumValues<PokitInputIconPosition>().forEach { iconPosition ->
                                PokitInput(
                                    text = "",
                                    hintText = "내용을 입력해주세요.",
                                    onChangeText = {},
                                    shape = shape,
                                    enable = enabled,
                                    readOnly = readOnly,
                                    isError = isError,
                                    icon = PokitInputIcon(iconPosition, R.drawable.icon_24_search)
                                )
                            }
                            PokitInput(
                                text = "",
                                hintText = "내용을 입력해주세요.",
                                onChangeText = {},
                                shape = shape,
                                enable = enabled,
                                readOnly = readOnly,
                                isError = isError,
                                icon = null
                            )
                        }
                    }
                }
            }
        }
    }
}
