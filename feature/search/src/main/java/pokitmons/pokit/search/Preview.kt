package pokitmons.pokit.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.SearchScreenState
import pokitmons.pokit.search.model.SearchScreenStep

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchScreen(
                state = SearchScreenState(step = SearchScreenStep.RESULT),
                viewModel = dummyViewModel
            )
        }
    }
}

internal val sampleLinks = listOf(
    Link(
        id = "1",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.04.12",
        domainUrl = "youtu.be",
        isRead = true,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "2",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.05.12",
        domainUrl = "youtu.be",
        isRead = false,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "3",
        title = "포킷포킷",
        imageUrl = null,
        dateString = "2024.04.12",
        domainUrl = "pokitmons.pokit",
        isRead = true,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "4",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.06.12",
        domainUrl = "youtu.be",
        isRead = true,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "5",
        title = "마지막 링크입니다.",
        imageUrl = null,
        dateString = "2024.07.14",
        domainUrl = "youtu.be",
        isRead = false,
        url = "",
        memo = "",
        bookmark = true
    )
)

val dummyViewModel = object : SearchViewModel {
    override val linkList: StateFlow<List<Link>>
        get() = MutableStateFlow(sampleLinks)
    override val linkPagingState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)
    override val pokitList: StateFlow<List<Pokit>>
        get() = MutableStateFlow(emptyList())
    override val pokitPagingState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)
    override val state: StateFlow<SearchScreenState>
        get() = MutableStateFlow(SearchScreenState())

    override fun inputSearchWord(searchWord: String) {}

    override fun searchByCurrentSearchWord() {}

    override fun inputSearchWordThenSearch(word: String) {}

    override fun toggleUseRecentSearchWord() {}

    override fun removeRecentSearchWord(word: String) {}

    override fun removeAllRecentSearchWord() {}

    override fun showFilterBottomSheet() {}

    override fun showFilterBottomSheetWithType(type: FilterType) {}

    override fun hideFilterBottomSheet() {}

    override fun showLinkRemoveBottomSheet(link: Link) {}

    override fun showLinkDetailBottomSheet(link: Link) {}

    override fun hideLinkBottomSheet() {}

    override fun setFilter(filter: Filter) {}

    override fun toggleSortOrder() {}

    override fun loadNextLinks() {}

    override fun loadNextPokits() {}

    override fun refreshPokits() {}

    override fun toggleBookmark(link: Link) {}

    override fun deleteLink(link: Link) {}
}
