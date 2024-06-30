package pokitmons.pokit.core.ui.components.block.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
private fun PokitSelectPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (enabled in arrayOf(false, true)) {
                for (readOnly in arrayOf(false, true)) {
                    PokitSelect(
                        text = "입력",
                        hintText = "선택해주세요",
                        label = "Label",
                        onClick = {},
                        enable = enabled,
                        readOnly = readOnly
                    )

                    PokitSelect(
                        text = "",
                        hintText = "선택해주세요",
                        label = "Label",
                        onClick = {},
                        enable = enabled,
                        readOnly = readOnly
                    )
                }
            }
        }
    }
}
