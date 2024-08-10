package pokitmons.pokit.data.model.pokit.response

import kotlinx.serialization.Serializable

@Serializable
data class GetPokitCountResponse(
    val categoryTotalCount: Int = 0
)
