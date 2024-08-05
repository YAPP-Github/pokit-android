package pokitmons.pokit.data.model.setting.response

import kotlinx.serialization.Serializable

@Serializable
data class EditNicknameResponse(
    val email: String,
    val id: Int,
    val nickname: String
)
