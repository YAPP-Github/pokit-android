package pokitmons.pokit.linklist.model

import pokitmons.pokit.linklist.R

enum class LinkSort(val titleResourceId: Int) {
    RECENT(titleResourceId = R.string.title_sort_recent),
    ALPHABETICAL(titleResourceId = R.string.title_sort_alphabet);

    companion object {
        fun toggle(sort: LinkSort): LinkSort {
            return if (sort == RECENT) ALPHABETICAL else RECENT
        }
    }
}
