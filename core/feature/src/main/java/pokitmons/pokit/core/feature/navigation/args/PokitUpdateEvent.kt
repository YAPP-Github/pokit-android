package pokitmons.pokit.core.feature.navigation.args

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object PokitUpdateEvent {
    private val _updatedPokit = MutableSharedFlow<PokitArg>()
    val updatedPokit = _updatedPokit.asSharedFlow()

    fun updatePokit(pokitArg: PokitArg) {
        CoroutineScope(Dispatchers.Default).launch {
            _updatedPokit.emit(pokitArg)
        }
    }
}
