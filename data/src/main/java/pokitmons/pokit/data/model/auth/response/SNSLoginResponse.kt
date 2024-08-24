package pokitmons.pokit.data.model.auth.response

import kotlinx.serialization.Serializable

@Serializable
data class SNSLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean,
)
