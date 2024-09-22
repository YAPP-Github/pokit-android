package com.strayalpaca.pokitdetail.model

data class Filter(
    val recentSortUsed: Boolean = true,
    val bookmarkChecked: Boolean = false,
    val notReadChecked: Boolean = false,
)
