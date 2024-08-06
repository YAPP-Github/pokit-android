package com.strayalpaca.pokitdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Filter
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.model.PokitDetailScreenState
import com.strayalpaca.pokitdetail.model.sampleLinkList
import com.strayalpaca.pokitdetail.paging.PokitPaging
import com.strayalpaca.pokitdetail.paging.SimplePagingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit
import javax.inject.Inject

@HiltViewModel
class PokitDetailViewModel @Inject constructor(
    private val getPokitsUseCase: GetPokitsUseCase
) : ViewModel() {
    private val pokitPaging = PokitPaging(
        getPokits = ::getPokits,
        perPage = 10,
        coroutineScope = viewModelScope,
        initPage = 0
    )

    private val _state = MutableStateFlow(PokitDetailScreenState())
    val state: StateFlow<PokitDetailScreenState> = _state.asStateFlow()

    val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitListState : StateFlow<SimplePagingState> = pokitPaging.pagingState

    private val _linkList = MutableStateFlow(sampleLinkList)
    val linkList: StateFlow<List<Link>> = _linkList.asStateFlow()

    private suspend fun getPokits(size : Int, page : Int) : PokitResult<List<DomainPokit>> {
        return getPokitsUseCase.getPokits(size = size, page = page)
    }

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

    fun loadNextPokits() {
        viewModelScope.launch {
            pokitPaging.load()
        }
    }

    fun refreshPokits() {
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }
}
