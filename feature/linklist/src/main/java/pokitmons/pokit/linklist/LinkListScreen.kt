package pokitmons.pokit.linklist

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.block.toolbar.Toolbar
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet.LinkDetailBottomSheet
import pokitmons.pokit.core.ui.components.template.pookiempty.EmptyPooki
import pokitmons.pokit.core.ui.components.template.pookierror.ErrorPooki
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.linklist.model.BottomSheetType
import pokitmons.pokit.linklist.model.Link
import pokitmons.pokit.linklist.model.LinkListScreenState
import pokitmons.pokit.core.ui.R.string as CoreString
import pokitmons.pokit.core.ui.R.drawable as CoreDrawable

@Composable
fun LinkListScreenContainer(
    viewModel: LinkListViewModel,
    onBackPressed: () -> Unit,
    onNavigateToLinkModify: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val linkList by viewModel.linkList.collectAsState()
    val linkListState by viewModel.linkListState.collectAsState()

    LinkListScreen(
        state = state,
        onBackPressed = onBackPressed,
        loadNextLinkList = viewModel::loadNextLinks,
        toggleSort = viewModel::toggleSort,
        linkList = linkList,
        linkListState = linkListState,
        showLinkDetailBottomSheet = viewModel::showLinkDetailBottomSheet,
        hideLinkDetailBottomSheet = viewModel::hideLinkDetailBottomSheet,
        showCheckLinkRemoveBottomSheet = viewModel::showCheckLinkRemoveBottomSheet,
        hideCheckLinkRemoveBottomSheet = viewModel::hideCheckLinkRemoveBottomSheet,
        onClickLinkRemove = viewModel::removeLink,
        onClickModifyLink = onNavigateToLinkModify,
        onClickBookmark = viewModel::toggleBookmark
    )

}

@Composable
fun LinkListScreen(
    state: LinkListScreenState,
    onBackPressed: () -> Unit,
    linkList: List<Link> = emptyList(),
    linkListState: PagingState = PagingState.IDLE,
    loadNextLinkList: () -> Unit,
    toggleSort: () -> Unit,
    showLinkDetailBottomSheet: (Link) -> Unit,
    hideLinkDetailBottomSheet: () -> Unit,
    showCheckLinkRemoveBottomSheet: () -> Unit,
    hideCheckLinkRemoveBottomSheet: () -> Unit,
    onClickLinkRemove: () -> Unit,
    onClickModifyLink: (String) -> Unit,
    onClickBookmark: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PokitTheme.colors.backgroundBase)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            onClickBack = onBackPressed,
            title = stringResource(id = state.type.resourceId)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.format_link_count, state.count),
                style = PokitTheme.typography.detail1.copy(color = PokitTheme.colors.textSecondary)
            )
            Row(
                modifier = Modifier
                    .noRippleClickable { toggleSort() }
                    .padding(vertical = 12.dp)
            ){
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = CoreDrawable.icon_24_align),
                    contentDescription = "change sort",
                    tint = PokitTheme.colors.iconPrimary
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(id = state.sort.titleResourceId),
                    style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.textSecondary)
                )
            }
        }

        val linkLazyColumnListState = rememberLazyListState()
        val startLinkPaging = remember {
            derivedStateOf {
                linkLazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { last ->
                    last.index >= linkLazyColumnListState.layoutInfo.totalItemsCount - 3
                } ?: false
            }
        }

        LaunchedEffect(startLinkPaging.value) {
            if (startLinkPaging.value && linkListState == PagingState.IDLE) {
                loadNextLinkList()
            }
        }

        when {
            (linkListState == PagingState.LOADING_INIT) -> {
                LoadingProgress(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            (linkListState == PagingState.FAILURE_INIT) -> {
                ErrorPooki(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(id = CoreString.title_error),
                    sub = stringResource(id = CoreString.sub_error)
                )
            }
            (linkList.isEmpty()) -> {
                EmptyPooki(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(id = CoreString.title_empty_links),
                    sub = stringResource(id = CoreString.sub_empty_links)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = linkLazyColumnListState
                ) {
                    items(
                        items = linkList,
                    ) { link ->
                        LinkCard(
                            item = link,
                            title = link.title,
                            sub = "${link.dateString} · ${link.domainUrl}",
                            painter = rememberAsyncImagePainter(link.imageUrl),
                            notRead = !link.isRead,
                            badgeText = link.pokitName,
                            onClickKebab = showLinkDetailBottomSheet,
                            onClickItem = showLinkDetailBottomSheet,
                            modifier = Modifier.padding(20.dp)
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            thickness = 1.dp,
                            color = PokitTheme.colors.borderTertiary
                        )
                    }
                }
            }
        }

        val context: Context = LocalContext.current
        val link = state.bottomSheetInfo?.link ?: Link()

        // 수정 필요
        // onHideBottomSheet 호출로 인해 후속 호출로 발생한 삭제 bottomSheet가 종료되고 있음
        LinkDetailBottomSheet(
            title = link.title,
            memo = link.memo,
            url = link.url,
            thumbnailPainter = rememberAsyncImagePainter(link.imageUrl),
            bookmark = link.bookmark,
            openWebBrowserByClick = true,
            pokitName = link.pokitName,
            dateString = link.dateString,
            onHideBottomSheet = hideLinkDetailBottomSheet,
            show = state.bottomSheetInfo?.type == BottomSheetType.DETAIL,
            onClickShareLink = {
                val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, link.url)
                }
                context.startActivity(Intent.createChooser(intent, "Pokit"))
            },
            onClickModifyLink = {
                hideLinkDetailBottomSheet()
                onClickModifyLink(link.id)
            },
            onClickRemoveLink = {
                showCheckLinkRemoveBottomSheet()
            },
            onClickBookmark = onClickBookmark
        )



        PokitBottomSheet(
            onHideBottomSheet = hideCheckLinkRemoveBottomSheet,
            show = state.bottomSheetInfo?.type == BottomSheetType.CHECK_REMOVE
        ) {
            TwoButtonBottomSheetContent(
                title = stringResource(id = R.string.title_remove_link),
                subText = stringResource(id = R.string.sub_remove_link),
                onClickLeftButton = hideCheckLinkRemoveBottomSheet,
                onClickRightButton = remember {
                    {
                        onClickLinkRemove()
                        hideCheckLinkRemoveBottomSheet()
                    }
                }
            )
        }


    }
}

