package pokitmons.pokit.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.modifybottomsheet.ModifyBottomSheetContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.components.filter.FilterArea
import pokitmons.pokit.search.components.filterbottomsheet.FilterBottomSheet
import pokitmons.pokit.search.components.recentsearchword.RecentSearchWord
import pokitmons.pokit.search.components.searchitemlist.SearchItemList
import pokitmons.pokit.search.components.toolbar.Toolbar
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.SearchScreenState
import pokitmons.pokit.search.model.SearchScreenStep
import pokitmons.pokit.search.paging.SimplePagingState

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
        hideBottomSheet = viewModel::hideFilterBottomSheet,
        onClickFilterSave = viewModel::setFilter,
        toggleSortOrder = viewModel::toggleSortOrder,
        showLinkModifyBottomSheet = viewModel::showLinkModifyBottomSheet,
        hideLinkModifyBottomSheet = viewModel::hideLinkModifyBottomSheet,
        onClickLinkModify = onNavigateToLinkModify,
        pokitList = pokitList,
        pokitPagingState = pokitPagingState,
        loadNextLinks = viewModel::loadNextLinks,
        loadNextPokits = viewModel::loadNextPokits,
        refreshPokits = viewModel::refreshPokits
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
    hideBottomSheet: () -> Unit = {},
    onClickFilterSave: (Filter) -> Unit = {},
    toggleSortOrder: () -> Unit = {},
    showLinkModifyBottomSheet: (Link) -> Unit = {},
    hideLinkModifyBottomSheet: () -> Unit = {},
    onClickLinkModify: (String) -> Unit = {},
    loadNextLinks: () -> Unit = {},
    pokitList: List<Pokit> = emptyList(),
    pokitPagingState: SimplePagingState = SimplePagingState.IDLE,
    loadNextPokits: () -> Unit = {},
    refreshPokits: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
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
            SearchItemList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onToggleSort = toggleSortOrder,
                useRecentOrder = state.sortRecent,
                onClickLinkKebab = showLinkModifyBottomSheet,
                links = linkList,
                linkPagingState = linkPagingState,
                loadNextLinks = loadNextLinks
            )
        }

        FilterBottomSheet(
            filter = state.filter ?: Filter(),
            firstShowType = state.firstBottomSheetFilterType,
            show = state.showFilterBottomSheet,
            onDismissRequest = hideBottomSheet,
            onSaveClilck = onClickFilterSave,
            pokits = pokitList,
            pokitPagingState = pokitPagingState,
            loadNextPokits = loadNextPokits,
            refreshPokits = refreshPokits
        )

        PokitBottomSheet(
            onHideBottomSheet = hideLinkModifyBottomSheet,
            show = state.currentLink != null
        ) {
            ModifyBottomSheetContent(
                onClickModify = remember {
                    {
                        state.currentLink?.let { link ->
                            hideLinkModifyBottomSheet()
                            onClickLinkModify(link.id)
                        }
                    }
                }
            )
        }
    }
}
