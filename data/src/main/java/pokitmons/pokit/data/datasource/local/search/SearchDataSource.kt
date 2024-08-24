package pokitmons.pokit.data.datasource.local.search

import kotlinx.coroutines.flow.Flow

interface SearchDataSource {
    fun getSearchWord(): Flow<List<String>>
    suspend fun addSearchWord(searchWord: String)
    suspend fun removeSearchWord(searchWord: String)
    suspend fun removeAllSearchWords()
    suspend fun setUseRecentSearchWord(use: Boolean): Boolean
    fun getUseRecentSearchWord(): Flow<Boolean>
}
