package pokitmons.pokit.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet.LinkDetailBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.components.template.pokkiempty.EmptyPokki
import pokitmons.pokit.core.ui.components.template.pokkierror.ErrorPokki
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.components.filter.FilterArea
import pokitmons.pokit.search.components.filterbottomsheet.FilterBottomSheet
import pokitmons.pokit.search.components.recentsearchword.RecentSearchWord
import pokitmons.pokit.search.components.searchitemlist.SearchItemList
import pokitmons.pokit.search.components.toolbar.Toolbar
import pokitmons.pokit.search.model.BottomSheetType
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.model.SearchScreenState
import pokitmons.pokit.search.model.SearchScreenStep
import pokitmons.pokit.search.paging.SimplePagingState
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun SearchScreenContainer(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit,
    onNavigateToLinkModify: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val searchWord by viewModel.searchWord.collectAsState()
    val linkList by viewModel.linkList.collectAsState()
    val linkPagingState by viewModel.linkPagingState.collectAsState()
    val pokitList by viewModel.pokitList.collectAsState()
    val pokitPagingState by viewModel.pokitPagingState.collectAsState()

    state.currentLink?.let { link ->
        LinkDetailBottomSheet(
            title = link.title,
            memo = link.memo,
            url = link.url,
            thumbnailPainter = rememberAsyncImagePainter(link.imageUrl),
            bookmark = link.bookmark,
            openWebBrowserByClick = true,
            linkType = stringResource(id = link.linkType.textResourceId),
            dateString = link.dateString,
            onHideBottomSheet = viewModel::hideLinkDetailBottomSheet,
            show = state.showLinkDetailBottomSheet,
            onClickModifyLink = {
                viewModel.hideLinkDetailBottomSheet()
                onNavigateToLinkModify(link.id)
            },
            onClickRemoveLink = {
                viewModel.showLinkRemoveBottomSheet(link)
            },
            onClickBookmark = viewModel::toggleBookmark
        )
    }

    FilterBottomSheet(
        filter = state.filter ?: Filter(),
        firstShowType = state.firstBottomSheetFilterType,
        show = state.showFilterBottomSheet,
        onDismissRequest = viewModel::hideFilterBottomSheet,
        onSaveClilck = viewModel::setFilter,
        pokits = pokitList,
        pokitPagingState = pokitPagingState,
        loadNextPokits = viewModel::loadNextPokits,
        refreshPokits = viewModel::refreshPokits
    )

    PokitBottomSheet(
        onHideBottomSheet = viewModel::hideLinkModifyBottomSheet,
        show = state.linkBottomSheetType != null
    ) {
        if (state.linkBottomSheetType == BottomSheetType.MODIFY) {
            ModifyBottomSheetContent(
                onClickModify = remember {
                    {
                        state.currentLink?.let { link ->
                            viewModel.hideLinkModifyBottomSheet()
                            onNavigateToLinkModify(link.id)
                        }
                    }
                },
                onClickRemove = remember {
                    {
                        state.currentLink?.let { link ->
                            viewModel.showLinkRemoveBottomSheet(link)
                        }
                    }
                },
                onClickShare = remember {
                    {
                    }
                }
            )
        }

        if (state.linkBottomSheetType == BottomSheetType.REMOVE) {
            TwoButtonBottomSheetContent(
                title = stringResource(id = R.string.title_remove_link),
                subText = stringResource(id = R.string.sub_remove_link),
                onClickLeftButton = viewModel::hideLinkModifyBottomSheet,
                onClickRightButton = {
                    viewModel.deleteLink()
                    viewModel.hideLinkModifyBottomSheet()
                }
            )
        }
    }

    SearchScreen(
        state = state,
        currentSearchWord = searchWord,
        linkList = linkList,
        linkPagingState = linkPagingState,
        onClickBack = onBackPressed,
        inputSearchWord = viewModel::inputSearchWord,
        onClickSearch = viewModel::applyCurrentSearchWord,
        onClickRecentSearchWord = viewModel::applySearchWord,
        onClickUseRecentSearchWord = viewModel::toggleUseRecentSearchWord,
        onClickRemoveAllRecentSearchWord = viewModel::removeAllRecentSearchWord,
        onClickRemoveRecentSearchWord = viewModel::removeRecentSearchWord,
        onClickFilterSelect = viewModel::showFilterBottomSheet,
        onClickFilterItem = viewModel::showFilterBottomSheetWithType,
        toggleSortOrder = viewModel::toggleSortOrder,
        showLinkModifyBottomSheet = viewModel::showLinkModifyBottomSheet,
        showLinkDetailBottomSheet = viewModel::showLinkDetailBottomSheet,
        loadNextLinks = viewModel::loadNextLinks
    )
}

@Composable
fun SearchScreen(
    state: SearchScreenState = SearchScreenState(),
    currentSearchWord: String = "",
    linkList: List<Link> = emptyList(),
    linkPagingState: SimplePagingState = SimplePagingState.IDLE,
    onClickBack: () -> Unit = {},
    inputSearchWord: (String) -> Unit = {},
    onClickSearch: () -> Unit = {},
    onClickRecentSearchWord: (String) -> Unit = {},
    onClickUseRecentSearchWord: () -> Unit = {},
    onClickRemoveAllRecentSearchWord: () -> Unit = {},
    onClickRemoveRecentSearchWord: (String) -> Unit = {},
    onClickFilterSelect: () -> Unit = {},
    onClickFilterItem: (FilterType) -> Unit = {},
    toggleSortOrder: () -> Unit = {},
    showLinkModifyBottomSheet: (Link) -> Unit = {},
    showLinkDetailBottomSheet: (Link) -> Unit = {},
    loadNextLinks: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PokitTheme.colors.backgroundBase)
    ) {
        Toolbar(
            onClickBack = onClickBack,
            inputSearchWord = inputSearchWord,
            currentSearchWord = currentSearchWord,
            onClickSearch = onClickSearch,
            onClickRemove = remember { { inputSearchWord("") } }
        )

        if (state.step == SearchScreenStep.INPUT) {
            RecentSearchWord(
                onClickRemoveAll = onClickRemoveAllRecentSearchWord,
                onToggleAutoSave = onClickUseRecentSearchWord,
                useAutoSave = state.useRecentSearchWord,
                recentSearchWords = state.recentSearchWords,
                onClickRemoveSearchWord = onClickRemoveRecentSearchWord,
                onClickSearchWord = onClickRecentSearchWord
            )
        }

        if (state.step == SearchScreenStep.RESULT) {
            FilterArea(
                filter = state.filter,
                onClickFilter = onClickFilterSelect,
                onClickBookmark = remember { { onClickFilterItem(FilterType.Collect) } },
                onClickPokitName = remember { { onClickFilterItem(FilterType.Pokit) } },
                onClickPeriod = remember { { onClickFilterItem(FilterType.Period) } }
            )
        }

        HorizontalDivider(
            thickness = 6.dp,
            color = PokitTheme.colors.backgroundPrimary
        )

        if (state.step == SearchScreenStep.RESULT) {
            when {
                (linkPagingState == SimplePagingState.LOADING_INIT) -> {
                    LoadingProgress(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                (linkPagingState == SimplePagingState.FAILURE_INIT) -> {
                    ErrorPokki(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        title = stringResource(id = coreString.title_error),
                        sub = stringResource(id = coreString.sub_error),
                        onClickRetry = onClickSearch
                    )
                }
                (linkList.isEmpty()) -> {
                    EmptyPokki(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        title = stringResource(id = coreString.title_empty_search),
                        sub = stringResource(id = coreString.sub_empty_search)
                    )
                }
                else -> {
                    SearchItemList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onToggleSort = toggleSortOrder,
                        useRecentOrder = state.sortRecent,
                        onClickLinkKebab = showLinkModifyBottomSheet,
                        onClickLink = showLinkDetailBottomSheet,
                        links = linkList,
                        linkPagingState = linkPagingState,
                        loadNextLinks = loadNextLinks
                    )
                }
            }
        }
    }
}
