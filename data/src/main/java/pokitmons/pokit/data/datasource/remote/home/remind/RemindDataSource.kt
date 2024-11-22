package pokitmons.pokit.data.datasource.remote.home.remind

import pokitmons.pokit.data.model.home.remind.BookmarkContentCountResponse
import pokitmons.pokit.data.model.home.remind.Remind
import pokitmons.pokit.data.model.home.remind.RemindRequest
import pokitmons.pokit.data.model.home.remind.RemindResponse
import pokitmons.pokit.data.model.home.remind.UnreadContentCountResponse

interface RemindDataSource {
    suspend fun getUnreadContents(remindRequest: RemindRequest): RemindResponse
    suspend fun getUnreadContentsCount(): UnreadContentCountResponse
    suspend fun getTodayContents(remindRequest: RemindRequest): List<Remind>
    suspend fun getBookmarkContents(remindRequest: RemindRequest): RemindResponse
    suspend fun getBookmarkContentsCount(): BookmarkContentCountResponse
}
