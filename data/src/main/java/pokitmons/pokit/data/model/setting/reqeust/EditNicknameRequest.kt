package pokitmons.pokit.data.model.setting.reqeust

import kotlinx.serialization.Serializable

@Serializable
data class EditNicknameRequest(
    val nickname: String,
)
