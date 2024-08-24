package pokitmons.pokit.domain.model.home.remind

data class RemindResult(
    val id: Int,
    val title: String,
    val domain: String,
    val createdAt: String,
    val isRead: Boolean,
    val thumbNail: String,
    val data: String,
)
