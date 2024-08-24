package pokitmons.pokit.home.remind

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.strayalpaca.pokitdetail.R
import com.strayalpaca.pokitdetail.components.template.linkdetailbottomsheet.LinkDetailBottomSheet
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Link
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent

@Composable
fun RemindScreen(
    modifier: Modifier = Modifier,
    viewModel: RemindViewModel = hiltViewModel(),
    onNavigateToLinkModify: (String) -> Unit,
) {
    val unreadContents = viewModel.unReadContents.collectAsState()
    val todayContents = viewModel.todayContents.collectAsState()
    val bookmarkContents = viewModel.bookmarkContents.collectAsState()

    val currentDetailShowLink by viewModel.currentShowingLink.collectAsState()

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
                    title = stringResource(id = R.string.title_remove_pokit),
                    subText = stringResource(id = R.string.sub_remove_pokit),
                    onClickLeftButton = viewModel::hideLinkOptionBottomSheet,
                    onClickRightButton = remember {
                        {
                            viewModel.hideLinkOptionBottomSheet()
                            viewModel.removeCurrentSelectedLink()
                        }
                    }
                )
            }
            else -> {}
        }
    }

    LinkDetailBottomSheet(
        show = currentDetailShowLink != null,
        link = currentDetailShowLink ?: Link(),
        onHideBottomSheet = viewModel::hideDetailLinkBottomSheet
    )

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        RemindSection(title = "오늘 이 링크는 어때요?") {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                todayContents.value.forEach { todayContent ->
                    ToadyLinkCard(
                        title = todayContent.title,
                        sub = todayContent.createdAt,
                        painter = rememberAsyncImagePainter(todayContent.thumbNail),
                        badgeText = todayContent.data,
                        domain = todayContent.domain,
                        onClick = {
                            viewModel.showDetailLinkBottomSheet(remindResult = todayContent)
                        },
                        onClickKebab = {
                            viewModel.showLinkOptionBottomSheet(remindResult = todayContent)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        RemindSection(title = "한번도 읽지 않았어요") {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                unreadContents.value.forEach { unReadContent ->
                    LinkCard(
                        item = unReadContent.title,
                        title = unReadContent.title,
                        sub = "${unReadContent.createdAt} • ${unReadContent.domain}",
                        painter = rememberAsyncImagePainter(unReadContent.thumbNail),
                        notRead = unReadContent.isRead,
                        badgeText = unReadContent.data,
                        onClickKebab = {
                            viewModel.showLinkOptionBottomSheet(remindResult = unReadContent)
                        },
                        onClickItem = {
                            viewModel.showDetailLinkBottomSheet(remindResult = unReadContent)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        RemindSection(title = "즐겨찾기 링크만 모았어요") {
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                bookmarkContents.value.forEach { favoriteContent ->
                    LinkCard(
                        item = favoriteContent.title,
                        title = favoriteContent.title,
                        sub = "${favoriteContent.createdAt} • ${favoriteContent.domain}",
                        painter = rememberAsyncImagePainter(favoriteContent.thumbNail),
                        notRead = favoriteContent.isRead,
                        badgeText = favoriteContent.data,
                        onClickKebab = {
                            viewModel.showLinkOptionBottomSheet(remindResult = favoriteContent)
                        },
                        onClickItem = {
                            viewModel.showDetailLinkBottomSheet(remindResult = favoriteContent)
                        }
                    )
                }
            }
        }
    }
}
