package pokitmons.pokit.data.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.datasource.remote.pokit.PokitDataSource
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.data.repository.pokit.PokitRepositoryImpl
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit

val pokitDataSource : PokitDataSource = mockk()

class PokitRepositoryImplTest : DescribeSpec({
    val pokitRepository = PokitRepositoryImpl(pokitDataSource)
    describe("포킷 목록 조회") {
        context("포킷 목록이 정상적으로 수신되면") {
            coEvery { pokitDataSource.getPokits(GetPokitsRequest()) } returns GetPokitsResponse()

            it("포킷 목록이 반환된다.") {
                val response = pokitRepository.getPokits()
                response.shouldBeInstanceOf<PokitResult.Success<List<Pokit>>>()
            }
        }

        context("에러가 발생했다면") {
            coEvery { pokitDataSource.getPokits(GetPokitsRequest()) } throws IllegalArgumentException()

            it("에러 코드, 메세지가 반환된다.") {
                val response = pokitRepository.getPokits()
                response.shouldBeInstanceOf<PokitResult.Error>()
            }
        }
    }
})
