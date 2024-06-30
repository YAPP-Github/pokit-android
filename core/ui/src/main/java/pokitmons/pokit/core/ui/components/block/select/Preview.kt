package pokitmons.pokit.core.ui.components.block.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.select.attributes.PokitSelectState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun PokitSelectPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            enumValues<PokitSelectState>().forEach { state ->
                PokitSelect(
                    text = "선택해주세요",
                    label = "Label",
                    onClick = {},
                    state = state
                )
            }
        }
    }
}
