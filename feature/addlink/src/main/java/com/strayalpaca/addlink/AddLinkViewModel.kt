package com.strayalpaca.addlink

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.addlink.model.AddLinkScreenSideEffect
import com.strayalpaca.addlink.model.AddLinkScreenState
import com.strayalpaca.addlink.model.Link
import com.strayalpaca.addlink.model.Pokit
import com.strayalpaca.addlink.model.ScreenStep
import com.strayalpaca.addlink.model.ToastMessageEvent
import com.strayalpaca.addlink.paging.PokitPaging
import com.strayalpaca.addlink.paging.SimplePagingState
import dagger.hilt.android.lifecycle.HiltViewModel
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
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.link.CreateLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkCardUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkUseCase
import pokitmons.pokit.domain.usecase.link.ModifyLinkUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import javax.inject.Inject

@HiltViewModel
class AddLinkViewModel @Inject constructor(
    private val getLinkUseCase: GetLinkUseCase,
    private val getLinkCardUseCase: GetLinkCardUseCase,
    private val createLinkUseCase: CreateLinkUseCase,
    private val modifyLinkUseCase: ModifyLinkUseCase,
    getPokitsUseCase: GetPokitsUseCase,
    savedStateHandle: SavedStateHandle,
) : ContainerHost<AddLinkScreenState, AddLinkScreenSideEffect>, ViewModel() {
    override val container: Container<AddLinkScreenState, AddLinkScreenSideEffect> = container(AddLinkScreenState())

    private val pokitPaging = PokitPaging(
        getPokits = getPokitsUseCase,
        perPage = 10,
        coroutineScope = viewModelScope,
        initPage = 0
    )

    val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitListState: StateFlow<SimplePagingState> = pokitPaging.pagingState

    private val _linkUrl = MutableStateFlow("")
    val linkUrl: StateFlow<String> = _linkUrl.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _memo = MutableStateFlow("")
    val memo: StateFlow<String> = _memo.asStateFlow()

    val currentLinkId: Int? = savedStateHandle.get<String>("link_id")?.toIntOrNull()

    init {
        currentLinkId?.let { linkId ->
            loadPokitLink(linkId)
        }
    }

    private var inputLinkJob: Job? = null

    private fun loadPokitLink(linkId: Int) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(step = ScreenStep.LOADING) }
            val response = getLinkUseCase.getLink(linkId)
            if (response is PokitResult.Success) {
                val responseResult = response.result
                reduce {
                    state.copy(
                        link = Link.fromDomainLink(responseResult),
                        useRemind = responseResult.alertYn == "Y",
                        currentPokit = Pokit(
                            title = responseResult.categoryName,
                            id = responseResult.categoryId.toString(),
                            count = 0
                        ),
                        step = ScreenStep.IDLE
                    )
                }
            } else {
                postSideEffect(AddLinkScreenSideEffect.OnNavigationBack)
            }
        }
    }

    fun inputLinkUrl(linkUrl: String) {
        this._linkUrl.update { linkUrl }

        intent {
            inputLinkJob?.cancel()
            inputLinkJob = viewModelScope.launch(Dispatchers.IO) {
                delay(1000L)
                reduce { state.copy(step = ScreenStep.LINK_LOADING, link = null) }

                val response = getLinkCardUseCase.getLinkCard(linkUrl)
                if (response is PokitResult.Success) {
                    reduce { state.copy(step = ScreenStep.IDLE, link = Link.fromDomainLinkCard(response.result)) }
                } else {
                    reduce { state.copy(step = ScreenStep.IDLE) }
                }
            }
        }
    }

    fun inputTitle(title: String) {
        _title.update { title }
    }

    fun inputMemo(memo: String) {
        _memo.update { memo }
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

    fun saveLink() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = state.copy()
            if (!canSave(currentState)) return@launch

            reduce { state.copy(step = ScreenStep.LINK_LOADING) }

            val isModify = currentLinkId != null
            val response = if (isModify) {
                modifyLinkUseCase.modifyLink(
                    linkId = currentLinkId!!,
                    data = currentState.link!!.url,
                    title = currentState.link.title,
                    categoryId = currentState.currentPokit!!.id.toInt(),
                    memo = memo.value,
                    alertYn = if (currentState.useRemind) "Y" else "n",
                    thumbNail = currentState.link.imageUrl ?: ""
                )
            } else {
                createLinkUseCase.createLink(
                    data = currentState.link!!.url,
                    title = title.value,
                    categoryId = currentState.currentPokit!!.id.toInt(),
                    memo = memo.value,
                    alertYn = if (currentState.useRemind) "Y" else "n",
                    thumbNail = currentState.link.imageUrl ?: ""
                )
            }
            if (response is PokitResult.Success) {
                reduce { state.copy(step = ScreenStep.IDLE) }
                postSideEffect(AddLinkScreenSideEffect.AddLinkSuccess)
            } else {
                reduce { state.copy(step = ScreenStep.IDLE) }
                postSideEffect(AddLinkScreenSideEffect.ToastMessage(ToastMessageEvent.NETWORK_ERROR))
            }
        }
    }

    private fun canSave(state: AddLinkScreenState): Boolean {
        return (state.currentPokit != null && state.link != null)
    }

    fun setRemind(remind: Boolean) = intent {
        reduce { state.copy(useRemind = remind) }
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
