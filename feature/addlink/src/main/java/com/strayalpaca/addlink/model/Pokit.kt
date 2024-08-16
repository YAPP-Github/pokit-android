package com.strayalpaca.addlink.model

import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit

data class Pokit(
    val title: String,
    val id: String,
    val count: Int,
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
