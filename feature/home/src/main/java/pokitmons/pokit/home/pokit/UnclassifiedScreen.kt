package pokitmons.pokit.home.pokit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.strayalpaca.pokitdetail.R
import com.strayalpaca.pokitdetail.model.BottomSheetType
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent

@Composable
fun UnclassifiedScreen(
    viewModel: PokitViewModel = hiltViewModel(),
    onNavigateToLinkModify: (String) -> Unit = {},
) {
    val unCategoryLinks = viewModel.unCategoryLinks.collectAsState()

    val pokitOptionBottomSheetType by viewModel.pokitOptionBottomSheetType.collectAsState()
    val currentSelectedLink by viewModel.currentSelectedLink.collectAsState()

    PokitBottomSheet(
        onHideBottomSheet = viewModel::hideLinkOptionBottomSheet,
        show = pokitOptionBottomSheetType != null
    ) {
        when (pokitOptionBottomSheetType) {
            BottomSheetType.MODIFY -> {
                ModifyBottomSheetContent(
                    onClickShare = {},
                    onClickModify = remember {
                        {
                            viewModel.hideLinkOptionBottomSheet()
                            onNavigateToLinkModify(currentSelectedLink!!.id)
                        }
                    },
                    onClickRemove = viewModel::showLinkRemoveBottomSheet
                )
            }
            BottomSheetType.REMOVE -> {
                TwoButtonBottomSheetContent(
                    title = stringResource(id = R.string.title_remove_link),
                    subText = stringResource(id = R.string.sub_remove_link),
                    onClickLeftButton = viewModel::hideLinkOptionBottomSheet,
                    onClickRightButton = remember {
                        {
                            viewModel.removeCurrentSelectedLink()
                            viewModel.hideLinkOptionBottomSheet()
                        }
                    }
                )
            }
            else -> {}
        }
    }

    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = unCategoryLinks.value,
            key = { it.id }
        ) { unCategoryDetail ->
            LinkCard(
                item = unCategoryDetail.linkType,
                title = unCategoryDetail.title,
                sub = unCategoryDetail.createdAt,
                painter = rememberAsyncImagePainter(model = unCategoryDetail.imageUrl),
                notRead = !unCategoryDetail.isRead,
                badgeText = "미분류",
                onClickKebab = {
                    viewModel.showLinkOptionBottomSheet(unCategoryDetail)
                },
                onClickItem = { }
            )
        }
    }
}

@Preview
@Composable
fun LinkCardPreview2() {
    UnclassifiedScreen()
}
