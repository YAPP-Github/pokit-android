package pokitmons.pokit.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["word"])
data class SearchWord(
    @ColumnInfo("word") val word: String,
    @ColumnInfo("searchedAt") val searchedAt: String,
)

