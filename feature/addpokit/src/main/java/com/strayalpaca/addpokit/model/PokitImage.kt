package com.strayalpaca.addpokit.model

import pokitmons.pokit.domain.model.pokit.Pokit.Image as DomainPokitImage

data class PokitImage(
    val id: Int,
    val url: String,
) {
    companion object {
        fun fromDomainPokitImage(domainPokitImage: DomainPokitImage): PokitImage {
            return PokitImage(id = domainPokitImage.id, url = domainPokitImage.url)
        }
    }
}
