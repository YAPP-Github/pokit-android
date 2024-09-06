package pokitmons.pokit.search.model

data class SearchScreenState(
    val step: SearchScreenStep = SearchScreenStep.INPUT,
    val filter: Filter? = null,
    val recentSearchWords: List<String> = emptyList(),
    val useRecentSearchWord: Boolean = false,
    val showFilterBottomSheet: Boolean = false,
    val firstBottomSheetFilterType: FilterType = FilterType.Pokit,
    val showLinkDetailBottomSheet: Boolean = false,
    val linkBottomSheetType: BottomSheetType? = null,
    val sortRecent: Boolean = true,
    val currentTargetLink: Link? = null,
    val currentDetailLink: Link? = null,
)

enum class SearchScreenStep {
    INPUT, RESULT
}

enum class BottomSheetType {
    MODIFY, REMOVE
}
