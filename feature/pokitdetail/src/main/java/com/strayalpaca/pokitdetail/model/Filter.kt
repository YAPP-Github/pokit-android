package com.strayalpaca.pokitdetail.model

data class Filter(
    val recentSortUsed: Boolean = true,
    val bookmarkChecked: Boolean = true,
    val notReadChecked: Boolean = true,
)
