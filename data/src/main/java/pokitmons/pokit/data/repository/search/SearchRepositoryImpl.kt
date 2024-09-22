package pokitmons.pokit.data.repository.search

import kotlinx.coroutines.flow.Flow
import pokitmons.pokit.data.datasource.local.search.SearchDataSource
import pokitmons.pokit.domain.repository.search.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource,
) : SearchRepository {
    override fun getRecentSearchWords(): Flow<List<String>> {
        return dataSource.getSearchWord()
    }

    override suspend fun removeSearchWord(word: String) {
        dataSource.removeSearchWord(word)
    }

    override suspend fun removeAllSearchWords() {
        dataSource.removeAllSearchWords()
    }

    override suspend fun setUseRecentSearchWord(use: Boolean): Boolean {
        return dataSource.setUseRecentSearchWord(use = use)
    }

    override fun getUseRecentSearchWord(): Flow<Boolean> {
        return dataSource.getUseRecentSearchWord()
    }

    override suspend fun addRecentSearchWord(word: String) {
        dataSource.addSearchWord(word)
    }
}
