package pokitmons.pokit.domain.usecase.search

import pokitmons.pokit.domain.repository.search.SearchRepository
import javax.inject.Inject

class SetUseRecentSearchWordsUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend fun setUse(use: Boolean): Boolean {
        return repository.setUseRecentSearchWord(use)
    }
}
