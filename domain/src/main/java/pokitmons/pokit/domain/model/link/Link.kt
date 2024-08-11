package pokitmons.pokit.domain.model.link

data class Link(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val data: String,
    val domain: String,
    val title: String,
    val memo: String,
    val alertYn: String,
    val createdAt: String,
    val isRead: Boolean,
    val thumbnail: String,
)
