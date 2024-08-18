package pokitmons.pokit.domain.model.alert

data class Alarm(
    val id: Int,
    val contentId: Int,
    val thumbnail: String? = null,
    val title: String,
    val createdAt: String,
)
