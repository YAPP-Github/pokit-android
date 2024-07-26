package pokitmons.pokit.domain.model.auth

data class SNSLogin(
    val accessToken: String,
    val refreshToken: String,
)
