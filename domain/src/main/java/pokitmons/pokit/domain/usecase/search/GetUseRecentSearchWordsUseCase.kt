package pokitmons.pokit.domain.usecase.search

import kotlinx.coroutines.flow.Flow
import pokitmons.pokit.domain.repository.search.SearchRepository
import javax.inject.Inject

class GetUseRecentSearchWordsUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    fun getUse(): Flow<Boolean> {
        return repository.getUseRecentSearchWord()
    }
}
