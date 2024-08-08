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
import com.strayalpaca.addpokit.model.PokitInputErrorMessage
import com.strayalpaca.addpokit.paging.PokitPaging
import com.strayalpaca.addpokit.paging.SimplePagingState
import dagger.hilt.android.lifecycle.HiltViewModel
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
import pokitmons.pokit.domain.usecase.pokit.GetPokitImagesUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit
import javax.inject.Inject

@HiltViewModel
class AddPokitViewModel @Inject constructor(
    private val getPokitImagesUseCase: GetPokitImagesUseCase,
    private val getPokitsUseCase: GetPokitsUseCase,
    private val getPokitUseCase: GetPokitUseCase,
    savedStateHandle: SavedStateHandle
) : ContainerHost<AddPokitScreenState, AddPokitSideEffect>, ViewModel() {
    override val container: Container<AddPokitScreenState, AddPokitSideEffect> = container(AddPokitScreenState())

    private val pokitPaging = PokitPaging(
        getPokits = ::getPokits,
        perPage = 10,
        coroutineScope = viewModelScope,
        initPage = 0
    )

    private val _pokitName = MutableStateFlow("")
    val pokitName: StateFlow<String> = _pokitName.asStateFlow()

    val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitListState : StateFlow<SimplePagingState> = pokitPaging.pagingState

    private val _pokitIamges = MutableStateFlow<List<PokitImage>>(emptyList())
    val pokitImages : StateFlow<List<PokitImage>> = _pokitIamges.asStateFlow()

    init {
        loadPokitList()
        loadPokitImages()

        val pokitId = savedStateHandle.get<String>("pokit_id")?.toIntOrNull()
        setAddModifyMode(pokitId)
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
            } else {
                postSideEffect(AddPokitSideEffect.OnNavigationBack)
            }
        }

    }

    private suspend fun getPokits(size: Int, page: Int) : PokitResult<List<DomainPokit>> {
        return getPokitsUseCase.getPokits(size = size, page = page)
    }

    fun inputPokitName(pokitName: String) {
        _pokitName.update { pokitName }

        intent {
            val isInAvailableLength = pokitName.length > POKIT_NAME_MAX_LENGTH
            val isDuplicatePokitName = false // api로 대체 필요

            val errorMessage = if (isInAvailableLength) {
                PokitInputErrorMessage.TEXT_LENGTH_LIMIT
            } else if (isDuplicatePokitName) {
                PokitInputErrorMessage.ALREADY_USED_POKIT_NAME
            } else {
                null
            }
            reduce { state.copy(pokitInputErrorMessage = errorMessage) }
        }
    }

    fun savePokit() = intent {
        reduce {
            state.copy(step = AddPokitScreenStep.POKIT_SAVE_LOADING)
        }
        // todo 포킷 저장 api 연동
        delay(1000L)
        reduce {
            state.copy(step = AddPokitScreenStep.IDLE)
        }
        postSideEffect(AddPokitSideEffect.AddPokitSuccess)
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
}
