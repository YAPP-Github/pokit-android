package pokitmons.pokit.data.model.pokit.request

import kotlinx.serialization.Serializable

@Serializable
data class ModifyPokitRequest(
    val categoryName: String = "",
    val categoryImageId: Int = 0,
)
