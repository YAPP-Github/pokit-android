package pokitmons.pokit.home.remind

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.strayalpaca.pokitdetail.R
import com.strayalpaca.pokitdetail.model.BottomSheetType
import pokitmons.pokit.core.feature.model.NetworkState
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet.LinkDetailBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.pookiempty.EmptyPooki
import pokitmons.pokit.core.ui.components.template.pookierror.ErrorPooki
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun RemindScreen(
    modifier: Modifier = Modifier,
    viewModel: RemindViewModel = hiltViewModel(),
    onNavigateToLinkModify: (String) -> Unit,
) {
    val unreadContents = viewModel.unReadContents.collectAsState()
    val unreadContentsState by viewModel.unreadContentNetworkState.collectAsState()

    val todayContents = viewModel.todayContents.collectAsState()
    val todayContentsState by viewModel.todayContentsNetworkState.collectAsState()

    val bookmarkContents = viewModel.bookmarkContents.collectAsState()
    val bookmarkContentState by viewModel.bookmarkContentsNetworkState.collectAsState()

    val currentDetailShowLink by viewModel.currentShowingLink.collectAsState()

    val pokitOptionBottomSheetType by viewModel.pokitOptionBottomSheetType.collectAsState()
    val currentSelectedLink by viewModel.currentSelectedLink.collectAsState()

    val context: Context = LocalContext.current

    val showTotalEmpty by remember {
        derivedStateOf {
            todayContentsState == NetworkState.IDLE &&
                todayContents.value.size < 5 &&
                unreadContentsState == NetworkState.IDLE &&
                unreadContents.value.isEmpty()
        }
    }

    PokitBottomSheet(
        onHideBottomSheet = viewModel::hideLinkOptionBottomSheet,
        show = pokitOptionBottomSheetType != null
    ) {
        when (pokitOptionBottomSheetType) {
            BottomSheetType.MODIFY -> {
                ModifyBottomSheetContent(
                    onClickShare = { Toast.makeText(context, "준비중입니다.", Toast.LENGTH_SHORT).show() },
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
                            viewModel.hideLinkOptionBottomSheet()
                            viewModel.removeCurrentSelectedLink()
                        }
                    }
                )
            }
            else -> {}
        }
    }

    currentDetailShowLink?.let { link ->
        LinkDetailBottomSheet(
            title = link.title,
            memo = link.memo,
            url = link.url,
            thumbnailPainter = rememberAsyncImagePainter(model = link.imageUrl),
            bookmark = link.bookmark,
            openWebBrowserByClick = true,
            pokitName = link.pokitName,
            dateString = link.dateString,
            onHideBottomSheet = viewModel::hideDetailLinkBottomSheet,
            show = true,
            onClickModifyLink = {
                viewModel.hideDetailLinkBottomSheet()
                onNavigateToLinkModify(link.id)
            },
            onClickRemoveLink = {
                viewModel.hideDetailLinkBottomSheet()
                viewModel.showLinkRemoveBottomSheet(link)
            },
            onClickBookmark = viewModel::toggleBookmark
        )
    }

    if (showTotalEmpty) {
        ErrorPooki(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp),
            title = stringResource(id = coreString.title_lack_of_links),
            sub = stringResource(id = coreString.sub_lack_of_links)
        )
    } else {
        Column(
            modifier = modifier
                .background(PokitTheme.colors.backgroundBase)
                .padding(20.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            RemindSection(title = "오늘 이 링크는 어때요?") {
                Spacer(modifier = Modifier.height(12.dp))

                when (todayContentsState) {
                    NetworkState.IDLE -> {
                        if (todayContents.value.isEmpty()) {
                            ErrorPooki(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(208.dp),
                                pookiSize = 140.dp,
                                title = stringResource(id = coreString.title_lack_of_links),
                                sub = stringResource(id = coreString.sub_lack_of_links)
                            )
                        } else {
                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                todayContents.value.forEach { todayContent ->
                                    ToadyLinkCard(
                                        title = todayContent.title,
                                        sub = todayContent.createdAt,
                                        painter = rememberAsyncImagePainter(todayContent.thumbNail),
                                        badgeText = null,
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
                    }
                    NetworkState.LOADING -> {
                        LoadingProgress(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(208.dp)
                        )
                    }
                    NetworkState.ERROR -> {
                        ErrorPooki(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(208.dp),
                            pookiSize = 140.dp,
                            title = stringResource(id = coreString.title_error),
                            sub = stringResource(id = coreString.sub_error)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if ((unreadContentsState == NetworkState.IDLE && unreadContents.value.isNotEmpty())) {
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
                                notRead = !unReadContent.isRead,
                                badgeText = null,
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
            }

            RemindSection(title = "즐겨찾기 링크만 모았어요") {
                Spacer(modifier = Modifier.height(12.dp))

                when (bookmarkContentState) {
                    NetworkState.IDLE -> {
                        if (bookmarkContents.value.isEmpty()) {
                            EmptyPooki(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(252.dp),
                                title = stringResource(id = coreString.title_empty_favorite),
                                sub = stringResource(id = coreString.sub_empty_favorite)
                            )
                        } else {
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
                                        badgeText = null,
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
                    NetworkState.LOADING -> {
                        LoadingProgress(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(252.dp)
                        )
                    }
                    NetworkState.ERROR -> {
                        ErrorPooki(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(252.dp),
                            pookiSize = 140.dp,
                            title = stringResource(id = coreString.title_empty_favorite),
                            sub = stringResource(id = coreString.sub_empty_favorite)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
