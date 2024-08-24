package pokitmons.pokit.data.datasource.remote.setting

import pokitmons.pokit.data.api.SettingApi
import pokitmons.pokit.data.model.setting.reqeust.EditNicknameRequest
import pokitmons.pokit.data.model.setting.response.EditNicknameResponse
import javax.inject.Inject

class RemoteSettingDataSourceImpl @Inject constructor(private val settingApi: SettingApi) : SettingDataSource {
    override suspend fun editNickname(editNicknameRequest: EditNicknameRequest): EditNicknameResponse {
        return settingApi.editNickname(editNicknameRequest)
    }
}
