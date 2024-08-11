package com.strayalpaca.pokitdetail.model

import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit

data class Pokit(
    val title: String = "",
    val id: String = "",
    val count: Int = 0,
) {
    companion object {
        fun fromDomainPokit(pokit: DomainPokit): Pokit {
            return Pokit(
                title = pokit.name,
                id = pokit.categoryId.toString(),
                count = pokit.linkCount
            )
        }
    }
}

internal val samplePokitList = listOf(
    Pokit(title = "안드로이드", id = "1", count = 2),
    Pokit(title = "IOS", id = "2", count = 2),
    Pokit(title = "디자인", id = "3", count = 2),
    Pokit(title = "PM", id = "4", count = 1),
    Pokit(title = "서버", id = "5", count = 2)
)
