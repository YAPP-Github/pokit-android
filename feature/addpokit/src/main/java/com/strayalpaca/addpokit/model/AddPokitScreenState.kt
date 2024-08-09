package com.strayalpaca.addpokit.model

import androidx.compose.runtime.Immutable

@Immutable
data class AddPokitScreenState(
    val pokitInputErrorMessage: String? = null,
    val step: AddPokitScreenStep = AddPokitScreenStep.IDLE,
    val pokitImage: PokitImage? = null,
    val isModify: Boolean = false
)

sealed class AddPokitScreenStep {
    data object IDLE : AddPokitScreenStep()
    data object POKIT_SAVE_LOADING : AddPokitScreenStep()
    data object SELECT_PROFILE : AddPokitScreenStep()
}

sealed class AddPokitSideEffect {
    data object AddPokitSuccess : AddPokitSideEffect()
    data object OnNavigationBack : AddPokitSideEffect()
}

