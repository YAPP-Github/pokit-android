package pokitmons.pokit.data.mapper.link

import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.domain.model.link.Link

object LinkMapper {
    fun mapperToLinks(linksResponse: GetLinksResponse): List<Link> {
        return linksResponse.data.map { data ->
            Link(
                id = data.contentId,
                categoryId = data.category.categoryId,
                categoryName = data.category.categoryName,
                data = data.data,
                domain = data.domain,
                title = data.title,
                memo = data.memo,
                alertYn = data.alertYn,
                createdAt = data.createdAt,
                isRead = data.isRead,
                thumbnail = data.thumbNail
            )
        }
    }
}
