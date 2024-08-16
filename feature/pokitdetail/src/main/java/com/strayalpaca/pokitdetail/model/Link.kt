package com.strayalpaca.pokitdetail.model

import com.strayalpaca.pokitdetail.R
import pokitmons.pokit.domain.model.link.Link as DomainLink

data class Link(
    val id: String = "",
    val title: String = "",
    val dateString: String = "",
    val domainUrl: String = "",
    val isRead: Boolean = false,
    val linkType: LinkType = LinkType.TEXT,
    val url: String = "",
    val memo: String = "",
    val bookmark: Boolean = false,
    val imageUrl: String? = null,
    val createdAt: String = ""
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
                createdAt = domainLink.createdAt
            )
        }
    }
}

enum class LinkType(val textResourceId: Int) {
    TEXT(R.string.badge_text),
}

internal val sampleLinkList = listOf(
    Link(
        id = "1",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.04.12",
        domainUrl = "youtu.be",
        isRead = true,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "2",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.05.12",
        domainUrl = "youtu.be",
        isRead = false,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "3",
        title = "포킷포킷",
        imageUrl = null,
        dateString = "2024.04.12",
        domainUrl = "pokitmons.pokit",
        isRead = true,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "4",
        title = "자연 친화적인 라이프스타일을 위한 환경 보호 방법",
        imageUrl = null,
        dateString = "2024.06.12",
        domainUrl = "youtu.be",
        isRead = true,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    ),
    Link(
        id = "5",
        title = "마지막 링크입니다.",
        imageUrl = null,
        dateString = "2024.07.14",
        domainUrl = "youtu.be",
        isRead = false,
        linkType = LinkType.TEXT,
        url = "",
        memo = "",
        bookmark = true
    )
)
