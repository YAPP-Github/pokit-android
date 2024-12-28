package com.strayalpaca.addpokit.model

import androidx.compose.runtime.Immutable

@Immutable
data class AddPokitScreenState(
    val pokitInputErrorMessage: String? = null,
    val step: AddPokitScreenStep = AddPokitScreenStep.IDLE,
    val pokitImage: PokitImage? = null,
    val errorToastMessage: String? = null,
    val pokitName: String = "",
    val pokitProfileImages: List<PokitImage>? = null,
    val pokitUpdateType: PokitUpdateType = PokitUpdateType.Create
) {
    val saveButtonEnable
        get() = step != AddPokitScreenStep.POKIT_SAVE_LOADING &&
            pokitInputErrorMessage == null &&
            errorToastMessage == null &&
            pokitImage != null &&
            pokitName.isNotEmpty()
}

sealed class PokitUpdateType {
    data object Create : PokitUpdateType()
    data class Modify(val pokitId: Int) : PokitUpdateType()
}

sealed class AddPokitScreenStep {
    data object IDLE : AddPokitScreenStep()
    data object POKIT_SAVE_LOADING : AddPokitScreenStep()
    data object SELECT_PROFILE : AddPokitScreenStep()
}

sealed class AddPokitSideEffect {
    data object OnNavigationBack : AddPokitSideEffect()
}
