package pokitmons.pokit.core.ui.components.block.switchradio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.switchradio.attributes.PokitSwitchRadioStyle
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun PokitSwitchRadioPreview() {
    val scrollState = rememberScrollState()
    val sampleItemList = listOf("텍스트1", "텍스트2")
    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            enumValues<PokitSwitchRadioStyle>().forEach { style ->
                PokitSwitchRadio(
                    itemList = sampleItemList,
                    selectedItem = sampleItemList[0],
                    onClickItem = {},
                    getTitleFromItem = { it },
                    modifier = Modifier.fillMaxWidth(),
                    style = style
                )

                PokitSwitchRadio(
                    itemList = sampleItemList,
                    selectedItem = sampleItemList[0],
                    onClickItem = {},
                    getTitleFromItem = { it },
                    modifier = Modifier.fillMaxWidth(),
                    style = style,
                    enabled = false
                )
            }
        }
    }
}
