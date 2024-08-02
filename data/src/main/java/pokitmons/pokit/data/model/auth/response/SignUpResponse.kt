package pokitmons.pokit.data.model.auth.response

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val id: Int,
    val email: String,
    val nickname: String
)
