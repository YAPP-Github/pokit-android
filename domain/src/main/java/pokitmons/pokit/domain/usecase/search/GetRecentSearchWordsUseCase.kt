package pokitmons.pokit.domain.usecase.search

import kotlinx.coroutines.flow.Flow
import pokitmons.pokit.domain.repository.search.SearchRepository
import javax.inject.Inject

class GetRecentSearchWordsUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    fun getWords(): Flow<List<String>> {
        return repository.getRecentSearchWords()
    }
}
