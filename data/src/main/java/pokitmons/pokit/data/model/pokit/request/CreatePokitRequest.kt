package pokitmons.pokit.data.model.pokit.request

import kotlinx.serialization.Serializable

@Serializable
data class CreatePokitRequest(
    val categoryName : String = "",
    val categoryImageId : Int = 0
)
