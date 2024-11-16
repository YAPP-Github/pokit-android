package pokitmons.pokit.linklist.model

import pokitmons.pokit.domain.model.home.remind.RemindResult

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
    val createdAt: String = "",
) {
    companion object {
        fun fromRemindResult(remindResult: RemindResult): Link {
            return Link(
                id = remindResult.id.toString(),
                title = remindResult.title,
                dateString = remindResult.createdAt,
                domainUrl = remindResult.domain,
                url = remindResult.data,
                imageUrl = remindResult.thumbNail,
                bookmark = true,
                isRead = remindResult.isRead
            )
        }

        fun fromDomainLink(domainLink: pokitmons.pokit.domain.model.link.Link): Link {
            return Link(
                id = domainLink.id.toString(),
                title = domainLink.title,
                dateString = domainLink.createdAt,
                domainUrl = domainLink.domain,
                isRead = domainLink.isRead,
                url = domainLink.data,
                memo = domainLink.memo,
                imageUrl = domainLink.thumbnail,
                createdAt = domainLink.createdAt,
                pokitName = domainLink.categoryName,
                pokitId = domainLink.categoryId.toString(),
                bookmark = domainLink.favorites
            )
        }

    }
}
