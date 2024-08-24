package pokitmons.pokit.data.datasource

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.api.AlertApi
import pokitmons.pokit.data.datasource.remote.alert.RemoteAlertDataSource
import pokitmons.pokit.data.model.alert.GetAlertsResponse

val alertApi: AlertApi = mockk()

class RemoteAlertDataSourceTest : DescribeSpec({
    val remoteAlertDataSource = RemoteAlertDataSource(alertApi)
    describe("알림 목록 조회시") {
        context("알림 목록 조회가 정상적으로 수행되면") {
            coEvery { alertApi.getAlerts(page = 0, size = 0) } returns GetAlertsResponse()
            it("알림 목록이 반환된다.") {
                val response = remoteAlertDataSource.getAlerts(page = 0, size = 0)
                response.shouldBeInstanceOf<GetAlertsResponse>()
            }
        }

        context("에러가 발생했다면") {
            coEvery { alertApi.getAlerts(page = 0, size = 0) } throws IllegalArgumentException("error")
            it("동일한 에러가 발생한다.") {
                val exception = shouldThrow<Exception> {
                    remoteAlertDataSource.getAlerts(page = 0, size = 0)
                }
                exception.message shouldBe "error"
            }
        }
    }

    describe("알림 제거시") {
        context("에러가 발생했다면") {
            coEvery { alertApi.deleteAlert(alertId = 0) } throws IllegalArgumentException("error")
            it("동일한 에러가 발생한다.") {
                val exception = shouldThrow<Exception> {
                    remoteAlertDataSource.deleteAlert(alertId = 0)
                }
                exception.message shouldBe "error"
            }
        }
    }
})
