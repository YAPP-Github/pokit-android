package pokitmons.pokit.data.datasource

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.api.LinkApi
import pokitmons.pokit.data.datasource.remote.link.RemoteLinkDataSource
import pokitmons.pokit.data.model.link.response.GetLinksResponse

val linkApi: LinkApi = mockk()

class RemoteLinkDataSourceTest : DescribeSpec({
    val remoteLinkDataSource = RemoteLinkDataSource(linkApi)
    describe("링크 목록 조회시") {
        context("링크 목록 조회가 정상적으로 수신되면") {
            coEvery { linkApi.getLinks() } returns GetLinksResponse()
            it("링크 목록이 반환된다.") {
                val response = remoteLinkDataSource.getLinks()
                response.shouldBeInstanceOf<GetLinksResponse>()
            }
        }

        context("에러가 발생했다면") {
            coEvery { linkApi.getLinks() } throws IllegalArgumentException("error")
            it("동일한 에러가 발생한다.") {
                val exception = shouldThrow<Exception> {
                    remoteLinkDataSource.getLinks()
                }
                exception.message shouldBe "error"
            }
        }
    }
})
