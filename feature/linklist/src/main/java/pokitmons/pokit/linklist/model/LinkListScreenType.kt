package pokitmons.pokit.linklist.model

import pokitmons.pokit.linklist.R

enum class LinkListScreenType(val resourceId: Int, val key: String) {
    Unread(R.string.title_unread, "unread"), Bookmark(R.string.title_bookmark, "bookmark");

    companion object {
        fun getByKey(key: String): LinkListScreenType {
            return LinkListScreenType.entries.find { it.key == key } ?: Unread
        }
    }
}
