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
    }
}
