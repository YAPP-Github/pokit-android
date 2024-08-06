package pokitmons.pokit.data.datasource.remote.pokit

import pokitmons.pokit.data.api.PokitApi
import pokitmons.pokit.data.model.pokit.request.CreatePokitRequest
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.request.ModifyPokitRequest
import pokitmons.pokit.data.model.pokit.response.CreatePokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.data.model.pokit.response.ModifyPokitResponse
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

    override suspend fun createPokit(createPokitRequest: CreatePokitRequest): CreatePokitResponse {
        return pokitApi.createPokit(createPokitRequest = createPokitRequest)
    }

    override suspend fun modifyPokit(pokitId : Int, modifyPokitRequest: ModifyPokitRequest): ModifyPokitResponse {
        return pokitApi.modifyPokit(categoryId = pokitId, modifyPokitRequest = modifyPokitRequest)
    }
}
