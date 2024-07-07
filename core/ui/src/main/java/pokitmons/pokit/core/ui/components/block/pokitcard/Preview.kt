package pokitmons.pokit.core.ui.components.block.pokitcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun PokitardPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PokitCard(
                    modifier = Modifier.weight(1f),
                    text = "요리/레시피",
                    linkCount = 10,
                    painter = null,
                    onClick = { },
                    onClickKebab = { }
                )

                PokitCard(
                    modifier = Modifier.weight(1f),
                    text = "요리/레시피",
                    linkCount = 5,
                    painter = painterResource(id = R.drawable.icon_24_google),
                    onClick = { },
                    onClickKebab = { }
                )
            }
        }
    }
}
