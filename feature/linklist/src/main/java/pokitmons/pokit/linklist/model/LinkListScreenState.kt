package pokitmons.pokit.linklist.model

data class LinkListScreenState(
    val type: LinkListScreenType = LinkListScreenType.Bookmark,
    val sort: LinkSort = LinkSort.RECENT,
    val count: Int = 0
)
