package pokitmons.pokit.core.feature.navigation.args

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object PokitUpdateEvent {
    private val _updatedPokit = MutableSharedFlow<PokitArg>()
    val updatedPokit = _updatedPokit.asSharedFlow()

    private val _removedPokitId = MutableSharedFlow<Int>()
    val removedPokitId = _removedPokitId.asSharedFlow()

    private val _addedPokit = MutableSharedFlow<PokitArg>()
    val addedPokit = _addedPokit.asSharedFlow()

    fun updatePokit(pokitArg: PokitArg) {
        CoroutineScope(Dispatchers.Default).launch {
            _updatedPokit.emit(pokitArg)
        }
    }

    fun removePokit(pokitId: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            _removedPokitId.emit(pokitId)
        }
    }

    fun createPokit(pokitArg: PokitArg) {
        CoroutineScope(Dispatchers.Default).launch {
            _addedPokit.emit(pokitArg)
        }
    }
}
