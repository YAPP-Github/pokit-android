package pokitmons.pokit.data.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.datasource.remote.pokit.PokitDataSource
import pokitmons.pokit.data.model.pokit.request.CreatePokitRequest
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.request.ModifyPokitRequest
import pokitmons.pokit.data.model.pokit.response.CreatePokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.data.model.pokit.response.ModifyPokitResponse
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

    describe("포킷 추가시") {
        context("추가가 성공적으로 수행되면") {
            coEvery { pokitDataSource.createPokit(CreatePokitRequest()) } returns CreatePokitResponse()
            it("추가된 포킷의 id가 반환된다.") {
                val response = pokitRepository.createPokit("", 0)
                response.shouldBeInstanceOf<PokitResult.Success<Int>>()
            }
        }

        context("추가도중 에러가 발생했다면") {
            coEvery { pokitDataSource.createPokit(CreatePokitRequest()) } throws IllegalArgumentException()
            it("에러 코드, 메세지가 반환된다.") {
                val response = pokitRepository.createPokit("", 0)
                response.shouldBeInstanceOf<PokitResult.Error>()
            }
        }
    }

    describe("포킷 수정시") {
        context("수정이 성공적으로 수행되면") {
            coEvery { pokitDataSource.modifyPokit(pokitId = 0, modifyPokitRequest = ModifyPokitRequest()) } returns ModifyPokitResponse()
            it("수정된 포킷의 id가 반환된다.") {
                val response = pokitRepository.modifyPokit(pokitId = 0, name = "", imageId = 0)
                response.shouldBeInstanceOf<PokitResult.Success<Int>>()
            }
        }

        context("수정도중 에러가 발생했다면") {
            coEvery { pokitDataSource.modifyPokit(pokitId = 0, modifyPokitRequest = ModifyPokitRequest()) } throws IllegalArgumentException()
            it("에러 코드, 메세지가 반환된다.") {
                val response = pokitRepository.modifyPokit(pokitId = 0, name = "", imageId = 0)
                response.shouldBeInstanceOf<PokitResult.Error>()
            }
        }
    }

    describe("포킷 조회시") {
        context("조회가 성공적으로 수행되면") {
            coEvery { pokitDataSource.getPokit(pokitId = 0) } returns GetPokitResponse()
            it("해당 포킷의 정보가 반환된다.") {
                val response = pokitRepository.getPokit(pokitId = 0)
                response.shouldBeInstanceOf<PokitResult.Success<Int>>()
            }
        }

        context("조회 도중 에러가 발생했다면") {
            coEvery { pokitDataSource.getPokit(pokitId = 0) } throws IllegalArgumentException()
            it("에러 코드, 메세지가 반환된다.") {
                val response = pokitRepository.getPokit(pokitId = 0)
                response.shouldBeInstanceOf<PokitResult.Error>()
            }
        }
    }
})
