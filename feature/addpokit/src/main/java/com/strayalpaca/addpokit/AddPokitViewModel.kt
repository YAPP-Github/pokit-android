package com.strayalpaca.addpokit

import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.AddPokitSideEffect
import com.strayalpaca.addpokit.model.Pokit
import com.strayalpaca.addpokit.model.PokitImage
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.model.paging.PagingState

interface AddPokitViewModel {
    val state: StateFlow<AddPokitScreenState>
    val pokitList: StateFlow<List<Pokit>>
    val pokitListState: StateFlow<PagingState>
    val sideEffect: EventFlow<AddPokitSideEffect>

    fun loadNextPokits()
    fun inputPokitName(pokitName: String)
    fun savePokit()
    fun showPokitProfileImageSelectBottomSheet()
    fun hidePokitProfileImageSelectBottomSheet()
    fun setPokitProfileImage(pokitImage: PokitImage)
    fun hideToastMessage()
}
