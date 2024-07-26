package pokitmons.pokit.data.model.auth.response

import kotlinx.serialization.Serializable

@Serializable
data class DuplicateNicknameResponse(
    val isDuplicate: Boolean,
)
