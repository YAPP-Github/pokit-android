package pokitmons.pokit.core.ui.components.block.category_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.block.category_card.attributes.CategoryCardState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryCard(state = CategoryCardState.DISABLE, item = "STRING", painter = painterResource(id = R.drawable.icon_24_star), title = "카테고리입니당", sub = "15개 항목", onClickKebab = {}, onClickItem = {})

            CategoryCard(state = CategoryCardState.DEFAULT, item = "STRING", painter = painterResource(id = R.drawable.icon_24_star), title = "카테고리입니당", sub = "15개 항목", onClickKebab = {}, onClickItem = {})

            CategoryCard(state = CategoryCardState.ACTIVE, item = "STRING", painter = painterResource(id = R.drawable.icon_24_star), title = "카테고리입니당", sub = "15개 항목", onClickKebab = {}, onClickItem = {})
        }
    }
}
