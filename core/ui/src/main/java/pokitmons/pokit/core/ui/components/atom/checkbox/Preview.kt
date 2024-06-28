package pokitmons.pokit.core.ui.components.atom.checkbox

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
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxShape
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxStyle
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
            enumValues<PokitCheckboxShape>().forEach { shape ->
                enumValues<PokitCheckboxStyle>().forEach { style ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PokitCheckbox(
                            checked = true,
                            onClick = {},
                            shape = shape,
                            style = style,
                            enabled = true,
                        )

                        PokitCheckbox(
                            checked = false,
                            onClick = {},
                            shape = shape,
                            style = style,
                            enabled = true,
                        )

                        PokitCheckbox(
                            checked = true,
                            onClick = {},
                            shape = shape,
                            style = style,
                            enabled = false,
                        )

                        PokitCheckbox(
                            checked = false,
                            onClick = {},
                            shape = shape,
                            style = style,
                            enabled = false,
                        )
                    }
                }
            }
        }
    }
}
