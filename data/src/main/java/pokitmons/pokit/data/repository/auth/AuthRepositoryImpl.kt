package pokitmons.pokit.data.repository.auth

import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pokitmons.pokit.data.datasource.remote.auth.AuthDataSource
import pokitmons.pokit.data.mapper.auth.AuthMapper
import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.data.model.common.PokitErrorResponse
import pokitmons.pokit.domain.commom.PokitError
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.auth.DuplicateNicknameResult
import pokitmons.pokit.domain.model.auth.SNSLoginResult
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
            try {
                val error: PokitErrorResponse = throwable.message?.let { errorBody ->
                    Json.decodeFromString<PokitErrorResponse>(errorBody)
                } ?: PokitErrorResponse()
                val pokitError = PokitError(message = error.message, code = error.code)
                PokitResult.Error(pokitError)
            } catch (e: Exception) {
                PokitResult.Error(PokitError())
            }
        }
    }

    override suspend fun checkDuplicateNickname(nickname: String): PokitResult<DuplicateNicknameResult> {
        TODO("Not yet implemented")
    }
}

