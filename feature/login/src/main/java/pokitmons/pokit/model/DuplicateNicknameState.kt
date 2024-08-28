package pokitmons.pokit.model

data class DuplicateNicknameState(
    val nickname: String = "",
    val isDuplicate: Boolean = false,
    val isRegex: Boolean = false
)
