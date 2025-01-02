package pokitmons.pokit.search.model

data class SearchScreenState(
    val step: SearchScreenStep = SearchScreenStep.INPUT,
    val filter: Filter? = null,
    val recentSearchWords: List<String> = emptyList(),
    val useRecentSearchWord: Boolean = false,
    val showFilterBottomSheet: Boolean = false,
    val firstBottomSheetFilterType: FilterType = FilterType.Pokit,
    val linkBottomSheetType: LinkBottomSheetState? = null,
    val sortRecent: Boolean = true,
    val searchWord: String = ""
)

enum class SearchScreenStep {
    INPUT, RESULT
}

sealed class LinkBottomSheetState(open val link: Link) {
    data class LinkDetail(override val link: Link) : LinkBottomSheetState(link)
    data class CheckRemove(override val link: Link) : LinkBottomSheetState(link)
}
