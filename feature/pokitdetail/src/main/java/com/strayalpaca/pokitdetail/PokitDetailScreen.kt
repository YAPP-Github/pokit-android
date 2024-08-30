package com.strayalpaca.pokitdetail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.strayalpaca.pokitdetail.components.block.TitleArea
import com.strayalpaca.pokitdetail.components.block.Toolbar
import com.strayalpaca.pokitdetail.components.template.filterselectbottomsheet.FilterSelectBottomSheet
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Filter
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.model.PokitDetailScreenState
import com.strayalpaca.pokitdetail.paging.SimplePagingState
import pokitmons.pokit.core.feature.flow.collectAsEffect
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.block.linkcard.LinkCard
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitList
import pokitmons.pokit.core.ui.components.block.pokitlist.attributes.PokitListState
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet.LinkDetailBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.pokkiempty.EmptyPokki
import pokitmons.pokit.core.ui.components.template.pokkierror.ErrorPokki
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun PokitDetailScreenContainer(
    viewModel: PokitDetailViewModel,
    onBackPressed: () -> Unit,
    onNavigateToLinkModify: (String) -> Unit,
    onNavigateToPokitModify: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val linkList by viewModel.linkList.collectAsState()
    val linkListState by viewModel.linkListState.collectAsState()
    val pokitList by viewModel.pokitList.collectAsState()
    val pokitListState by viewModel.pokitListState.collectAsState()

    viewModel.moveToBackEvent.collectAsEffect {
        onBackPressed()
    }

    PokitDetailScreen(
        onBackPressed = onBackPressed,
        onClickFilter = viewModel::showFilterChangeBottomSheet,
        hideFilterChangeBottomSheet = viewModel::hideFilterChangeBottomSheet,
        changeFilter = viewModel::changeFilter,
        showPokitSelectBottomSheet = viewModel::showPokitSelectBottomSheet,
        hidePokitSelectBottomSheet = viewModel::hidePokitSelectBottomSheet,
        changePokit = viewModel::changePokit,
        showPokitModifyBottomSheet = viewModel::showPokitModifyBottomSheet,
        showPokitRemoveBottomSheet = viewModel::showPokitRemoveBottomSheet,
        hidePokitModifyBottomSheet = viewModel::hidePokitBottomSheet,
        showLinkModifyBottomSheet = viewModel::showLinkModifyBottomSheet,
        showLinkRemoveBottomSheet = viewModel::showLinkRemoveBottomSheet,
        hideLinkModifyBottomSheet = viewModel::hideLinkBottomSheet,
        hideLinkDetailBottomSheet = viewModel::hideLinkDetailBottomSheet,
        state = state,
        linkList = linkList,
        linkListState = linkListState,
        pokitList = pokitList,
        pokitListState = pokitListState,
        onClickLink = viewModel::showLinkDetailBottomSheet,
        onClickPokitModify = onNavigateToPokitModify,
        onClickPokitRemove = viewModel::deletePokit,
        onClickLinkModify = onNavigateToLinkModify,
        onClickLinkRemove = viewModel::deleteLink,
        loadNextPokits = viewModel::loadNextPokits,
        refreshPokits = viewModel::refreshPokits,
        loadNextLinks = viewModel::loadNextLinks
    )
}

@Composable
fun PokitDetailScreen(
    onBackPressed: () -> Unit = {},
    onClickFilter: () -> Unit = {},
    hideFilterChangeBottomSheet: () -> Unit = {},
    changeFilter: (Filter) -> Unit = {},
    showPokitSelectBottomSheet: () -> Unit = {},
    hidePokitSelectBottomSheet: () -> Unit = {},
    changePokit: (Pokit) -> Unit = {},
    showPokitModifyBottomSheet: () -> Unit = {},
    showPokitRemoveBottomSheet: () -> Unit = {},
    hidePokitModifyBottomSheet: () -> Unit = {},
    showLinkModifyBottomSheet: (Link) -> Unit = {},
    showLinkRemoveBottomSheet: () -> Unit = {},
    hideLinkModifyBottomSheet: () -> Unit = {},
    hideLinkDetailBottomSheet: () -> Unit = {},
    state: PokitDetailScreenState = PokitDetailScreenState(),
    linkList: List<Link> = emptyList(),
    linkListState: SimplePagingState = SimplePagingState.IDLE,
    pokitList: List<Pokit> = emptyList(),
    pokitListState: SimplePagingState = SimplePagingState.IDLE,
    onClickLink: (Link) -> Unit = {},
    onClickPokitModify: (String) -> Unit = {},
    onClickPokitRemove: () -> Unit = {},
    onClickLinkModify: (String) -> Unit = {},
    onClickLinkRemove: () -> Unit = {},
    loadNextPokits: () -> Unit = {},
    refreshPokits: () -> Unit = {},
    loadNextLinks: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(
            onBackPressed = onBackPressed,
            onClickKebab = showPokitModifyBottomSheet
        )

        Spacer(modifier = Modifier.height(12.dp))

        TitleArea(
            title = state.currentPokit?.title ?: "",
            sub = stringResource(id = pokitmons.pokit.core.ui.R.string.pokit_count_format, state.currentPokit?.count ?: 0),
            onClickSelectPokit = showPokitSelectBottomSheet,
            onClickSelectFilter = onClickFilter
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
            if (startLinkPaging.value && linkListState == SimplePagingState.IDLE) {
                loadNextLinks()
            }
        }

        when {
            (linkListState == SimplePagingState.LOADING_INIT) -> {
                LoadingProgress(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            (linkListState == SimplePagingState.FAILURE_INIT) -> {
                ErrorPokki(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(id = coreString.title_error),
                    sub = stringResource(id = coreString.sub_error)
                )
            }
            (linkList.isEmpty()) -> {
                EmptyPokki(
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
                            notRead = link.isRead,
                            badgeText = stringResource(id = link.linkType.textResourceId),
                            onClickKebab = showLinkModifyBottomSheet,
                            onClickItem = onClickLink,
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

        if (state.currentLink != null) {
            val context: Context = LocalContext.current
            LinkDetailBottomSheet(
                title = state.currentLink.title,
                memo = state.currentLink.memo,
                url = state.currentLink.url,
                thumbnailPainter = rememberAsyncImagePainter(state.currentLink.imageUrl),
                bookmark = state.currentLink.bookmark,
                openWebBrowserByClick = true,
                linkType = stringResource(state.currentLink.linkType.textResourceId),
                dateString = state.currentLink.dateString,
                onHideBottomSheet = hideLinkDetailBottomSheet,
                show = state.linkDetailBottomSheetVisible,
                onClickShareLink = {
                    val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, state.currentLink.url)
                    }
                    context.startActivity(Intent.createChooser(intent, "Pokit"))
                },
                onClickModifyLink = {

                },
                onClickRemoveLink = {

                },
            )
        }

        FilterSelectBottomSheet(
            filter = state.currentFilter,
            onHideRequest = hideFilterChangeBottomSheet,
            onFilterChange = changeFilter,
            show = state.filterChangeBottomSheetVisible
        )

        PokitBottomSheet(
            onHideBottomSheet = hidePokitSelectBottomSheet,
            show = state.pokitSelectBottomSheetVisible
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
                refreshPokits()
            }

            LaunchedEffect(startPaging.value) {
                if (startPaging.value && pokitListState == SimplePagingState.IDLE) {
                    loadNextPokits()
                }
            }

            LazyColumn(
                state = lazyColumnListState
            ) {
                items(
                    items = pokitList
                ) { pokit ->
                    PokitList(
                        item = pokit,
                        title = pokit.title,
                        sub = stringResource(id = R.string.link_count_format, pokit.count),
                        onClickItem = changePokit,
                        state = PokitListState.ACTIVE
                    )
                }
            }
        }

        PokitBottomSheet(
            onHideBottomSheet = hideLinkModifyBottomSheet,
            show = state.linkBottomSheetType != null
        ) {
            val context: Context = LocalContext.current
            when (state.linkBottomSheetType) {
                BottomSheetType.MODIFY -> {
                    ModifyBottomSheetContent(
                        onClickShare = { Toast.makeText(context, "준비중입니다.", Toast.LENGTH_SHORT).show() },
                        onClickModify = remember {
                            {
                                state.currentLink?.let { link ->
                                    hideLinkModifyBottomSheet()
                                    onClickLinkModify(link.id)
                                }
                            }
                        },
                        onClickRemove = showLinkRemoveBottomSheet
                    )
                }

                BottomSheetType.REMOVE -> {
                    TwoButtonBottomSheetContent(
                        title = stringResource(id = R.string.title_remove_link),
                        subText = stringResource(id = R.string.sub_remove_link),
                        onClickLeftButton = hideLinkModifyBottomSheet,
                        onClickRightButton = {
                            onClickLinkRemove()
                            hideLinkModifyBottomSheet()
                        }
                    )
                }

                else -> {}
            }
        }

        PokitBottomSheet(
            onHideBottomSheet = hidePokitModifyBottomSheet,
            show = state.pokitBottomSheetType != null
        ) {
            when (state.pokitBottomSheetType) {
                BottomSheetType.MODIFY -> {
                    val context: Context = LocalContext.current
                    ModifyBottomSheetContent(
                        onClickShare = { Toast.makeText(context, "준비중입니다.", Toast.LENGTH_SHORT).show() },
                        onClickModify = remember {
                            {
                                hidePokitModifyBottomSheet()
                                onClickPokitModify(state.currentPokit!!.id)
                            }
                        },
                        onClickRemove = showPokitRemoveBottomSheet
                    )
                }

                BottomSheetType.REMOVE -> {
                    TwoButtonBottomSheetContent(
                        title = stringResource(id = R.string.title_remove_pokit),
                        subText = stringResource(id = R.string.sub_remove_pokit),
                        onClickLeftButton = hidePokitModifyBottomSheet,
                        onClickRightButton = remember {
                            {
                                onClickPokitRemove()
                                hidePokitModifyBottomSheet()
                            }
                        }
                    )
                }

                else -> {}
            }
        }
    }
}
