package pokitmons.pokit.data.mapper.auth

import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.data.model.auth.response.SignUpResponse
import pokitmons.pokit.domain.model.auth.DuplicateNicknameResult
import pokitmons.pokit.domain.model.auth.SNSLoginResult
import pokitmons.pokit.domain.model.auth.SignUpResult

object AuthMapper {
    fun mapperToSNSLogin(snsLoginResponse: SNSLoginResponse): SNSLoginResult {
        return SNSLoginResult(
            accessToken = snsLoginResponse.accessToken,
            refreshToken = snsLoginResponse.refreshToken,
            isRegistered = snsLoginResponse.isRegistered
        )
    }

    fun mapperToDuplicateNickname(checkDuplicateNicknameResponse: DuplicateNicknameResponse): DuplicateNicknameResult {
        return DuplicateNicknameResult(
            isDuplicate = checkDuplicateNicknameResponse.isDuplicate
        )
    }

    fun mapperToSignUp(signUpResponse: SignUpResponse): SignUpResult {
        return SignUpResult(
            id = signUpResponse.id,
            email = signUpResponse.email,
            nickname = signUpResponse.nickname
        )
    }
}
