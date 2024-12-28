package com.strayalpaca.addpokit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.addpokit.const.POKIT_NAME_MAX_LENGTH
import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.AddPokitScreenStep
import com.strayalpaca.addpokit.model.AddPokitSideEffect
import com.strayalpaca.addpokit.model.Pokit
import com.strayalpaca.addpokit.model.PokitImage
import com.strayalpaca.addpokit.model.PokitUpdateType
import com.strayalpaca.addpokit.utils.ErrorMessageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.core.feature.navigation.args.PokitArg
import pokitmons.pokit.core.feature.navigation.args.PokitUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.PokitErrorCode
import pokitmons.pokit.domain.usecase.pokit.CreatePokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitImagesUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.usecase.pokit.ModifyPokitUseCase
import javax.inject.Inject

@HiltViewModel
class AddPokitViewModelImpl @Inject constructor(
    private val getPokitImagesUseCase: GetPokitImagesUseCase,
    private val getPokitsUseCase: GetPokitsUseCase,
    private val getPokitUseCase: GetPokitUseCase,
    private val createPokitUseCase: CreatePokitUseCase,
    private val modifyPokitUseCase: ModifyPokitUseCase,
    private val errorMessageProvider: ErrorMessageProvider,
    savedStateHandle: SavedStateHandle,
) : AddPokitViewModel, ViewModel() {
    private val defaultDispatcher = Dispatchers.IO

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

    private val _state = MutableStateFlow(AddPokitScreenState())
    override val state: StateFlow<AddPokitScreenState>
        get() = _state.asStateFlow()

    override val pokitList: StateFlow<List<Pokit>>
        get() = pokitPaging.pagingData
    override val pokitListState: StateFlow<PagingState>
        get() = pokitPaging.pagingState

    private val _sideEffect = MutableEventFlow<AddPokitSideEffect>()
    override val sideEffect: EventFlow<AddPokitSideEffect>
        get() = _sideEffect.asEventFlow()

    private var originPokitName: String? = null

    init {
        initPokitList()

        initData(savedStateHandle = savedStateHandle)
    }

    private fun initPokitList() {
        viewModelScope.launch(defaultDispatcher) {
            pokitPaging.refresh()
        }
    }

    private fun initData(savedStateHandle: SavedStateHandle) {
        viewModelScope.launch(defaultDispatcher) {
            val profileImages = getPokitProfileImages()
            if (profileImages.isNullOrEmpty()) {
                _sideEffect.emit(AddPokitSideEffect.OnNavigationBack)
                return@launch
            }

            val updateType = savedStateHandle.get<String>("pokit_id")?.toIntOrNull()?.let {
                PokitUpdateType.Modify(pokitId = it)
            } ?: PokitUpdateType.Create

            when (updateType) {
                is PokitUpdateType.Create -> {
                    _state.update { state ->
                        state.copy(
                            pokitImage = profileImages[0],
                            pokitUpdateType = updateType,
                            pokitProfileImages = profileImages
                        )
                    }
                }
                is PokitUpdateType.Modify -> {
                    val response = getPokitUseCase.getPokit(updateType.pokitId)
                    if (response is PokitResult.Success) {
                        _state.update { state ->
                            state.copy(
                                pokitUpdateType = updateType,
                                pokitImage = PokitImage.fromDomainPokitImage(response.result.image),
                                pokitName = response.result.name,
                                pokitProfileImages = profileImages
                            )
                        }
                        originPokitName = response.result.name
                    } else {
                        _sideEffect.emit(AddPokitSideEffect.OnNavigationBack)
                    }
                }
            }
        }
    }

    private suspend fun getPokitProfileImages() : List<PokitImage>? {
        val response = getPokitImagesUseCase.getImages()
        if (response is PokitResult.Success) {
            return response.result.map { PokitImage.fromDomainPokitImage(it) }
        }
        return null
    }

    override fun loadNextPokits() {
        viewModelScope.launch(defaultDispatcher) {
            pokitPaging.load()
        }
    }

    override fun inputPokitName(pokitName: String) {
        val pokitNameRangeOutOfLength = pokitName.length > POKIT_NAME_MAX_LENGTH

        if (pokitNameRangeOutOfLength) {
            val errorMessage = errorMessageProvider.getTextLengthErrorMessage()
            _state.update { state -> state.copy(pokitInputErrorMessage = errorMessage, pokitName = pokitName) }
        } else {
            _state.update { state -> state.copy(pokitInputErrorMessage = null, pokitName = pokitName) }
        }
    }

    override fun savePokit() {
        val currentState = state.value.copy()
        val needNicknameDuplicateCheck = (currentState.pokitName != originPokitName)
        val nicknameDuplicated = (pokitPaging.pagingData.value.find { it.title == currentState.pokitName } != null)
        if (needNicknameDuplicateCheck && nicknameDuplicated) {
            val errorMessage = errorMessageProvider.errorCodeToMessage(PokitErrorCode.ALREADY_USED_POKIT_NAME)
            _state.update { state -> state.copy(errorToastMessage = errorMessage) }
            return
        }

        _state.update { state -> state.copy(step = AddPokitScreenStep.POKIT_SAVE_LOADING) }

        viewModelScope.launch(defaultDispatcher) {
            val response = when(currentState.pokitUpdateType) {
                PokitUpdateType.Create -> {
                    createPokitUseCase.createPokit(
                        name = currentState.pokitName,
                        imageId = currentState.pokitImage?.id ?: 0
                    )
                }
                is PokitUpdateType.Modify -> {
                    modifyPokitUseCase.modifyPokit(
                        pokitId = currentState.pokitUpdateType.pokitId,
                        name = currentState.pokitName,
                        imageId = currentState.pokitImage?.id ?: 0
                    )
                }
            }

            if (response is PokitResult.Success) {
                _state.update { state -> state.copy(step = AddPokitScreenStep.IDLE) }

                when(currentState.pokitUpdateType) {
                    PokitUpdateType.Create -> {
                        PokitUpdateEvent.createPokit(
                            PokitArg(
                                id = response.result,
                                title = currentState.pokitName,
                                imageUrl = currentState.pokitImage?.url ?: "",
                                imageId = currentState.pokitImage?.id ?: 0
                            )
                        )
                    }
                    is PokitUpdateType.Modify -> {
                        PokitUpdateEvent.updatePokit(
                            PokitArg(
                                id = currentState.pokitUpdateType.pokitId,
                                title = currentState.pokitName,
                                imageUrl = currentState.pokitImage?.url ?: "",
                                imageId = currentState.pokitImage?.id ?: 0
                            )
                        )
                    }
                }

                _sideEffect.emit(AddPokitSideEffect.OnNavigationBack)
            } else {
                response as PokitResult.Error
                val errorMessage = errorMessageProvider.errorCodeToMessage(response.error.code)
                _state.update { state -> state.copy(errorToastMessage = errorMessage, step = AddPokitScreenStep.IDLE) }
            }
        }
    }

    override fun showPokitProfileImageSelectBottomSheet() {
        _state.update { state -> state.copy(step = AddPokitScreenStep.SELECT_PROFILE) }
    }

    override fun hidePokitProfileImageSelectBottomSheet() {
        _state.update { state -> state.copy(step = AddPokitScreenStep.IDLE) }
    }

    override fun setPokitProfileImage(pokitImage: PokitImage) {
        _state.update { state -> state.copy(pokitImage = pokitImage) }
    }

    override fun hideToastMessage() {
        _state.update { state -> state.copy(errorToastMessage = null) }
    }
}
