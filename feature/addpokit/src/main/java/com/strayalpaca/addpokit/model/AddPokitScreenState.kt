package com.strayalpaca.addpokit.model

import androidx.compose.runtime.Immutable
import com.strayalpaca.addpokit.R

@Immutable
data class AddPokitScreenState(
    val pokitInputErrorMessage: PokitInputErrorMessage? = null,
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

enum class PokitInputErrorMessage(val resourceId: Int) {
    TEXT_LENGTH_LIMIT(R.string.text_length_limit_format),
    ALREADY_USED_POKIT_NAME(R.string.already_used_pokit_name),
}
