package pokitmons.pokit.core.ui.components.block.tap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme


@Preview(showBackground = true)
@Composable
private fun PokitSwitchRadioPreview() {
    val scrollState = rememberScrollState()
    val sampleTapList = listOf("tap1", "tap2")
    var currentSelectedTap by remember { mutableStateOf(sampleTapList[0]) }

    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                sampleTapList.forEach { tap ->
                    PokitTap(
                        text = tap,
                        data = tap,
                        onClick = {currentSelectedTap = it},
                        selectedItem = currentSelectedTap
                    )
                }
            }
        }
    }
}
