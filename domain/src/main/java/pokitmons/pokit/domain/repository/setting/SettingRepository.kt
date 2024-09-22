package pokitmons.pokit.domain.repository.setting

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.setting.EditNicknameResult

interface SettingRepository {
    suspend fun editNickname(nickname: String): PokitResult<EditNicknameResult>
}
