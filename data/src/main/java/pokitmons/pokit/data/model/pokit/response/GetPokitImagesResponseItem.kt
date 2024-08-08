package pokitmons.pokit.data.model.pokit.response

import kotlinx.serialization.Serializable

@Serializable
data class GetPokitImagesResponseItem(
    val imageId: Int,
    val imageUrl: String
)
