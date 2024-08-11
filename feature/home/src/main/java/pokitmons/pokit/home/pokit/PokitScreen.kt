package pokitmons.pokit.home.pokit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.block.pokitcard.PokitCard
import pokitmons.pokit.home.HomeMid

@Composable
fun PokitScreen(modifier: Modifier) {
    val dummy = arrayListOf<PokitCardDummy>().apply {
        add(PokitCardDummy())
        add(PokitCardDummy())
        add(PokitCardDummy())
        add(PokitCardDummy())
        add(PokitCardDummy())
    }.toList()

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        HomeMid()
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
}

data class PokitCardDummy(
    val text: String = "요리/레시피",
    val linkCount: Int = 10,
)
