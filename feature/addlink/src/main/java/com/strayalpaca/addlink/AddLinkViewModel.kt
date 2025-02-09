package com.strayalpaca.addlink

import com.strayalpaca.addlink.model.AddLinkScreenSideEffect
import com.strayalpaca.addlink.model.AddLinkScreenState
import com.strayalpaca.addlink.model.Pokit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingState

interface AddLinkViewModel {
    val state: StateFlow<AddLinkScreenState>
    val sideEffect: EventFlow<AddLinkScreenSideEffect>

    val pokitList: StateFlow<List<Pokit>>
    val pokitListState: StateFlow<PagingState>

    fun saveLink()
    fun inputLinkUrl(linkUrl: String)
    fun clearLinkUrl()
    fun inputTitle(title: String)
    fun clearTitle()
    fun inputMemo(memo: String)
    fun checkPokitCountThenNavigateToAddPokit()
    fun showPokitListBottomSheet()
    fun hidePokitListBottomSheet()
    fun hideToastMessage()
    fun loadNextPokits()
    fun refreshPokits()
    fun setSelectedPokit(pokit: Pokit)

    companion object {
        val dummyAddLinkViewModel = object : AddLinkViewModel {
            override val state: StateFlow<AddLinkScreenState>
                get() = MutableStateFlow(AddLinkScreenState())
            override val sideEffect: EventFlow<AddLinkScreenSideEffect>
                get() = MutableEventFlow()
            override val pokitList: StateFlow<List<Pokit>>
                get() = MutableStateFlow(emptyList())
            override val pokitListState: StateFlow<PagingState>
                get() = MutableStateFlow(PagingState.IDLE)

            override fun saveLink() { }

            override fun inputLinkUrl(linkUrl: String) {}

            override fun clearLinkUrl() { }

            override fun inputTitle(title: String) { }

            override fun clearTitle() { }

            override fun inputMemo(memo: String) { }

            override fun checkPokitCountThenNavigateToAddPokit() { }

            override fun showPokitListBottomSheet() { }

            override fun hidePokitListBottomSheet() { }

            override fun hideToastMessage() { }

            override fun loadNextPokits() { }

            override fun refreshPokits() { }

            override fun setSelectedPokit(pokit: Pokit) { }
        }
    }
}
