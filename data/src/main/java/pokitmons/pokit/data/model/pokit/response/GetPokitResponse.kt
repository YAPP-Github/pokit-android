package pokitmons.pokit.data.model.pokit.response

import kotlinx.serialization.Serializable

@Serializable
data class GetPokitResponse(
    val categoryId: Int = 0,
    val categoryName: String = "",
    val categoryImage: Image = Image(),
    val createdAt: String = ""
) {
    @Serializable
    data class Image(
        val imageId: Int = 0,
        val imageUrl: String = "",
    )
}
