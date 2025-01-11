package pokitmons.pokit.data.datasource.remote.shared

import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.domain.model.link.LinksSort

interface SharedDataSource {
    suspend fun getSharedPokitContentsPreview(
        categoryId: Int,
        page: Int = 0,
        size: Int = 10,
        sort: List<String> = listOf(LinksSort.RECENT.value)
    ): GetLinksResponse
}
