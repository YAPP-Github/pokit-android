package pokitmons.pokit.data.datasource.remote.setting

import pokitmons.pokit.data.model.setting.reqeust.EditNicknameRequest
import pokitmons.pokit.data.model.setting.response.EditNicknameResponse

interface SettingDataSource {
    suspend fun editNickname(editNicknameRequest: EditNicknameRequest): EditNicknameResponse
}




