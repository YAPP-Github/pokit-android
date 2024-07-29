package pokitmons.pokit.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class PokitErrorResponse(
    val message: String = "그 외",
    val code: String = "U_0000",
)
