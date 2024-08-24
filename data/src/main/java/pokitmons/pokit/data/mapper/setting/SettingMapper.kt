package pokitmons.pokit.data.mapper.setting

import pokitmons.pokit.data.model.setting.response.EditNicknameResponse
import pokitmons.pokit.domain.model.setting.EditNicknameResult

object SettingMapper {
    fun mapperToEditNickname(editNicknameResponse: EditNicknameResponse): EditNicknameResult {
        return EditNicknameResult(
            id = editNicknameResponse.id,
            nickname = editNicknameResponse.nickname,
            email = editNicknameResponse.email
        )
    }
}
