package pokitmons.pokit.data.model.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class WithdrawRequest(
    val refreshToken: String,
    val authPlatform: String,
)
