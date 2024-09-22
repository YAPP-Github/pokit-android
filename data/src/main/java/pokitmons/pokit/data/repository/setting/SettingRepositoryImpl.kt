package pokitmons.pokit.data.repository.setting

import pokitmons.pokit.data.datasource.remote.setting.SettingDataSource
import pokitmons.pokit.data.mapper.setting.SettingMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.data.model.setting.reqeust.EditNicknameRequest
import pokitmons.pokit.data.model.setting.response.EditNicknameResponse
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.setting.EditNicknameResult
import pokitmons.pokit.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val remoteSettingDataSource: SettingDataSource,
) : SettingRepository {
    override suspend fun editNickname(nickname: String): PokitResult<EditNicknameResult> {
        return runCatching {
            val editNicknameResponse: EditNicknameResponse = remoteSettingDataSource.editNickname(EditNicknameRequest(nickname))
            val editNicknameMapper = SettingMapper.mapperToEditNickname(editNicknameResponse)
            PokitResult.Success(editNicknameMapper)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
