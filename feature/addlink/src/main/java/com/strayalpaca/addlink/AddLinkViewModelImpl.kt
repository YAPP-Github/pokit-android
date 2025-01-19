package com.strayalpaca.addlink

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.addlink.model.AddLinkScreenSideEffect
import com.strayalpaca.addlink.model.AddLinkScreenState
import com.strayalpaca.addlink.model.Link
import com.strayalpaca.addlink.model.LinkUpdateType
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
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.core.feature.navigation.args.LinkArg
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.core.feature.navigation.args.PokitUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link.Companion.MEMO_MAX_LENGTH
import pokitmons.pokit.domain.model.pokit.MAX_POKIT_COUNT
import pokitmons.pokit.domain.usecase.link.CreateLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkCardUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkUseCase
import pokitmons.pokit.domain.usecase.link.ModifyLinkUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitCountUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.usecase.pokit.GetUncategorizedPokitUseCase
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AddLinkViewModelImpl @Inject constructor(
    private val getLinkUseCase: GetLinkUseCase,
    private val getLinkCardUseCase: GetLinkCardUseCase,
    private val createLinkUseCase: CreateLinkUseCase,
    private val modifyLinkUseCase: ModifyLinkUseCase,
    private val getPokitCountUseCase: GetPokitCountUseCase,
    private val getUncategorizedPokitUseCase: GetUncategorizedPokitUseCase,
    getPokitsUseCase: GetPokitsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), AddLinkViewModel {
    private val defaultDispatcher: CoroutineContext = Dispatchers.IO

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

    private val _state = MutableStateFlow(AddLinkScreenState())
    override val state: StateFlow<AddLinkScreenState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableEventFlow<AddLinkScreenSideEffect>()
    override val sideEffect: EventFlow<AddLinkScreenSideEffect>
        get() = _sideEffect.asEventFlow()

    override val pokitList: StateFlow<List<Pokit>>
        get() = pokitPaging.pagingData
    override val pokitListState: StateFlow<PagingState>
        get() = pokitPaging.pagingState

    // 수정 이전 pokit과 수정 이후 pokit이 다른 경우를 체크하기 위해서만 사용
    private var prevPokitId: Int? = null

    private var inputLinkDebounceJob: Job? = null

    init {
        initPokitAddEventListener()

        initData(savedStateHandle = savedStateHandle)

        savedStateHandle.get<String>("link_url")?.let { inputLinkUrl(it) }
    }

    private fun initPokitAddEventListener() {
        viewModelScope.launch(defaultDispatcher) {
            PokitUpdateEvent.addedPokit.collectLatest { addedPokit ->
                _state.update {
                    it.copy(currentPokit = Pokit(addedPokit.title, addedPokit.id.toString(), 0))
                }
            }
        }
    }

    private fun initData(savedStateHandle: SavedStateHandle) {
        val linkUpdateType = savedStateHandle.get<String>("link_id")?.toIntOrNull()?.let {
            LinkUpdateType.Modify(linkId = it)
        } ?: LinkUpdateType.Create

        _state.update {
            it.copy(linkUpdateType = linkUpdateType)
        }

        when (linkUpdateType) {
            LinkUpdateType.Create -> {
                setCurrentPokit(savedStateHandle)
            }
            is LinkUpdateType.Modify -> {
                loadLink(linkUpdateType.linkId)
            }
        }
    }

    private fun loadLink(linkId: Int) {
        viewModelScope.launch(defaultDispatcher) {
            _state.update { it.copy(step = ScreenStep.LOADING) }
            val response = getLinkUseCase.getLink(linkId)
            if (response is PokitResult.Success) {
                val result = response.result
                _state.update {
                    it.copy(
                        link = Link.fromDomainLink(result),
                        title = result.title,
                        linkUrl = result.data,
                        memo = result.memo,
                        useRemind = result.alertYn == "Y",
                        currentPokit = Pokit(
                            title = result.categoryName,
                            id = result.categoryId.toString(),
                            count = 0
                        ),
                        step = ScreenStep.IDLE
                    )
                }
                prevPokitId = result.categoryId
                getLinkMetaData(result.data)
            } else {
                _sideEffect.emit(AddLinkScreenSideEffect.NavigationEvent.Back)
            }
        }
    }

    private suspend fun getLinkMetaData(linkUrl: String) {
        val response = getLinkCardUseCase.getLinkCard(linkUrl)
        if (response is PokitResult.Success) {
            val result = response.result
            val title = if (state.value.title.isEmpty() && result.title.isNotEmpty()) result.title else state.value.title

            _state.update {
                it.copy(
                    step = ScreenStep.IDLE,
                    link = Link.fromDomainLinkCard(response.result),
                    title = title
                )
            }
        } else {
            _state.update {
                it.copy(step = ScreenStep.IDLE)
            }
        }
    }

    private fun setCurrentPokit(savedStateHandle: SavedStateHandle) {
        val initPokitId = savedStateHandle.get<String>("pokit_id")
        val initPokitName = savedStateHandle.get<String>("pokit_name")

        val pokitDataExists = (initPokitId != null && initPokitName != null)
        if (pokitDataExists) {
            viewModelScope.launch(defaultDispatcher) {
                _state.update {
                    it.copy(
                        currentPokit = Pokit(
                            title = initPokitName!!,
                            id = initPokitId!!,
                            count = 0
                        )
                    )
                }
            }
        } else {
            loadUncategorizedPokit()
        }
    }

    private fun loadUncategorizedPokit() {
        viewModelScope.launch(defaultDispatcher) {
            val response = getUncategorizedPokitUseCase.getUncategoriezdPokit()
            if (response is PokitResult.Success) {
                _state.update {
                    it.copy(
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

    override fun saveLink() {
        viewModelScope.launch(defaultDispatcher) {
            val currentState = state.value.copy()
            val linkInfoLoaded = (currentState.currentPokit != null && currentState.link != null)
            val currentSelectedPokit = currentState.currentPokit ?: return@launch
            if (!linkInfoLoaded) return@launch

            _state.update { currentState.copy(step = ScreenStep.LINK_LOADING) }

            val response = when (currentState.linkUpdateType) {
                LinkUpdateType.Create -> {
                    createLinkUseCase.createLink(
                        data = currentState.link!!.url,
                        title = currentState.title,
                        categoryId = currentSelectedPokit.id.toInt(),
                        memo = currentState.memo,
                        alertYn = if (currentState.useRemind) "Y" else "n",
                        thumbNail = currentState.link.imageUrl ?: ""
                    )
                }
                is LinkUpdateType.Modify -> {
                    modifyLinkUseCase.modifyLink(
                        linkId = currentState.linkUpdateType.linkId,
                        data = currentState.link!!.url,
                        title = currentState.title,
                        categoryId = currentSelectedPokit.id.toInt(),
                        memo = currentState.memo,
                        alertYn = if (currentState.useRemind) "Y" else "n",
                        thumbNail = currentState.link.imageUrl ?: ""
                    )
                }
            }

            if (response is PokitResult.Success) {
                val result = response.result

                val linkArg = LinkArg(
                    id = result.id,
                    title = result.title,
                    thumbnail = currentState.link.imageUrl ?: result.thumbnail,
                    domain = result.domain,
                    createdAt = result.createdAt,
                    pokitId = currentSelectedPokit.id.toInt()
                )

                when (currentState.linkUpdateType) {
                    LinkUpdateType.Create -> {
                        LinkUpdateEvent.createSuccess(linkArg)
                    }
                    is LinkUpdateType.Modify -> {
                        PokitUpdateEvent.updatePokitLinkCount(
                            linkAddedPokitId = currentSelectedPokit.id.toIntOrNull(),
                            linkRemovedPokitId = prevPokitId
                        )
                        LinkUpdateEvent.modifySuccess(linkArg)
                    }
                }

                _sideEffect.emit(AddLinkScreenSideEffect.NavigationEvent.Back)
            } else {
                _state.update { it.copy(step = ScreenStep.IDLE, toastMessage = ToastMessageEvent.NETWORK_ERROR) }
            }
        }
    }

    override fun inputLinkUrl(linkUrl: String) {
        _state.update { it.copy(linkUrl = linkUrl) }

        inputLinkDebounceJob?.cancel()
        inputLinkDebounceJob = viewModelScope.launch(defaultDispatcher) {
            delay(1000L)
            _state.update { it.copy(step = ScreenStep.LINK_LOADING, link = null) }
            getLinkMetaData(linkUrl = linkUrl)
        }
    }

    override fun inputTitle(title: String) {
        _state.update { it.copy(title = title) }
    }

    override fun inputMemo(memo: String) {
        if (memo.length <= MEMO_MAX_LENGTH) {
            _state.update { it.copy(memo = memo) }
        }
    }

    override fun checkPokitCountThenNavigateToAddPokit() {
        viewModelScope.launch {
            val response = getPokitCountUseCase.getPokitCount()
            if (response is PokitResult.Success) {
                if (response.result < MAX_POKIT_COUNT) {
                    _state.update { it.copy(step = ScreenStep.IDLE) }
                    _sideEffect.emit(AddLinkScreenSideEffect.NavigationEvent.AddPokit)
                } else {
                    _state.update { it.copy(toastMessage = ToastMessageEvent.CANNOT_CREATE_POKIT_MORE) }
                }
            } else {
                _state.update { it.copy(toastMessage = ToastMessageEvent.NETWORK_ERROR) }
            }
        }
    }

    override fun showPokitListBottomSheet() {
        _state.update { it.copy(step = ScreenStep.POKIT_SELECT) }
    }

    override fun hidePokitListBottomSheet() {
        _state.update { it.copy(step = ScreenStep.IDLE) }
    }

    override fun hideToastMessage() {
        _state.update { it.copy(toastMessage = null) }
    }

    override fun loadNextPokits() {
        viewModelScope.launch {
            pokitPaging.load()
        }
    }

    override fun refreshPokits() {
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    override fun setSelectedPokit(pokit: Pokit) {
        _state.update { it.copy(currentPokit = pokit, step = ScreenStep.IDLE) }
    }

    override fun clearTitle() {
        _state.update { it.copy(title = "") }
    }

    override fun clearLinkUrl() {
        _state.update { it.copy(linkUrl = "") }
    }
}
