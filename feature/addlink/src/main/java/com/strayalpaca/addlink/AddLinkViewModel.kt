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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.core.feature.navigation.args.LinkArg
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.core.feature.navigation.args.PokitUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.MAX_POKIT_COUNT
import pokitmons.pokit.domain.usecase.link.CreateLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkCardUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkUseCase
import pokitmons.pokit.domain.usecase.link.ModifyLinkUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitCountUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.usecase.pokit.GetUncategorizedPokitUseCase
import javax.inject.Inject

@HiltViewModel
class AddLinkViewModel @Inject constructor(
    private val getLinkUseCase: GetLinkUseCase,
    private val getLinkCardUseCase: GetLinkCardUseCase,
    private val createLinkUseCase: CreateLinkUseCase,
    private val modifyLinkUseCase: ModifyLinkUseCase,
    private val getPokitCountUseCase: GetPokitCountUseCase,
    private val getUncategorizedPokitUseCase: GetUncategorizedPokitUseCase,
    getPokitsUseCase: GetPokitsUseCase,
    savedStateHandle: SavedStateHandle,
) : ContainerHost<AddLinkScreenState, AddLinkScreenSideEffect>, ViewModel() {
    override val container: Container<AddLinkScreenState, AddLinkScreenSideEffect> = container(AddLinkScreenState())

    private val pokitPagingSource = object : PagingSource<Pokit> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Pokit> {
            val response = getPokitsUseCase.getPokits(page = pageIndex, size = pageSize)
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { domainPokits -> domainPokits.map { Pokit.fromDomainPokit(it) } }
            )
        }
    }

    private val pokitPaging = SimplePaging(
        pagingSource = pokitPagingSource,
        getKeyFromItem = { pokit -> pokit.id },
        coroutineScope = viewModelScope
    )

    val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitListState: StateFlow<PagingState> = pokitPaging.pagingState

    private val _linkUrl = MutableStateFlow("")
    val linkUrl: StateFlow<String> = _linkUrl.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _memo = MutableStateFlow("")
    val memo: StateFlow<String> = _memo.asStateFlow()

    val currentLinkId: Int? = savedStateHandle.get<String>("link_id")?.toIntOrNull()

    // 수정 이전 pokit과 수정 이후 pokit이 다른 경우를 체크하기 위해서만 사용
    private var prevPokitId: Int? = null

    init {
        initPokitAddEventDetector()

        if (currentLinkId != null) {
            loadPokitLink(currentLinkId)
        } else {
            loadUncategorizedPokit()
        }
    }

    private fun initPokitAddEventDetector() {
        viewModelScope.launch {
            PokitUpdateEvent.addedPokit.collectLatest { addedPokit ->
                intent {
                    reduce {
                        state.copy(currentPokit = Pokit(addedPokit.title, addedPokit.id.toString(), 0))
                    }
                }
            }
        }
    }

    private fun loadUncategorizedPokit() {
        viewModelScope.launch {
            val response = getUncategorizedPokitUseCase.getUncategoriezdPokit()
            if (response is PokitResult.Success) {
                intent {
                    reduce {
                        state.copy(
                            currentPokit = Pokit(
                                title = response.result.name,
                                id = response.result.categoryId.toString(),
                                count = response.result.linkCount
                            )
                        )
                    }
                }
            }
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
                prevPokitId = responseResult.categoryId
                _title.update { response.result.title }
                _memo.update { response.result.memo }
                _linkUrl.update { response.result.data }
                getLinkMetaData(response.result.data)
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

                getLinkMetaData(linkUrl)
            }
        }
    }

    private suspend fun getLinkMetaData(linkUrl: String) = intent {
        val response = getLinkCardUseCase.getLinkCard(linkUrl)
        if (response is PokitResult.Success) {
            reduce { state.copy(step = ScreenStep.IDLE, link = Link.fromDomainLinkCard(response.result)) }
            if (response.result.title.isNotEmpty() && title.value.isEmpty()) {
                _title.update { response.result.title }
            }
        } else {
            reduce { state.copy(step = ScreenStep.IDLE) }
        }
    }

    fun inputTitle(title: String) {
        _title.update { title }
    }

    fun inputMemo(memo: String) {
        if (memo.length <= 100) {
            _memo.update { memo }
        }
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
            val currentSelectedPokit = currentState.currentPokit ?: return@launch
            if (!canSave(currentState)) return@launch

            reduce { state.copy(step = ScreenStep.LINK_LOADING) }

            val isModify = currentLinkId != null
            val response = if (isModify) {
                modifyLinkUseCase.modifyLink(
                    linkId = currentLinkId!!,
                    data = currentState.link!!.url,
                    title = title.value,
                    categoryId = currentSelectedPokit.id.toInt(),
                    memo = memo.value,
                    alertYn = if (currentState.useRemind) "Y" else "n",
                    thumbNail = currentState.link.imageUrl ?: ""
                )
            } else {
                createLinkUseCase.createLink(
                    data = currentState.link!!.url,
                    title = title.value,
                    categoryId = currentSelectedPokit.id.toInt(),
                    memo = memo.value,
                    alertYn = if (currentState.useRemind) "Y" else "n",
                    thumbNail = currentState.link.imageUrl ?: ""
                )
            }
            if (response is PokitResult.Success) {
                reduce { state.copy(step = ScreenStep.IDLE) }
                val responseLink = response.result

                val linkArg = LinkArg(
                    id = responseLink.id,
                    title = responseLink.title,
                    thumbnail = currentState.link.imageUrl ?: responseLink.thumbnail,
                    domain = responseLink.domain,
                    createdAt = responseLink.createdAt,
                    pokitId = currentSelectedPokit.id.toInt()
                )

                val isCreate = (currentLinkId == null)
                if (isCreate) {
                    LinkUpdateEvent.createSuccess(linkArg)
                } else {
                    PokitUpdateEvent.updatePokitLinkCount(
                        linkAddedPokitId = currentSelectedPokit.id.toIntOrNull(),
                        linkRemovedPokitId = prevPokitId
                    )
                    LinkUpdateEvent.modifySuccess(linkArg)
                }

                postSideEffect(AddLinkScreenSideEffect.AddLinkSuccess)
            } else {
                reduce { state.copy(step = ScreenStep.IDLE, toastMessage = ToastMessageEvent.NETWORK_ERROR) }
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

    fun checkPokitCount() = intent {
        viewModelScope.launch {
            val response = getPokitCountUseCase.getPokitCount()
            if (response is PokitResult.Success) {
                if (response.result >= MAX_POKIT_COUNT) {
                    reduce { state.copy(toastMessage = ToastMessageEvent.CANNOT_CREATE_POKIT_MORE) }
                } else {
                    postSideEffect(AddLinkScreenSideEffect.OnNavigateToAddPokit)
                }
            } else {
                reduce { state.copy(toastMessage = ToastMessageEvent.NETWORK_ERROR) }
            }
        }
    }

    fun closeToastMessage() = intent {
        reduce { state.copy(toastMessage = null) }
    }
}
