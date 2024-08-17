package pokitmons.pokit.data.model.pokit.response

import kotlinx.serialization.Serializable

@Serializable
data class GetPokitsResponse(
    val data: List<Data> = emptyList(),
    val page: Int = 10,
    val size: Int = 0,
    val sort: List<Sort> = emptyList(),
    val hasNext: Boolean = false,
) {
    @Serializable
    data class Data(
        val categoryId: Int,
        val userId: Int,
        val categoryName: String,
        val categoryImage: PokitImage,
        val contentCount: Int,
        val createdAt: String,
    )

    @Serializable
    data class PokitImage(
        val imageId: Int,
        val imageUrl: String,
    )

    @Serializable
    data class Sort(
        val direction: String,
        val nullHandling: String,
        val ascending: Boolean,
        val property: String,
        val ignoreCase: Boolean,
    )
}
