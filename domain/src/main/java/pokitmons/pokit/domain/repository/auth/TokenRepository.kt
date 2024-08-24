package pokitmons.pokit.domain.repository.auth

interface TokenRepository {
    suspend fun setAccessToken(token: String)
    suspend fun setRefreshToken(token: String)
}
