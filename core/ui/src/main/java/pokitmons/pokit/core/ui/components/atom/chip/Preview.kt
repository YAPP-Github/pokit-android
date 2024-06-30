package pokitmons.pokit.core.ui.components.atom.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipIconPosiion
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipType
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
            enumValues<PokitChipSize>().forEach { size ->
                enumValues<PokitChipState>().forEach { state ->
                    enumValues<PokitChipType>().forEach { type ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PokitChip(
                                data = "String",
                                text = "텍스트",
                                state = state,
                                type = type,
                                size = size,
                                removeIconPosition = PokitChipIconPosiion.LEFT,
                                onClickRemove = {},
                                onClickItem = {}
                            )

                            PokitChip(
                                data = "String",
                                text = "텍스트",
                                state = state,
                                type = type,
                                size = size,
                                removeIconPosition = PokitChipIconPosiion.RIGHT,
                                onClickRemove = {},
                                onClickItem = {}
                            )

                            PokitChip(
                                data = "String",
                                text = "텍스트",
                                state = state,
                                type = type,
                                size = size,
                                removeIconPosition = null,
                                onClickRemove = {},
                                onClickItem = {}
                            )
                        }
                    }
                }
            }
        }
    }
}
