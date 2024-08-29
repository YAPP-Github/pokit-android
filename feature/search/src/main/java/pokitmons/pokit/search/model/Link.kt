package pokitmons.pokit.search.model

import pokitmons.pokit.domain.model.link.Link as DomainLink

data class Link(
    val id: String = "",
    val title: String = "",
    val dateString: String = "",
    val domainUrl: String = "",
    val isRead: Boolean = false,
    val pokitName: String = "",
    val pokitId: String = "",
    val url: String = "",
    val memo: String = "",
    val bookmark: Boolean = false,
    val imageUrl: String? = null,
) {
    companion object {
        fun fromDomainLink(domainLink: DomainLink): Link {
            return Link(
                id = domainLink.id.toString(),
                title = domainLink.title,
                dateString = domainLink.createdAt,
                domainUrl = domainLink.domain,
                isRead = domainLink.isRead,
                url = domainLink.data,
                memo = domainLink.memo,
                imageUrl = domainLink.thumbnail,
                bookmark = domainLink.favorites,
                pokitName = domainLink.categoryName,
                pokitId = domainLink.categoryId.toString()
            )
        }
    }
}
