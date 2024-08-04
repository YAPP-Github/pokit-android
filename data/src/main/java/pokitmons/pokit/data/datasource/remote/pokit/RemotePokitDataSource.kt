package pokitmons.pokit.data.datasource.remote.pokit

import pokitmons.pokit.data.api.PokitApi
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import javax.inject.Inject

class RemotePokitDataSource @Inject constructor(
    private val pokitApi: PokitApi,
) : PokitDataSource {
    override suspend fun getPokits(getPokitsRequest: GetPokitsRequest): GetPokitsResponse {
        return pokitApi.getPokits(
            filterUncategorized = getPokitsRequest.filterUncategoriezd,
            size = getPokitsRequest.size,
            page = getPokitsRequest.page,
            sort = getPokitsRequest.sort.value
        )
    }
}
