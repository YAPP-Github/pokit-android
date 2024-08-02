package pokitmons.pokit.data.model.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val nickname: String,
    val interests: List<String>,
)
