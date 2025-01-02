package pokitmons.pokit.search

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.utils.ShareUrlLink
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.linkdetailbottomsheet.LinkDetailBottomSheetContent
import pokitmons.pokit.core.ui.components.template.pookiempty.EmptyPooki
import pokitmons.pokit.core.ui.components.template.pookierror.ErrorPooki
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.components.filter.FilterArea
import pokitmons.pokit.search.components.filterbottomsheet.FilterBottomSheet
import pokitmons.pokit.search.components.recentsearchword.RecentSearchWord
import pokitmons.pokit.search.components.searchitemlist.SearchItemList
import pokitmons.pokit.search.components.toolbar.Toolbar
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.LinkBottomSheetState
import pokitmons.pokit.search.model.SearchScreenState
import pokitmons.pokit.search.model.SearchScreenStep
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun SearchScreenContainer(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit,
    onNavigateToLinkModify: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val pokitList by viewModel.pokitList.collectAsState()
    val pokitPagingState by viewModel.pokitPagingState.collectAsState()

    val context: Context = LocalContext.current

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
        onHideBottomSheet = viewModel::hideLinkBottomSheet,
        show = state.linkBottomSheetType != null
    ) {
        state.linkBottomSheetType?.let { linkBottomSheetState ->
            when (linkBottomSheetState) {
                is LinkBottomSheetState.CheckRemove -> {
                    TwoButtonBottomSheetContent(
                        title = stringResource(id = R.string.title_remove_link),
                        subText = stringResource(id = R.string.sub_remove_link),
                        onClickLeftButton = viewModel::hideLinkBottomSheet,
                        onClickRightButton = {
                            viewModel.deleteLink(linkBottomSheetState.link)
                            viewModel.hideLinkBottomSheet()
                        }
                    )
                }
                is LinkBottomSheetState.LinkDetail -> {
                    LinkDetailBottomSheetContent(
                        title = linkBottomSheetState.link.title,
                        memo = linkBottomSheetState.link.memo,
                        bookmark = linkBottomSheetState.link.bookmark,
                        pokitName = linkBottomSheetState.link.pokitName,
                        dateString = linkBottomSheetState.link.dateString,
                        onClickShareLink = {
                            ShareUrlLink(
                                context = context,
                                url = linkBottomSheetState.link.url
                            )
                        },
                        onClickModifyLink = {
                            viewModel.hideLinkBottomSheet()
                            onNavigateToLinkModify(linkBottomSheetState.link.id)
                        },
                        onClickRemoveLink = {
                            viewModel.showLinkRemoveBottomSheet(linkBottomSheetState.link)
                        },
                        onClickBookmark = { viewModel.toggleBookmark(linkBottomSheetState.link) }
                    )
                }
            }
        }
    }

    SearchScreen(
        state = state,
        onClickBack = onBackPressed,
        viewModel = viewModel
    )
}

@Composable
fun SearchScreen(
    state: SearchScreenState = SearchScreenState(),
    onClickBack: () -> Unit = {},
    viewModel: SearchViewModel,
) {
    val uriHandler = LocalUriHandler.current
    val linkList by viewModel.linkList.collectAsState()
    val linkPagingState by viewModel.linkPagingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PokitTheme.colors.backgroundBase)
    ) {
        Toolbar(
            onClickBack = onClickBack,
            inputSearchWord = viewModel::inputSearchWord,
            currentSearchWord = state.searchWord,
            onClickSearch = viewModel::searchByCurrentSearchWord,
            onClickRemove = remember { { viewModel.inputSearchWord("") } }
        )

        if (state.step == SearchScreenStep.INPUT) {
            RecentSearchWord(
                onClickRemoveAll = viewModel::removeAllRecentSearchWord,
                onToggleAutoSave = viewModel::toggleUseRecentSearchWord,
                useAutoSave = state.useRecentSearchWord,
                recentSearchWords = state.recentSearchWords,
                onClickRemoveSearchWord = viewModel::removeRecentSearchWord,
                onClickSearchWord = viewModel::inputSearchWordThenSearch
            )
        }

        if (state.step == SearchScreenStep.RESULT) {
            FilterArea(
                filter = state.filter,
                onClickFilter = viewModel::showFilterBottomSheet,
                onClickBookmark = remember { { viewModel.showFilterBottomSheetWithType(FilterType.Collect) } },
                onClickPokitName = remember { { viewModel.showFilterBottomSheetWithType(FilterType.Pokit) } },
                onClickPeriod = remember { { viewModel.showFilterBottomSheetWithType(FilterType.Period) } }
            )
        }

        HorizontalDivider(
            thickness = 6.dp,
            color = PokitTheme.colors.backgroundPrimary
        )

        if (state.step == SearchScreenStep.RESULT) {
            when {
                (linkPagingState == PagingState.LOADING_INIT) -> {
                    LoadingProgress(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                (linkPagingState == PagingState.FAILURE_INIT) -> {
                    ErrorPooki(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        title = stringResource(id = coreString.title_error),
                        sub = stringResource(id = coreString.sub_error),
                        onClickRetry = viewModel::searchByCurrentSearchWord
                    )
                }
                (linkList.isEmpty()) -> {
                    EmptyPooki(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        title = stringResource(id = coreString.title_empty_search),
                        sub = stringResource(id = coreString.sub_empty_search)
                    )
                }
                else -> {
                    SearchItemList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onToggleSort = viewModel::toggleSortOrder,
                        useRecentOrder = state.sortRecent,
                        onClickLinkKebab = viewModel::showLinkDetailBottomSheet,
                        onClickLink = {
                            uriHandler.openUri(it.url)
                        },
                        links = linkList,
                        linkPagingState = linkPagingState,
                        loadNextLinks = viewModel::loadNextLinks
                    )
                }
            }
        }
    }
}
