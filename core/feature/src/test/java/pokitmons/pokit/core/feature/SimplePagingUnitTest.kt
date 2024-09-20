package pokitmons.pokit.core.feature

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.testCoroutineScheduler
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import pokitmons.pokit.core.feature.model.TestPagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging

const val FIRST_REQUEST_PAGE_SAMPLE = 3
const val PAGE_LOAD_TIME = 1000L
const val TOTAL_ITEM_COUNT = 46

@OptIn(ExperimentalKotest::class, ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
class SimplePagingUnitTest : DescribeSpec({

    describe("SimplePaging").config(coroutineTestScope = true) {
        val coroutineScope = this
        val testPaging = SimplePaging(
            coroutineScope = coroutineScope,
            pagingSource = TestPagingSource(
                loadTime = PAGE_LOAD_TIME,
                totalItemCount = TOTAL_ITEM_COUNT
            ),
            getKeyFromItem = { it },
            firstRequestPage = FIRST_REQUEST_PAGE_SAMPLE
        )

        context("새로고침을 한 상황에서") {
            it("새로고침 로딩 상태가 되어야 한다.") {
                testPaging.refresh()
                testPaging.pagingState.value shouldBe PagingState.LOADING_INIT
            }

            it("다른 페이지 요청을 무시한다.") {
                testPaging.refresh()
                testPaging.load()

                coroutineScope.testCoroutineScheduler.advanceTimeBy(5000L)
                // testCoroutineScheduler.advanceUntilIdle()
                // it 내의 this(coroutineScope)와 전체 describe의 coroutineScope가 서로 다르다!

                val state = testPaging.pagingState.first()
                state shouldBe PagingState.IDLE
            }

            it("초기화 작업은 수행 가능하다.") {
                testPaging.refresh()
                testPaging.clear()

                val state = testPaging.pagingState.first()
                state shouldBe PagingState.IDLE
            }
        }

        context("기존 페이지를 로드하던 중 새로고침 요청이 들어온 상황에서") {
            it("기존 작업을 무시하고 새로고침을 수행한다.") {
                testPaging.load()
                testPaging.refresh()
                testPaging.pagingState.value shouldBe PagingState.LOADING_INIT
            }
        }

        context("전체 데이터를 모두 로드한 상황에서") {
            testPaging.clear()
            testPaging.refresh()
            coroutineScope.testCoroutineScheduler.advanceTimeBy(PAGE_LOAD_TIME + 1)
            testPaging.load()
            coroutineScope.testCoroutineScheduler.advanceTimeBy(PAGE_LOAD_TIME + 1)
            testPaging.load()
            coroutineScope.testCoroutineScheduler.advanceTimeBy(PAGE_LOAD_TIME + 1)

            it("마지막 상태를 가진다.") {
                testPaging.pagingState.value shouldBe PagingState.LAST
            }

            it("추가적인 데이터 로드 요청을 무시한다.") {
                testPaging.load()
                testPaging.pagingState.value shouldBe PagingState.LAST
            }
        }
    }
})
