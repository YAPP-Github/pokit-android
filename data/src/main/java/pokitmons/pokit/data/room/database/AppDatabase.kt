package pokitmons.pokit.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pokitmons.pokit.data.room.dao.SearchWordDao
import pokitmons.pokit.data.room.entity.SearchWord

@Database(entities = [SearchWord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchWordDao(): SearchWordDao
}
