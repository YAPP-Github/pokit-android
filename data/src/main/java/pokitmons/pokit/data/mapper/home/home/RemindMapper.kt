package pokitmons.pokit.data.mapper.home.home

import pokitmons.pokit.data.model.home.remind.Remind
import pokitmons.pokit.data.model.home.remind.RemindResponse
import pokitmons.pokit.domain.model.home.remind.RemindResult

object RemindMapper {
    fun mapperToRemind(remindResponse: RemindResponse): List<RemindResult> {
        return remindResponse.data.map { remind ->
            RemindResult(
                id = remind.contentId,
                title = remind.title,
                domain = remind.domain,
                createdAt = remind.createdAt,
                isRead = remind.isRead,
                thumbNail = remind.thumbNail,
                data = remind.data
            )
        }
    }

    fun mapperToTodayContents(remindResponse: List<Remind>): List<RemindResult> {
        return remindResponse.map { remind ->
            RemindResult(
                id = remind.contentId,
                title = remind.title,
                domain = remind.domain,
                createdAt = remind.createdAt,
                isRead = remind.isRead,
                thumbNail = remind.thumbNail,
                data = remind.data
            )
        }
    }
}
