package pokitmons.pokit.data.datasource.local.search

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pokitmons.pokit.data.room.dao.SearchWordDao
import pokitmons.pokit.data.room.entity.SearchWord
import java.util.Calendar
import javax.inject.Inject

class LocalSearchWordDataSource @Inject constructor(
    private val searchWordDao: SearchWordDao,
    private val sharedPreferences: SharedPreferences,
) : SearchDataSource {
    companion object {
        const val USE_RECENT_WORD_SP_KEY = "use_recent_word"
    }

    private val useRecentSearchWords = MutableStateFlow(
        sharedPreferences.getBoolean(USE_RECENT_WORD_SP_KEY, false)
    )

    override fun getSearchWord(): Flow<List<String>> {
        return searchWordDao.getRecentSearchWords()
    }

    override suspend fun addSearchWord(searchWord: String) {
        val currentDateString = Calendar.getInstance()
        val searchWordEntity = SearchWord(
            word = searchWord,
            searchedAt = currentDateString.timeInMillis.toString()
        )
        searchWordDao.addSearchWord(searchWord = searchWordEntity)
    }

    override suspend fun removeSearchWord(searchWord: String) {
        searchWordDao.removeSearchWord(searchWord)
    }

    override suspend fun removeAllSearchWords() {
        searchWordDao.removeAllSearchWords()
    }

    override suspend fun setUseRecentSearchWord(use: Boolean): Boolean {
        sharedPreferences.edit().putBoolean(USE_RECENT_WORD_SP_KEY, use).apply()
        useRecentSearchWords.update { use }
        return use
    }

    override fun getUseRecentSearchWord(): Flow<Boolean> {
        return useRecentSearchWords
    }
}
