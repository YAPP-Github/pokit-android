package pokitmons.pokit.data.model.shared

import kotlinx.serialization.Serializable
import pokitmons.pokit.data.model.link.response.GetLinksResponse

@Serializable
data class GetSharedLinksResponse(
    val category: Category,
    val contents: Contents
) {
    @Serializable
    data class Category(
        val categoryId: Int,
        val categoryImageId: Int,
        val categoryImageUrl: String,
        val categoryName: String,
        val contentCount: Int
    )

    @Serializable
    data class Contents(
        val data: List<Data>,
        val hasNext: Boolean,
        val page: Int,
        val size: Int,
        val sort: List<GetLinksResponse.Sort>
    ) {
        @Serializable
        data class Data(
            val contentId: Int,
            val createdAt: String,
            val data: String,
            val domain: String,
            val memo: String,
            val thumbNail: String,
            val title: String
        )
    }
}
