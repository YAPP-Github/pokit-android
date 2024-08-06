package pokitmons.pokit.data.model.link.response

import kotlinx.serialization.Serializable

@Serializable
data class GetLinksResponse(
    val data: List<Data>,
    val page: Int,
    val size: Int,
    val sort: List<Sort>,
    val hasNext: Boolean
) {
    @Serializable
    data class Data(
        val contentId: Int,
        val categoryId: Int,
        val categoryName: String,
        val data: String,
        val domain: String,
        val title: String,
        val memo: String,
        val alertYn: String,
        val createdAt: String,
        val isRead: Boolean,
        val thumbNail: String
    )

    @Serializable
    data class Sort(
        val direction: String,
        val nullHandling: String,
        val ascending: Boolean,
        val property: String,
        val ignoreCase: Boolean
    )
}
