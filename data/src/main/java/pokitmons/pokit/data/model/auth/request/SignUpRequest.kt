package pokitmons.pokit.data.model.auth.request

data class SignUpRequest(
    val nickname: String,
    val interests: List<String>,
)
