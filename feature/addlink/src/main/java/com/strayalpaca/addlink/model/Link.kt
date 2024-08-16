package com.strayalpaca.addlink.model

import pokitmons.pokit.domain.model.link.Link as DomainLink
import pokitmons.pokit.domain.model.link.LinkCard as DomainLinkCard

data class Link(
    val url: String,
    val title: String,
    val imageUrl: String?,
) {
    companion object {
        fun fromDomainLink(domainLink : DomainLink) : Link {
            return Link(
                url = domainLink.data,
                title = domainLink.title,
                imageUrl = domainLink.thumbnail
            )
        }

        fun fromDomainLinkCard(domainLinkCard : DomainLinkCard): Link {
            return Link(
                url = domainLinkCard.url,
                title = domainLinkCard.title,
                imageUrl = domainLinkCard.thumbnailUrl
            )
        }
    }
}
