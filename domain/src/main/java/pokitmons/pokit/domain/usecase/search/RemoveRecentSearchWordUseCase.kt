package pokitmons.pokit.domain.usecase.search

import pokitmons.pokit.domain.repository.search.SearchRepository
import javax.inject.Inject

class RemoveRecentSearchWordUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend fun removeWord(word: String) {
        repository.removeSearchWord(word)
    }

    suspend fun removeAll() {
        repository.removeAllSearchWords()
    }
}
