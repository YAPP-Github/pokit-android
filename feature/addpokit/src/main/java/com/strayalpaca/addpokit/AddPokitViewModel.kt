package com.strayalpaca.addpokit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.AddPokitScreenStep
import com.strayalpaca.addpokit.model.AddPokitSideEffect
import com.strayalpaca.addpokit.model.PokitInputErrorMessage
import com.strayalpaca.addpokit.model.samplePokitList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class AddPokitViewModel : ContainerHost<AddPokitScreenState, AddPokitSideEffect>, ViewModel() {
    override val container: Container<AddPokitScreenState, AddPokitSideEffect> = container(AddPokitScreenState())

    private val _pokitName = MutableStateFlow("")
    val pokitName : StateFlow<String> = _pokitName.asStateFlow()

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

    fun inputPokitName(pokitName : String) {
        _pokitName.update { pokitName }

        intent {
            val errorMessage = if (pokitName.length > 10) {
                PokitInputErrorMessage.TEXT_LENGTH_LIMIT
            } else {
                null
            }
            reduce { state.copy(pokitInputErrorMessage = errorMessage) }
        }
    }

    fun savePokit() {

    }

    fun onBackPressed() {

    }
}
