package pokitmons.pokit.data.mapper.auth

import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.domain.model.auth.SNSLogin

object AuthMapper {
    fun mapperToSNSLogin(snsLoginResponse: SNSLoginResponse): SNSLogin {
        return SNSLogin(
            accessToken = snsLoginResponse.accessToken,
            refreshToken = snsLoginResponse.refreshToken
        )
    }
}
