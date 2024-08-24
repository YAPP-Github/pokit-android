package pokitmons.pokit.data.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import pokitmons.pokit.data.datasource.remote.alert.AlertDataSource
import pokitmons.pokit.data.model.alert.GetAlertsResponse
import pokitmons.pokit.data.repository.alert.AlertRepositoryImpl
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.alert.Alarm

val alertDataSource: AlertDataSource = mockk()

class AlertRepositoryImplTest : DescribeSpec({
    val alertRepository = AlertRepositoryImpl(alertDataSource)
    describe("알림 목록 조회시") {
        context("알림 목록 조회가 정상적으로 수행되면") {
            coEvery { alertDataSource.getAlerts(page = 0, size = 0) } returns GetAlertsResponse()
            it("PokitResult로 래핑된 알림 목록이 반환된다.") {
                val response = alertRepository.getAlerts(page = 0, size = 0)
                response.shouldBeInstanceOf<PokitResult.Success<List<Alarm>>>()
            }
        }

        context("에러가 발생했다면") {
            coEvery { alertDataSource.getAlerts(page = 0, size = 0) } throws IllegalArgumentException()
            it("PokitResult로 래핑된 에러 내용이 반환된다.") {
                val response = alertRepository.getAlerts(page = 0, size = 0)
                response.shouldBeInstanceOf<PokitResult.Error>()
            }
        }
    }

    describe("알림 제거시") {
        context("제거가 정상적으로 수행되면") {
            coEvery { alertDataSource.deleteAlert(alertId = 0) } returns Unit
            it("데이터가 없는 PokitResult.Success가 반환된다.") {
                val response = alertRepository.deleteAlert(alertId = 0)
                response.shouldBeInstanceOf<PokitResult.Success<Unit>>()
            }
        }

        context("에러가 발생했다면") {
            coEvery { alertDataSource.deleteAlert(alertId = 0) } throws IllegalArgumentException()
            it("PokitResult로 래핑된 에러 내용이 반환된다.") {
                val response = alertRepository.deleteAlert(alertId = 0)
                response.shouldBeInstanceOf<PokitResult.Error>()
            }
        }
    }
})
