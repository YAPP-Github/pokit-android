package pokitmons.pokit.data.mapper.auth

import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.domain.model.auth.DuplicateNicknameResult
import pokitmons.pokit.domain.model.auth.SNSLoginResult

object AuthMapper {
    fun mapperToSNSLogin(snsLoginResponse: SNSLoginResponse): SNSLoginResult {
        return SNSLoginResult(
            accessToken = snsLoginResponse.accessToken,
            refreshToken = snsLoginResponse.refreshToken
        )
    }

    fun mapperToDuplicateNickname(checkDuplicateNicknameResponse: DuplicateNicknameResponse): DuplicateNicknameResult {
        return DuplicateNicknameResult(
            isDuplicate = checkDuplicateNicknameResponse.isDuplicate
        )
    }
}
