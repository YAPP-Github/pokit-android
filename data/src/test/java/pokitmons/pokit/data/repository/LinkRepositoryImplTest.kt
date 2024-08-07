package pokitmons.pokit.data.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.datasource.remote.link.LinkDataSource
import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.data.repository.link.LinkRepositoryImpl
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link

val linkDataSource : LinkDataSource = mockk()

class LinkRepositoryImplTest: DescribeSpec({
    val linkRepository = LinkRepositoryImpl(linkDataSource)
    describe("링크 목록 조회") {
        context("링크 목록이 정상적으로 수신되면") {
            coEvery { linkDataSource.getLinks() } returns GetLinksResponse()
            it("링크 목록이 반환된다.") {
                val response = linkRepository.getLinks()
                response.shouldBeInstanceOf<PokitResult.Success<List<Link>>>()
            }
        }

        context("에러가 발생했다면") {
            coEvery { linkDataSource.getLinks() } throws IllegalArgumentException()
            it("에러코드, 메세지가 반환된다.") {
                val response = linkRepository.getLinks()
                response.shouldBeInstanceOf<PokitResult.Error>()
            }
        }
    }
})
