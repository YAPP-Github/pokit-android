package pokitmons.pokit.data.datasource

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.api.PokitApi
import pokitmons.pokit.data.datasource.remote.pokit.RemotePokitDataSource
import pokitmons.pokit.data.model.pokit.request.CreatePokitRequest
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.request.ModifyPokitRequest
import pokitmons.pokit.data.model.pokit.response.CreatePokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.data.model.pokit.response.ModifyPokitResponse

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

    describe("포킷 추가시") {
        context("추가가 성공적으로 수행되면") {
            coEvery { pokitApi.createPokit(CreatePokitRequest()) } returns CreatePokitResponse()
            it("추가된 포킷의 정보가 반환된다.") {
                val response = remotePokitDataSource.createPokit(CreatePokitRequest())
                response.shouldBeInstanceOf<CreatePokitResponse>()
            }
        }

        context("추가도중 에러가 발생했다면") {
            coEvery { pokitApi.createPokit(CreatePokitRequest()) } throws IllegalArgumentException("error")
            it("동일한 에러가 발생한다.") {
                val exception = shouldThrow<Exception> {
                    remotePokitDataSource.createPokit(CreatePokitRequest())
                }
                exception.message shouldBe "error"
            }
        }
    }

    describe("포킷 수정시") {
        context("수정이 성공적으로 수행되면") {
            coEvery { pokitApi.modifyPokit(categoryId = 0, modifyPokitRequest = ModifyPokitRequest()) } returns ModifyPokitResponse()
            it("수정된 포킷의 정보가 반환된다.") {
                val response = remotePokitDataSource.modifyPokit(pokitId = 0, modifyPokitRequest = ModifyPokitRequest())
                response.shouldBeInstanceOf<ModifyPokitResponse>()
            }
        }

        context("수정도중 에러가 발생했다면") {
            coEvery { pokitApi.modifyPokit(categoryId = 0, modifyPokitRequest = ModifyPokitRequest()) } throws IllegalArgumentException("error")
            it("동일한 에러가 발생한다.") {
                val exception = shouldThrow<Exception> {
                    remotePokitDataSource.modifyPokit(pokitId = 0, modifyPokitRequest = ModifyPokitRequest())
                }
                exception.message shouldBe "error"
            }
        }
    }

    describe("포킷 조회시") {
        context("조회가 성공적으로 수행되면") {
            coEvery { pokitApi.getPokit(0) } returns GetPokitResponse()
            it("해당 포킷의 정보가 반환된다.") {
                val response = remotePokitDataSource.getPokit(0)
                response.shouldBeInstanceOf<GetPokitResponse>()
            }
        }

        context("조회 도중 에러가 발생했다면") {
            coEvery { pokitApi.getPokit(0) } throws IllegalArgumentException("error")
            it("동일한 에러가 발생한다.") {
                val exception = shouldThrow<Exception> {
                    remotePokitDataSource.getPokit(0)
                }
                exception.message shouldBe "error"
            }
        }
    }
})
