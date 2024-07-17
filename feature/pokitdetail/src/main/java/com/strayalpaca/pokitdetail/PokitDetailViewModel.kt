package com.strayalpaca.pokitdetail

import androidx.lifecycle.ViewModel
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Filter
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.model.PokitDetailScreenState
import com.strayalpaca.pokitdetail.model.sampleLinkList
import com.strayalpaca.pokitdetail.model.samplePokitList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokitDetailViewModel : ViewModel() {
    private val _state = MutableStateFlow(PokitDetailScreenState())
    val state: StateFlow<PokitDetailScreenState> = _state.asStateFlow()

    private val _pokitList = MutableStateFlow(samplePokitList)
    val pokitList: StateFlow<List<Pokit>> = _pokitList.asStateFlow()

    private val _linkList = MutableStateFlow(sampleLinkList)
    val linkList: StateFlow<List<Link>> = _linkList.asStateFlow()

    fun changePokit(pokit: Pokit) {
        _state.update { it.copy(currentPokit = pokit, pokitSelectBottomSheetVisible = false) }
    }

    fun changeFilter(filter: Filter) {
        _state.update { it.copy(currentFilter = filter, filterChangeBottomSheetVisible = false) }
    }

    fun showPokitModifyBottomSheet() {
        _state.update { it.copy(pokitBottomSheetType = BottomSheetType.MODIFY) }
    }

    fun showPokitRemoveBottomSheet() {
        _state.update { it.copy(pokitBottomSheetType = BottomSheetType.REMOVE) }
    }

    fun hidePokitBottomSheet() {
        _state.update { it.copy(pokitBottomSheetType = null) }
    }

    fun showLinkModifyBottomSheet(link: Link) {
        _state.update { it.copy(linkBottomSheetType = BottomSheetType.MODIFY, currentLink = link) }
    }

    fun showLinkRemoveBottomSheet() {
        _state.update { it.copy(linkBottomSheetType = BottomSheetType.REMOVE) }
    }

    fun hideLinkBottomSheet() {
        _state.update { it.copy(linkBottomSheetType = null, currentLink = null) }
    }

    fun showLinkDetailBottomSheet(link: Link) {
        _state.update { it.copy(currentLink = link, linkDetailBottomSheetVisible = true) }
    }

    fun hideLinkDetailBottomSheet() {
        _state.update { it.copy(currentLink = null, linkDetailBottomSheetVisible = false) }
    }

    fun showFilterChangeBottomSheet() {
        _state.update { it.copy(filterChangeBottomSheetVisible = true) }
    }

    fun hideFilterChangeBottomSheet() {
        _state.update { it.copy(filterChangeBottomSheetVisible = false) }
    }

    fun showPokitSelectBottomSheet() {
        _state.update { it.copy(pokitSelectBottomSheetVisible = true) }
    }

    fun hidePokitSelectBottomSheet() {
        _state.update { it.copy(pokitSelectBottomSheetVisible = false) }
    }
}
