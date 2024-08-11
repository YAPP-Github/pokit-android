package pokitmons.pokit.data.model.pokit.response

import kotlinx.serialization.Serializable

@Serializable
data class ModifyPokitResponse(
    val categoryId: Int = 0,
    val categoryName: String = "",
    val categoryImage: Image = Image(),
) {
    @Serializable
    data class Image(
        val imageId: Int = 0,
        val imageUrl: String = "",
    )
}
