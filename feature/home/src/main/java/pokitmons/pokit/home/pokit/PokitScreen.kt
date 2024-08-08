package pokitmons.pokit.home.pokit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.pokitcard.PokitCard

@Composable
fun PokitScreen() {
    val dummy = arrayListOf<PokitCardDummy>().apply {
        add(PokitCardDummy())
        add(PokitCardDummy())
        add(PokitCardDummy())
        add(PokitCardDummy())
        add(PokitCardDummy())
    }.toList()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(dummy) { pokitInfo ->
            PokitCard(
                text = pokitInfo.text,
                linkCount = pokitInfo.linkCount,
                painter = null,
                onClick = { /*TODO*/ },
                onClickKebab = { /*TODO*/ }
            )
        }
    }
}

data class PokitCardDummy(
    val text: String = "요리/레시피",
    val linkCount: Int = 10,
)

@Preview
@Composable
fun test() {
    PokitScreen()
}
