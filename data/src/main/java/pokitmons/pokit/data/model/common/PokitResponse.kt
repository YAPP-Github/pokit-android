package pokitmons.pokit.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class PokitResponse<T>(
    val data: T,
    val message: String,
    val code: String
)
