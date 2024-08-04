package pokitmons.pokit.data.datasource.remote.pokit

import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse

interface PokitDataSource {
    suspend fun getPokits(getPokitsRequest: GetPokitsRequest): GetPokitsResponse
}
