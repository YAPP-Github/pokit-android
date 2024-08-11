package com.strayalpaca.addpokit.model

data class Pokit(
    val title: String,
    val id: String,
    val count: Int,
) {
    companion object {
        fun fromDomainPokit(pokit: pokitmons.pokit.domain.model.pokit.Pokit): Pokit {
            return Pokit(
                title = pokit.name,
                id = pokit.categoryId.toString(),
                count = pokit.linkCount
            )
        }
    }
}
