package com.strayalpaca.addlink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.addlink.model.AddLinkScreenSideEffect
import com.strayalpaca.addlink.model.AddLinkScreenState
import com.strayalpaca.addlink.model.Pokit
import com.strayalpaca.addlink.model.ScreenStep
import com.strayalpaca.addlink.model.sampleLink
import com.strayalpaca.addlink.model.samplePokitList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class AddLinkViewModel : ContainerHost<AddLinkScreenState, AddLinkScreenSideEffect>, ViewModel() {
    override val container: Container<AddLinkScreenState, AddLinkScreenSideEffect> = container(AddLinkScreenState())

    private val _linkUrl = MutableStateFlow("")
    val linkUrl : StateFlow<String> = _linkUrl.asStateFlow()

    private val _title = MutableStateFlow("")
    val title : StateFlow<String> = _title.asStateFlow()

    private val _memo = MutableStateFlow("")
    val memo : StateFlow<String> = _memo.asStateFlow()

    private val _pokitName = MutableStateFlow("")
    val pokitName : StateFlow<String> = _pokitName.asStateFlow()

    init {
        loadPokitList()
    }

    private var inputLinkJob: Job? = null

    private fun loadPokitList() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(step = ScreenStep.LOADING) }
            // todo 포킷 목록 가져오기 api 연결
            delay(1000L)
            reduce {
                state.copy(
                    step = ScreenStep.IDLE,
                    pokitList = samplePokitList
                )
            }
        }
    }

    fun loadPokitLink(linkId: String) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(step = ScreenStep.LOADING) }
            // todo 포킷 링크 가져오기 api 연결
            delay(1000L)
            reduce {
                state.copy(
                    step = ScreenStep.IDLE
                )
            }
        }
    }

    fun inputLinkUrl(linkUrl: String) {
        this._linkUrl.update { linkUrl }

        intent {
            inputLinkJob?.cancel()
            inputLinkJob = viewModelScope.launch(Dispatchers.IO) {
                delay(1000L)
                reduce { state.copy(step = ScreenStep.LINK_LOADING) }
                // todo 링크 카드 정보 가져오기 api 연결
                delay(1000L)
                reduce { state.copy(step = ScreenStep.IDLE, link = sampleLink.copy(url = linkUrl)) }
            }
        }
    }

    fun inputTitle(title: String) {
        _title.update { title }
    }

    fun inputMemo(memo: String) {
        _memo.update { memo }
    }

    fun showAddPokitBottomSheet() = intent {
        reduce { state.copy(step = ScreenStep.POKIT_ADD) }
    }

    fun hideAddPokitBottomSheet() = intent {
        reduce { state.copy(step = ScreenStep.IDLE) }
    }

    fun showSelectPokitBottomSheet() = intent {
        reduce { state.copy(step = ScreenStep.POKIT_SELECT) }
    }

    fun hideSelectPokitBottomSheet() = intent {
        reduce { state.copy(step = ScreenStep.IDLE) }
    }

    fun selectPokit(pokit: Pokit) = intent {
        reduce { state.copy(currentPokit = pokit, step = ScreenStep.IDLE) }
    }

    fun onBackPressed() = intent {
        val currentStep = container.stateFlow.value.step
        when (currentStep) {
            is ScreenStep.POKIT_ADD_LOADING -> {} // discard

            is ScreenStep.SAVE_LOADING -> {} // discard

            ScreenStep.POKIT_SELECT -> reduce { state.copy(step = ScreenStep.IDLE) }

            is ScreenStep.POKIT_ADD -> reduce { state.copy(step = ScreenStep.IDLE) }

            else -> postSideEffect(AddLinkScreenSideEffect.OnNavigationBack)
        }
    }

    fun inputNewPokitName(pokitName: String) {
        _pokitName.update { pokitName }
    }

    fun savePokit() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(step = ScreenStep.POKIT_ADD_LOADING) }
            // todo 포킷 저장 useCase 연결
            delay(1000L)
            reduce { state.copy(step = ScreenStep.IDLE) }
        }
    }

    fun saveLink() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(step = ScreenStep.LINK_LOADING) }
            // todo 링크 저장 useCase 연결
            delay(1000L)
            reduce { state.copy(step = ScreenStep.IDLE) }
            postSideEffect(AddLinkScreenSideEffect.AddLinkSuccess)
        }
    }

    fun setRemind(remind: Boolean) = intent {
        reduce { state.copy(useRemind = remind) }
    }
}
