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
import com.strayalpaca.addpokit.utils.ErrorMessageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
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
class AddPokitViewModel @Inject constructor(
    private val getPokitImagesUseCase: GetPokitImagesUseCase,
    private val getPokitsUseCase: GetPokitsUseCase,
    private val getPokitUseCase: GetPokitUseCase,
    private val createPokitUseCase: CreatePokitUseCase,
    private val modifyPokitUseCase: ModifyPokitUseCase,
    private val errorMessageProvider: ErrorMessageProvider,
    savedStateHandle: SavedStateHandle,
) : ContainerHost<AddPokitScreenState, AddPokitSideEffect>, ViewModel() {
    override val container: Container<AddPokitScreenState, AddPokitSideEffect> = container(AddPokitScreenState())

    private val pokitId = savedStateHandle.get<String>("pokit_id")?.toIntOrNull()

    private val pokitPagingSource = object: PagingSource<Pokit> {
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

    private val _pokitName = MutableStateFlow("")
    val pokitName: StateFlow<String> = _pokitName.asStateFlow()

    val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitListState: StateFlow<PagingState> = pokitPaging.pagingState

    private val _pokitIamges = MutableStateFlow<List<PokitImage>>(emptyList())
    val pokitImages: StateFlow<List<PokitImage>> = _pokitIamges.asStateFlow()

    private var existingPokitName: String? = null

    init {
        initPokitList()
        loadPokitImages()

        setAddModifyMode(pokitId)
    }

    private fun initPokitList() {
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    fun loadPokitList() {
        viewModelScope.launch {
            pokitPaging.load()
        }
    }

    private fun loadPokitImages() {
        viewModelScope.launch {
            val response = getPokitImagesUseCase.getImages()
            if (response is PokitResult.Success) {
                _pokitIamges.update { response.result.map { PokitImage.fromDomainPokitImage(it) } }

                if (pokitId == null) {
                    val defaultPokitImage = PokitImage.fromDomainPokitImage(response.result[0])
                    intent {
                        reduce { state.copy(pokitImage = defaultPokitImage) }
                    }
                }
            } else {
                // 만약 이미지 로딩이 실패한다면....?
            }
        }
    }

    private fun setAddModifyMode(pokitId: Int?) = intent {
        if (pokitId == null) {
            reduce {
                state.copy(isModify = false)
            }
        } else {
            val response = getPokitUseCase.getPokit(pokitId)
            if (response is PokitResult.Success) {
                reduce {
                    state.copy(isModify = true, pokitImage = PokitImage.fromDomainPokitImage(response.result.image))
                }
                _pokitName.update { response.result.name }
                existingPokitName = response.result.name
            } else {
                postSideEffect(AddPokitSideEffect.OnNavigationBack)
            }
        }
    }

    fun inputPokitName(pokitName: String) {
        _pokitName.update { pokitName }

        intent {
            val isOutOfMaxLength = pokitName.length > POKIT_NAME_MAX_LENGTH

            if (isOutOfMaxLength) {
                val errorMessage = errorMessageProvider.getTextLengthErrorMessage()
                reduce { state.copy(pokitInputErrorMessage = errorMessage) }
            } else {
                reduce { state.copy(pokitInputErrorMessage = null) }
            }
        }
    }

    fun savePokit() = intent {
        // todo 에러 코드 파싱 수정시 제거 필요
        val needNicknameCheck = (pokitName.value != existingPokitName)
        val nicknameDuplicated = (pokitPaging.pagingData.value.find { it.title == pokitName.value } != null)
        if (needNicknameCheck && nicknameDuplicated) {
            val errorMessage = errorMessageProvider.errorCodeToMessage(PokitErrorCode.ALREADY_USED_POKIT_NAME)
            reduce { state.copy(errorToastMessage = errorMessage) }
            return@intent
        }

        reduce {
            state.copy(step = AddPokitScreenStep.POKIT_SAVE_LOADING)
        }

        val currentPokitName = pokitName.value
        val pokitImageId = state.pokitImage?.id ?: 0
        val response = if (pokitId != null) {
            modifyPokitUseCase.modifyPokit(pokitId, currentPokitName, pokitImageId)
        } else {
            createPokitUseCase.createPokit(currentPokitName, pokitImageId)
        }

        if (response is PokitResult.Success) {
            reduce { state.copy(step = AddPokitScreenStep.IDLE) }

            val isCreate = (pokitId == null)
            if (isCreate) {
                PokitUpdateEvent.createPokit(
                    PokitArg(id = response.result, title = currentPokitName, imageUrl = state.pokitImage?.url ?: "", imageId = pokitImageId)
                )
            } else {
                PokitUpdateEvent.updatePokit(
                    PokitArg(id = pokitId ?: 0, title = currentPokitName, imageUrl = state.pokitImage?.url ?: "", imageId = pokitImageId)
                )
            }

            postSideEffect(AddPokitSideEffect.OnNavigationBack)
        } else {
            response as PokitResult.Error
            val errorMessage = errorMessageProvider.errorCodeToMessage(response.error.code)
            reduce { state.copy(errorToastMessage = errorMessage, step = AddPokitScreenStep.IDLE) }
        }
    }

    fun onBackPressed() = intent {
        val currentStep = state.step
        when (currentStep) {
            AddPokitScreenStep.POKIT_SAVE_LOADING -> {} // discard
            AddPokitScreenStep.SELECT_PROFILE -> {
                reduce { state.copy(step = AddPokitScreenStep.IDLE) }
            }
            else -> {
                postSideEffect(AddPokitSideEffect.OnNavigationBack)
            }
        }
    }

    fun showPokitProfileSelectBottomSheet() = intent {
        reduce {
            state.copy(step = AddPokitScreenStep.SELECT_PROFILE)
        }
    }

    fun hidePokitProfileSelectBottomSheet() = intent {
        reduce {
            state.copy(step = AddPokitScreenStep.IDLE)
        }
    }

    fun selectPokitProfile(pokitImage: PokitImage) = intent {
        reduce {
            state.copy(pokitImage = pokitImage)
        }
    }

    fun hideToastMessage() = intent {
        reduce {
            state.copy(errorToastMessage = null)
        }
    }
}
