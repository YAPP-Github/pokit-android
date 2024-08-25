package com.strayalpaca.pokitdetail

import com.strayalpaca.pokitdetail.paging.PokitPaging
import com.strayalpaca.pokitdetail.paging.SimplePagingState
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.testCoroutineScheduler
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit

const val PER_PAGE_SAMPLE = 3
const val FIRST_REQUEST_PAGE_SAMPLE = 3

@OptIn(ExperimentalKotest::class, ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
class PokitPagingTest : DescribeSpec({
    val sampleGetPokitsUseCase: GetPokitsUseCase = mockk()

    describe("PokitPaging").config(coroutineTestScope = true) {
        val coroutineScope = this
        val pokitPaging = PokitPaging(
            getPokits = sampleGetPokitsUseCase,
            coroutineScope = coroutineScope,
            firstRequestPage = FIRST_REQUEST_PAGE_SAMPLE,
            perPage = PER_PAGE_SAMPLE
        )
        coEvery { sampleGetPokitsUseCase.getPokits(size = PER_PAGE_SAMPLE * FIRST_REQUEST_PAGE_SAMPLE, page = 0) } coAnswers {
            delay(1000L)
            PokitResult.Success(result = listOf(DomainPokit(1, 1, "", DomainPokit.Image(1, ""), 1, "")))
        }
        coEvery { sampleGetPokitsUseCase.getPokits(size = PER_PAGE_SAMPLE, page = 0) } coAnswers {
            delay(1000L)
            PokitResult.Success(result = listOf(DomainPokit(1, 1, "", DomainPokit.Image(1, ""), 1, "")))
        }

        context("새로고침을 하는 경우") {
            it("새로고침 로딩 상태가 되어야 한다.") {
                pokitPaging.refresh()
                pokitPaging.pagingState.value shouldBe SimplePagingState.LOADING_INIT
            }
        }

        context("기존 페이지를 로드하던 중 다른 페이지 요청이 들어온 경우") {
            it("해당 요청을 무시하고 기존 상태를 유지한다.") {
                pokitPaging.refresh()
                pokitPaging.load()

                coroutineScope.testCoroutineScheduler.advanceTimeBy(5000L)

                val state = pokitPaging.pagingState.first()
                state shouldBe SimplePagingState.LAST

                // testCoroutineScheduler.advanceUntilIdle()
                // it 내의 this(coroutineScope)와 전체 describe의 coroutineScope가 서로 다르다!
            }
        }

        context("기존 페이지를 로드하던 중 새로고침 요청이 들어온 경우") {
            it("기존 작업을 무시하고 새로고침을 수행한다.") {
                pokitPaging.load()
                pokitPaging.refresh()
                pokitPaging.pagingState.value shouldBe SimplePagingState.LOADING_INIT
            }
        }
    }
})
