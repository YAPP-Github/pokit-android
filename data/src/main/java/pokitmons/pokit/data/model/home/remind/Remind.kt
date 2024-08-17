package pokitmons.pokit.data.model.home.remind

import kotlinx.serialization.Serializable

@Serializable
data class Remind(
    val category: Category,
    val contentId: Int,
    val createdAt: String,
    val data: String,
    val domain: String,
    val isRead: Boolean,
    val thumbNail: String,
    val title: String,
)
