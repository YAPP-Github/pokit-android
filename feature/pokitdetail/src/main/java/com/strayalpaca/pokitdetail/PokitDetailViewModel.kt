package com.strayalpaca.pokitdetail

import com.strayalpaca.pokitdetail.model.Filter
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.model.PokitDetailScreenState
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.model.paging.PagingState

interface PokitDetailViewModel {
    val pokitList: StateFlow<List<Pokit>>
    val pokitListState: StateFlow<PagingState>

    val linkList: StateFlow<List<Link>>
    val linkListState: StateFlow<PagingState>

    val moveToBackEvent: EventFlow<Boolean>
    val state: StateFlow<PokitDetailScreenState>

    fun changePokit(pokit: Pokit)
    fun changeFilter(filter: Filter)

    fun showPokitModifyBottomSheet()
    fun showPokitRemoveBottomSheet()
    fun hidePokitBottomSheet()

    fun showLinkRemoveBottomSheet()
    fun showLinkRemoveBottomSheet(link: Link)
    fun hideLinkBottomSheet()

    fun showLinkDetailBottomSheet(link: Link)
    fun hideLinkDetailBottomSheet()

    fun showFilterChangeBottomSheet()
    fun hideFilterChangeBottomSheet()

    fun showPokitSelectBottomSheet()
    fun hidePokitSelectBottomSheet()

    fun loadNextPokits()
    fun refreshPokits()

    fun loadNextLinks()

    fun deletePokit(pokit: Pokit)
    fun deleteLink(link: Link)

    fun toggleBookmark(link: Link)
}
