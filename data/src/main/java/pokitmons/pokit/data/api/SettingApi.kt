package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.setting.reqeust.EditNicknameRequest
import pokitmons.pokit.data.model.setting.response.EditNicknameResponse
import retrofit2.http.Body
import retrofit2.http.PUT

interface SettingApi {
    @PUT("user/nickname")
    suspend fun editNickname(
        @Body editNicknameRequest: EditNicknameRequest,
    ): EditNicknameResponse
}
