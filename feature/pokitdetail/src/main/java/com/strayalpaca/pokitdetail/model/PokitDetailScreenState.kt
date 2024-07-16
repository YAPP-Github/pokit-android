package com.strayalpaca.pokitdetail.model

data class PokitDetailScreenState(
    val currentPokit: Pokit = Pokit(),
    val currentFilter: Filter = Filter(),
    val currentLink: Link? = null,
    val linkBottomSheetType: BottomSheetType? = null,
    val pokitBottomSheetType: BottomSheetType? = null,
    val filterChangeBottomSheetVisible: Boolean = false,
    val pokitSelectBottomSheetVisible: Boolean = false,
    val linkDetailBottomSheetVisible: Boolean = false,
    val enable : Boolean = true
)

enum class BottomSheetType {
    MODIFY, REMOVE
}
