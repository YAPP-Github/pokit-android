package com.strayalpaca.addpokit.model

import com.strayalpaca.addpokit.R

data class AddPokitScreenState(
    val pokitInputErrorMessage: PokitInputErrorMessage? = null,
    val pokitList : List<Pokit> = emptyList(),
    val step : AddPokitScreenStep = AddPokitScreenStep.POKIT_LIST_LOADING
)

enum class AddPokitScreenStep {
    IDLE, POKIT_LIST_LOADING, POKIT_SAVE_LOADING, SELECT_POKIT_IMAGE
}

sealed class AddPokitSideEffect {
    data object AddPokitSuccess : AddPokitSideEffect()
    data object OnNavigationBack : AddPokitSideEffect()
}

enum class PokitInputErrorMessage(val resourceId : Int) {
    TEXT_LENGTH_LIMIT(R.string.text_length_limit_format),
    ALREADY_USED_POKIT_NAME(R.string.already_used_pokit_name)
}
