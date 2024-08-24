package pokitmons.pokit.data.mapper.alert

import pokitmons.pokit.data.model.alert.GetAlertsResponse
import pokitmons.pokit.domain.model.alert.Alarm

object AlertMapper {
    fun mapperToAlarmList(response: GetAlertsResponse): List<Alarm> {
        return response.data.map { data ->
            Alarm(
                id = data.id,
                contentId = data.contentId,
                thumbnail = data.thumbNail,
                title = data.title,
                createdAt = data.createdAt
            )
        }
    }
}
