package pokitmons.pokit.data.repository.auth

import pokitmons.pokit.data.datasource.remote.auth.AuthDataSource
import pokitmons.pokit.data.mapper.auth.AuthMapper
import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.request.SignUpRequest
import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.data.model.auth.response.SignUpResponse
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.auth.DuplicateNicknameResult
import pokitmons.pokit.domain.model.auth.SNSLoginResult
import pokitmons.pokit.domain.model.auth.SignUpResult
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun snsLogin(
        authPlatform: String,
        idToken: String,
    ): PokitResult<SNSLoginResult> {
        return runCatching {
            val snsLoginResponse: SNSLoginResponse = remoteAuthDataSource.snsLogin(SNSLoginRequest(authPlatform = authPlatform, idToken = idToken))
            val snsLoginMapper = AuthMapper.mapperToSNSLogin(snsLoginResponse)
            PokitResult.Success(snsLoginMapper)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun checkDuplicateNickname(nickname: String): PokitResult<DuplicateNicknameResult> {
        return runCatching {
            val checkDuplicateNicknameResponse: DuplicateNicknameResponse = remoteAuthDataSource.checkDuplicateNickname(nickname)
            val checkDuplicateMapper = AuthMapper.mapperToDuplicateNickname(checkDuplicateNicknameResponse)
            PokitResult.Success(checkDuplicateMapper)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun signUp(nickname: String, categories: List<String>): PokitResult<SignUpResult> {
        return runCatching {
            val signUpResponse: SignUpResponse = remoteAuthDataSource.signUp(SignUpRequest(nickname = nickname, interests = categories))
            val signUpMapper: SignUpResult = AuthMapper.mapperToSignUp(signUpResponse)
            PokitResult.Success(signUpMapper)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
