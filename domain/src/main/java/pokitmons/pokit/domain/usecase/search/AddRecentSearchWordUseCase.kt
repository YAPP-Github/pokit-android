package pokitmons.pokit.domain.usecase.search

import pokitmons.pokit.domain.repository.search.SearchRepository
import javax.inject.Inject

class AddRecentSearchWordUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun addRecentSearchWord(word: String) {
        repository.addRecentSearchWord(word)
    }
}
