package pokitmons.pokit.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pokitmons.pokit.data.room.entity.SearchWord

@Dao
interface SearchWordDao {
    @Query("SELECT word from SearchWord order by searchedAt desc limit 10")
    fun getRecentSearchWords(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchWord(searchWord: SearchWord)

    @Query("DELETE from SearchWord where word = :word")
    suspend fun removeSearchWord(word: String)

    @Query("DELETE from SearchWord")
    suspend fun removeAllSearchWords()
}
