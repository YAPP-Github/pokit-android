package pokitmons.pokit.domain.model.auth

data class SNSLoginResult(
    val accessToken: String,
    val refreshToken: String,
)
