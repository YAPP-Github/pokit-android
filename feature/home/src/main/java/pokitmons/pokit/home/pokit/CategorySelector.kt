package pokitmons.pokit.home.pokit

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
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokitButton(
                size = PokitButtonSize.SMALL,
                style = when (viewModel.selectedCategory.value) {
                    is Category.Pokit -> PokitButtonStyle.FILLED
                    is Category.Unclassified -> PokitButtonStyle.DEFAULT
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

            Spacer(modifier = Modifier.padding(start = 8.dp))

            PokitButton(
                size = PokitButtonSize.SMALL,
                style = when (viewModel.selectedCategory.value) {
                    is Category.Pokit -> PokitButtonStyle.DEFAULT
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
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = DrawableResource.icon_24_align),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.padding(start = 2.dp))

                if (viewModel.selectedCategory.value == Category.Pokit) {
                    Text(
                        modifier = Modifier
                            .noRippleClickable {
                                when (viewModel.pokitsSortOrder.value) {
                                    is PokitsSortOrder.Latest -> viewModel.updatePokitsSortOrder(PokitsSortOrder.Name)
                                    is PokitsSortOrder.Name -> viewModel.updatePokitsSortOrder(PokitsSortOrder.Latest)
                                }
                            }
                            .align(Alignment.CenterVertically),
                        text = when (viewModel.pokitsSortOrder.value) {
                            is PokitsSortOrder.Latest -> "최신순"
                            is PokitsSortOrder.Name -> "이름순"
                        },
                        style = PokitTheme.typography.body3Medium
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .noRippleClickable {
                                when (viewModel.linksSortOrder.value) {
                                    is UncategorizedLinksSortOrder.Latest -> viewModel.updateLinksSortOrder(UncategorizedLinksSortOrder.Older)
                                    is UncategorizedLinksSortOrder.Older -> viewModel.updateLinksSortOrder(UncategorizedLinksSortOrder.Latest)
                                }
                            }
                            .align(Alignment.CenterVertically),
                        text = when (viewModel.linksSortOrder.value) {
                            is UncategorizedLinksSortOrder.Latest -> "최신순"
                            is UncategorizedLinksSortOrder.Older -> "오래된순"
                        },
                        style = PokitTheme.typography.body3Medium
                    )
                }
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
