package pokitmons.pokit.domain.repository.search

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getRecentSearchWords(): Flow<List<String>>
    suspend fun removeSearchWord(word: String)
    suspend fun removeAllSearchWords()
    suspend fun setUseRecentSearchWord(use: Boolean): Boolean
    fun getUseRecentSearchWord(): Flow<Boolean>
    suspend fun addRecentSearchWord(word: String)
}
