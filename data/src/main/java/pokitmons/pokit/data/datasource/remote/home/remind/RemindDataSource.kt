package pokitmons.pokit.data.datasource.remote.home.remind

import pokitmons.pokit.data.model.home.remind.Remind
import pokitmons.pokit.data.model.home.remind.RemindRequest
import pokitmons.pokit.data.model.home.remind.RemindResponse

interface RemindDataSource {
    suspend fun getUnreadContents(remindRequest: RemindRequest): RemindResponse
    suspend fun getTodayContents(remindRequest: RemindRequest): List<Remind>
    suspend fun getBookmarkContents(remindRequest: RemindRequest): RemindResponse
}
