package com.strayalpaca.addpokit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.addpokit.const.POKIT_NAME_MAX_LENGTH
import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.AddPokitScreenStep
import com.strayalpaca.addpokit.model.AddPokitSideEffect
import com.strayalpaca.addpokit.model.PokitInputErrorMessage
import com.strayalpaca.addpokit.model.PokitProfile
import com.strayalpaca.addpokit.model.samplePokitList
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
import javax.inject.Inject

@HiltViewModel
class AddPokitViewModel @Inject constructor() : ContainerHost<AddPokitScreenState, AddPokitSideEffect>, ViewModel() {
    override val container: Container<AddPokitScreenState, AddPokitSideEffect> = container(AddPokitScreenState())

    private val _pokitName = MutableStateFlow("")
    val pokitName: StateFlow<String> = _pokitName.asStateFlow()

    init {
        loadPokitList()
    }

    private fun loadPokitList() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(
                    step = AddPokitScreenStep.POKIT_LIST_LOADING,
                    pokitInputErrorMessage = null,
                    pokitList = emptyList()
                )
            }
            // todo 포킷 리스트 로드 api 연동
            delay(1000L)

            reduce {
                state.copy(
                    step = AddPokitScreenStep.IDLE,
                    pokitList = samplePokitList
                )
            }
        }
    }

    fun inputPokitName(pokitName: String) {
        _pokitName.update { pokitName }

        intent {
            val isInAvailableLength = pokitName.length > POKIT_NAME_MAX_LENGTH
            val isDuplicatePokitName = state.pokitList.find { it.title == pokitName } != null

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

    fun selectPoktiProfile(pokitProfile: PokitProfile) = intent {
        reduce {
            state.copy(pokitProfile = pokitProfile)
        }
    }
}
