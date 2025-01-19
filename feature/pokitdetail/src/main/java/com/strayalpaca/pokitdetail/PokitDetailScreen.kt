package com.strayalpaca.pokitdetail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.strayalpaca.pokitdetail.components.block.TitleArea
import com.strayalpaca.pokitdetail.components.block.Toolbar
import com.strayalpaca.pokitdetail.components.template.filterselectbottomsheet.FilterSelectBottomSheet
import com.strayalpaca.pokitdetail.model.BottomSheetType
import pokitmons.pokit.core.feature.flow.collectAsEffect
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.utils.ShareUrlLink
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitList
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet.LinkDetailBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.pookiempty.EmptyPooki
import pokitmons.pokit.core.ui.components.template.pookierror.ErrorPooki
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as coreDrawable
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun PokitDetailScreenContainer(
    viewModel: PokitDetailViewModel,
    onBackPressed: () -> Unit,
    onNavigateToLinkModify: (String) -> Unit,
    onNavigateToPokitModify: (String) -> Unit,
    onNavigateToAddLink: (String, String) -> Unit,
) {
    viewModel.moveToBackEvent.collectAsEffect {
        onBackPressed()
    }

    PokitDetailScreen(
        onBackPressed = onBackPressed,
        onClickPokitModify = onNavigateToPokitModify,
        onClickLinkModify = onNavigateToLinkModify,
        onClickAddLink = onNavigateToAddLink,
        viewModel = viewModel
    )
}

@Composable
fun PokitDetailScreen(
    onBackPressed: () -> Unit = {},
    onClickPokitModify: (String) -> Unit = {},
    onClickLinkModify: (String) -> Unit = {},
    onClickAddLink: (String, String) -> Unit = { _, _ -> },
    viewModel: PokitDetailViewModel,
) {
    val state by viewModel.state.collectAsState()
    val linkList by viewModel.linkList.collectAsState()
    val linkListState by viewModel.linkListState.collectAsState()
    val pokitList by viewModel.pokitList.collectAsState()
    val pokitListState by viewModel.pokitListState.collectAsState()

    val uriHandler = LocalUriHandler.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Toolbar(
                onBackPressed = onBackPressed,
                onClickKebab = viewModel::showPokitModifyBottomSheet
            )

            Spacer(modifier = Modifier.height(12.dp))

            TitleArea(
                title = state.currentPokit?.title ?: "",
                sub = stringResource(id = pokitmons.pokit.core.ui.R.string.pokit_count_format, state.currentPokit?.count ?: 0),
                onClickSelectPokit = viewModel::showPokitSelectBottomSheet,
                onClickSelectFilter = viewModel::showFilterChangeBottomSheet
            )

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
                    viewModel.loadNextLinks()
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
                        title = stringResource(id = coreString.title_error),
                        sub = stringResource(id = coreString.sub_error)
                    )
                }
                (linkList.isEmpty()) -> {
                    EmptyPooki(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        title = stringResource(id = coreString.title_empty_links),
                        sub = stringResource(id = coreString.sub_empty_links)
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
                            key = { it.id }
                        ) { link ->
                            LinkCard(
                                item = link,
                                title = link.title,
                                sub = "${link.dateString} · ${link.domainUrl}",
                                painter = rememberAsyncImagePainter(link.imageUrl),
                                notRead = !link.isRead,
                                badgeText = link.pokitName,
                                onClickKebab = viewModel::showLinkDetailBottomSheet,
                                onClickItem = {
                                    uriHandler.openUri(link.url)
                                },
                                modifier = Modifier.padding(20.dp),
                                bookmark = link.bookmark
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
        }

        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 48.dp, end = 20.dp)
                .size(60.dp)
                .clip(shape = CircleShape)
                .background(color = PokitTheme.colors.brand)
                .clickable {
                    state.currentPokit?.let { currentPokit ->
                        onClickAddLink(currentPokit.id, currentPokit.title)
                    }
                }
                .padding(12.dp),
            painter = painterResource(id = coreDrawable.icon_24_plus),
            contentDescription = "add link",
            colorFilter = ColorFilter.tint(color = PokitTheme.colors.inverseWh)
        )

        state.currentLink?.let { currentLink ->
            val context: Context = LocalContext.current
            LinkDetailBottomSheet(
                title = currentLink.title,
                memo = currentLink.memo,
                bookmark = currentLink.bookmark,
                pokitName = currentLink.pokitName,
                dateString = currentLink.dateString,
                onHideBottomSheet = viewModel::hideLinkDetailBottomSheet,
                show = state.linkDetailBottomSheetVisible,
                onClickShareLink = {
                    ShareUrlLink(
                        context = context,
                        url = currentLink.url
                    )
                },
                onClickModifyLink = {
                    viewModel.hideLinkDetailBottomSheet()
                    onClickLinkModify(currentLink.id)
                },
                onClickRemoveLink = {
                    viewModel.hideLinkDetailBottomSheet()
                    viewModel.showLinkRemoveBottomSheet(currentLink)
                },
                onClickBookmark = {
                    viewModel.toggleBookmark(currentLink)
                }
            )
        }

        FilterSelectBottomSheet(
            filter = state.currentFilter,
            onHideRequest = viewModel::hideFilterChangeBottomSheet,
            onFilterChange = viewModel::changeFilter,
            show = state.filterChangeBottomSheetVisible
        )

        PokitBottomSheet(
            onHideBottomSheet = viewModel::hidePokitSelectBottomSheet,
            show = state.pokitSelectBottomSheetVisible,
            skipPartiallyExpanded = false
        ) {
            val lazyColumnListState = rememberLazyListState()
            val startPaging = remember {
                derivedStateOf {
                    lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { last ->
                        last.index >= lazyColumnListState.layoutInfo.totalItemsCount - 3
                    } ?: false
                }
            }

            LaunchedEffect(Unit) {
                viewModel.refreshPokits()
            }

            LaunchedEffect(startPaging.value) {
                if (startPaging.value && pokitListState == PagingState.IDLE) {
                    viewModel.loadNextPokits()
                }
            }

            LazyColumn(
                state = lazyColumnListState
            ) {
                items(
                    items = pokitList,
                    key = { it.id }
                ) { pokit ->
                    PokitList(
                        item = pokit,
                        title = pokit.title,
                        sub = stringResource(id = R.string.link_count_format, pokit.count),
                        onClickItem = viewModel::changePokit,
                        state = PokitListState.ACTIVE
                    )
                }
            }
        }

        PokitBottomSheet(
            onHideBottomSheet = viewModel::hideLinkBottomSheet,
            show = state.linkBottomSheetType != null
        ) {
            val context: Context = LocalContext.current
            when (state.linkBottomSheetType) {
                BottomSheetType.MODIFY -> {
                    ModifyBottomSheetContent(
                        onClickShare = {
                            ShareUrlLink(
                                context = context,
                                url = state.currentLink?.url ?: ""
                            )
                        },
                        onClickModify = remember {
                            {
                                state.currentLink?.let { link ->
                                    viewModel.hideLinkBottomSheet()
                                    onClickLinkModify(link.id)
                                }
                            }
                        },
                        onClickRemove = viewModel::showLinkRemoveBottomSheet
                    )
                }

                BottomSheetType.REMOVE -> {
                    TwoButtonBottomSheetContent(
                        title = stringResource(id = R.string.title_remove_link),
                        subText = stringResource(id = R.string.sub_remove_link),
                        onClickLeftButton = viewModel::hideLinkBottomSheet,
                        onClickRightButton = {
                            state.currentLink?.let { currentLink ->
                                viewModel.deleteLink(currentLink)
                                viewModel.hideLinkBottomSheet()
                            }
                        }
                    )
                }

                else -> {}
            }
        }

        PokitBottomSheet(
            onHideBottomSheet = viewModel::hidePokitBottomSheet,
            show = state.pokitBottomSheetType != null
        ) {
            when (state.pokitBottomSheetType) {
                BottomSheetType.MODIFY -> {
                    val context: Context = LocalContext.current
                    ModifyBottomSheetContent(
                        onClickShare = {
                            ShareUrlLink(
                                context = context,
                                url = state.currentLink?.url ?: ""
                            )
                        },
                        onClickModify = remember {
                            {
                                viewModel.hidePokitBottomSheet()
                                onClickPokitModify(state.currentPokit!!.id)
                            }
                        },
                        onClickRemove = viewModel::showPokitRemoveBottomSheet
                    )
                }

                BottomSheetType.REMOVE -> {
                    TwoButtonBottomSheetContent(
                        title = stringResource(id = R.string.title_remove_pokit),
                        subText = stringResource(id = R.string.sub_remove_pokit),
                        onClickLeftButton = viewModel::hidePokitBottomSheet,
                        onClickRightButton = remember {
                            {
                                viewModel.deletePokit(state.currentPokit!!)
                                viewModel.hidePokitBottomSheet()
                            }
                        }
                    )
                }

                else -> {}
            }
        }
    }
}
