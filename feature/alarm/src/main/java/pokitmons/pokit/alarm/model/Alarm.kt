package pokitmons.pokit.alarm.model

import pokitmons.pokit.domain.model.alert.Alarm as DomainAlarm

data class Alarm(
    val id: String = "",
    val contentId: String = "",
    val title: String = "",
    val thumbnail: String? = null,
    val createdAt: Date = Date(),
    val read: Boolean = false,
) {
    companion object {
        fun fromDomainAlarm(domainAlarm: DomainAlarm): Alarm {
            return Alarm(
                id = domainAlarm.id.toString(),
                contentId = domainAlarm.contentId.toString(),
                title = domainAlarm.title,
                thumbnail = domainAlarm.thumbnail,
                createdAt = Date.getInstanceFromString(domainAlarm.createdAt)
            )
        }
    }
}
