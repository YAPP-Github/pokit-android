package pokitmons.pokit.data.repository.auth

import pokitmons.pokit.data.datasource.remote.auth.AuthDataSource
import pokitmons.pokit.data.mapper.auth.AuthMapper
import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.domain.model.auth.SNSLogin
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun snsLogin(
        authPlatform: String,
        idToken: String
    ): Result<SNSLogin> = runCatching {
        val snsLoginRequest = SNSLoginRequest(authPlatform = authPlatform, idToken = idToken)
        val snsLoginResponse = remoteAuthDataSource.snsLogin(snsLoginRequest)
        AuthMapper.mapperToSNSLogin(snsLoginResponse)
    }.onFailure { throwable ->
        throw Exception(throwable)
    }
}
