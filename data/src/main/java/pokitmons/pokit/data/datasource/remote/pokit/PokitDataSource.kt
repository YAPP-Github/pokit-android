package pokitmons.pokit.data.datasource.remote.pokit

import pokitmons.pokit.data.model.pokit.request.CreatePokitRequest
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.request.ModifyPokitRequest
import pokitmons.pokit.data.model.pokit.response.CreatePokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitCountResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitImagesResponseItem
import pokitmons.pokit.data.model.pokit.response.GetPokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.data.model.pokit.response.ModifyPokitResponse

interface PokitDataSource {
    suspend fun getPokits(getPokitsRequest: GetPokitsRequest): GetPokitsResponse
    suspend fun createPokit(createPokitRequest: CreatePokitRequest): CreatePokitResponse
    suspend fun modifyPokit(pokitId : Int, modifyPokitRequest: ModifyPokitRequest): ModifyPokitResponse
    suspend fun getPokitImages(): List<GetPokitImagesResponseItem>
    suspend fun getPokit(pokitId: Int): GetPokitResponse
    suspend fun deletePokit(pokitId: Int)
    suspend fun getPokitCount(): GetPokitCountResponse
}
