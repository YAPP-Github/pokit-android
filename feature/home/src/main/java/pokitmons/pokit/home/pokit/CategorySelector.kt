package pokitmons.pokit.home.pokit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.home.pokit.Category
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.home.pokit.SortOrder
import pokitmons.pokit.core.ui.R.drawable as DrawableResource

@Composable
fun HomeMid(viewModel: PokitViewModel = hiltViewModel()) {
    Spacer(modifier = Modifier.height(24.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PokitButton(
                style = when (viewModel.selectedCategory.value) {
                    is Category.Pokit -> PokitButtonStyle.FILLED
                    is Category.Unclassified -> PokitButtonStyle.STROKE
                },
                text = "포킷",
                shape = PokitButtonShape.ROUND,
                icon = PokitButtonIcon(
                    DrawableResource.icon_24_folderline,
                    PokitButtonIconPosition.LEFT
                ),
                type = when (viewModel.selectedCategory.value) {
                    is Category.Pokit -> PokitButtonType.PRIMARY
                    is Category.Unclassified -> PokitButtonType.SECONDARY
                },
                onClick = { viewModel.updateCategory(Category.Pokit) }
            )

            Spacer(modifier = Modifier.padding(start = 12.dp))

            PokitButton(
                style = when (viewModel.selectedCategory.value) {
                    is Category.Pokit -> PokitButtonStyle.STROKE
                    is Category.Unclassified -> PokitButtonStyle.FILLED
                },
                text = "미분류",
                shape = PokitButtonShape.ROUND,
                icon = PokitButtonIcon(
                    DrawableResource.icon_24_info,
                    PokitButtonIconPosition.LEFT
                ),
                type = when (viewModel.selectedCategory.value) {
                    is Category.Pokit -> PokitButtonType.SECONDARY
                    is Category.Unclassified -> PokitButtonType.PRIMARY
                },
                onClick = { viewModel.updateCategory(Category.Unclassified) }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = DrawableResource.icon_24_align),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.padding(start = 2.dp))

                Text(
                    modifier = Modifier
                        .clickable {
                            when (viewModel.sortOrder.value) {
                                is SortOrder.Latest -> viewModel.updateSortOrder(SortOrder.Name)
                                is SortOrder.Name -> viewModel.updateSortOrder(SortOrder.Latest)
                            }
                        }
                        .align(Alignment.CenterVertically),
                    text = when (viewModel.sortOrder.value) {
                        is SortOrder.Latest -> "최신순"
                        is SortOrder.Name -> "이름순"
                    },
                    style = PokitTheme.typography.body3Medium,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomRow() {
    HomeMid()
}
