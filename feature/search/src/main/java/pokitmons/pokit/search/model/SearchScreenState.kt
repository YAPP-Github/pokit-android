package pokitmons.pokit.search.model

data class SearchScreenState(
    val step: SearchScreenStep = SearchScreenStep.INPUT,
    val filter: Filter? = null,
    val recentSearchWords: List<String> = emptyList(),
    val useRecentSearchWord: Boolean = false,
    val showFilterBottomSheet: Boolean = false,
    val firstBottomSheetFilterType: FilterType = FilterType.Pokit,
    val sortRecent: Boolean = true,
    val currentLink: Link? = null,
)

enum class SearchScreenStep {
    INPUT, RESULT
}
