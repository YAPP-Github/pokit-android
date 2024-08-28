package pokitmons.pokit.data.model.home.remind

import kotlinx.serialization.Serializable

@Serializable
data class Remind(
    val category: Category,
    val contentId: Int,
    val createdAt: String,
    val data: String,
    val domain: String,
    val isRead: Boolean = false, // today의 경우 isRead가 빠져서 오기에, 기본값 설정 필요
    val thumbNail: String,
    val title: String,
)
