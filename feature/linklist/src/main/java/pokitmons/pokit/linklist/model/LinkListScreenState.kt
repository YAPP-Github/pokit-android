package pokitmons.pokit.linklist.model

data class LinkListScreenState(
    val type: LinkListScreenType = LinkListScreenType.Bookmark,
    val sort: LinkSort = LinkSort.RECENT,
    val count: Int = 0,
    val bottomSheetInfo: BottomSheetInfo? = null,
)

data class BottomSheetInfo(
    val type: BottomSheetType,
    val link: Link
)

enum class BottomSheetType {
    DETAIL, CHECK_REMOVE
}
