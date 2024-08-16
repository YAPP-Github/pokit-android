package pokitmons.pokit.home.pokit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import pokitmons.pokit.core.ui.components.block.pokitcard.PokitCard
import pokitmons.pokit.home.HomeMid

@Composable
fun PokitScreen(
    modifier: Modifier = Modifier,
    viewModel: PokitViewModel = hiltViewModel(),
) {
    viewModel.loadPokits()
    val pokits = viewModel.pokits.collectAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        HomeMid()

        when (viewModel.selectedCategory.value) {
            is Category.Pokit -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(pokits.value) { pokitDetail ->
                        PokitCard(
                            text = pokitDetail.title,
                            linkCount = pokitDetail.count,
                            painter = rememberAsyncImagePainter(model = pokitDetail.image.url),
                            onClick = { /*TODO*/ },
                            onClickKebab = { /*TODO*/ }
                        )
                    }
                }
            }

            is Category.Unclassified -> {
                UnclassifiedScreen()
            }
        }
    }
}


@Preview
@Composable
fun PokitScreenPreview(viewModel: PokitViewModel = hiltViewModel()) {
    PokitScreen()
}


