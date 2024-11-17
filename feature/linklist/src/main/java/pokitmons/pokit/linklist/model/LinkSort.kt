package pokitmons.pokit.linklist.model

import pokitmons.pokit.domain.model.pokit.PokitsSort
import pokitmons.pokit.linklist.R

enum class LinkSort(val titleResourceId: Int) {
    RECENT(titleResourceId = R.string.title_sort_recent),
    ALPHABETICAL(titleResourceId = R.string.title_sort_alphabet),
    ;

    companion object {
        fun toggle(sort: LinkSort): LinkSort {
            return if (sort == RECENT) ALPHABETICAL else RECENT
        }

        fun toPokitsSort(linkSort: LinkSort): PokitsSort {
            return when (linkSort) {
                RECENT -> PokitsSort.RECENT
                ALPHABETICAL -> PokitsSort.ALPHABETICAL
            }
        }
    }
}
