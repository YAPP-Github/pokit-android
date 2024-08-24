package pokitmons.pokit.domain.usecase.setting

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.setting.EditNicknameResult
import pokitmons.pokit.domain.repository.setting.SettingRepository
import javax.inject.Inject

class EditNicknameUseCase @Inject constructor(private val settingRepository: SettingRepository) {
    suspend fun editNickname(nickname: String): PokitResult<EditNicknameResult> {
        return when (val editNicknameResult = settingRepository.editNickname(nickname)) {
            is PokitResult.Success -> PokitResult.Success(editNicknameResult.result)
            is PokitResult.Error -> PokitResult.Error(editNicknameResult.error)
        }
    }
}
