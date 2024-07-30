package pokitmons.pokit.data.model.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class SNSLoginRequest(
    val authPlatform: String,
    val idToken: String,
)
