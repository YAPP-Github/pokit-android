package pokitmons.pokit.data.datasource

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.api.PokitApi
import pokitmons.pokit.data.datasource.remote.pokit.RemotePokitDataSource
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse

val pokitApi: PokitApi = mockk()

class RemotePokitDataSourceTest : DescribeSpec({
    val remotePokitDataSource = RemotePokitDataSource(pokitApi)
    describe("포킷 목록 조회시") {
        context("포킷 목록이 정상적으로 수신되면") {
            coEvery { pokitApi.getPokits() } returns GetPokitsResponse()
            it("포킷 목록이 반환된다.") {
                val response = remotePokitDataSource.getPokits(GetPokitsRequest())
                response.shouldBeInstanceOf<GetPokitsResponse>()
            }
        }

        context("에러가 발생했다면") {
            coEvery { pokitApi.getPokits() } throws IllegalArgumentException("error")
            it("동일한 에러가 발생한다.") {
                val exception = shouldThrow<Exception> {
                    remotePokitDataSource.getPokits(GetPokitsRequest())
                }
                exception.message shouldBe "error"
            }
        }
    }
})
