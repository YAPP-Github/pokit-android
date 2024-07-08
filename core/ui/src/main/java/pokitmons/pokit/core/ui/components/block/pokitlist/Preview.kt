package pokitmons.pokit.core.ui.components.block.pokitlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun PokitListPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PokitList(
                state = PokitListState.DISABLE,
                item = "STRING",
                title = "카테고리입니당",
                sub = "15개 항목",
                onClickKebab = {},
                onClickItem = {}
            )

            PokitList(
                state = PokitListState.DEFAULT,
                item = "STRING",
                title = "카테고리입니당",
                sub = "15개 항목",
                onClickKebab = {},
                onClickItem = {}
            )

            PokitList(
                state = PokitListState.ACTIVE,
                item = "STRING",
                title = "카테고리입니당",
                sub = "15개 항목",
                onClickKebab = {},
                onClickItem = {}
            )
        }
    }
}
