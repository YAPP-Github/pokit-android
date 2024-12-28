package com.strayalpaca.addpokit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalpaca.addpokit.model.AddPokitScreenState
import com.strayalpaca.addpokit.model.AddPokitSideEffect
import com.strayalpaca.addpokit.model.Pokit
import com.strayalpaca.addpokit.model.PokitImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun Preview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AddPokitScreen(
                viewModel = dummyAddPokitViewModel
            )
        }
    }
}

private val dummyAddPokitViewModel = object : AddPokitViewModel {
    override val state: StateFlow<AddPokitScreenState>
        get() = MutableStateFlow(AddPokitScreenState())
    override val pokitList: StateFlow<List<Pokit>>
        get() = MutableStateFlow(samplePokitList)
    override val pokitListState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)
    override val sideEffect: EventFlow<AddPokitSideEffect>
        get() = MutableEventFlow()

    override fun loadNextPokits() {}

    override fun inputPokitName(pokitName: String) {}

    override fun savePokit() {}

    override fun showPokitProfileImageSelectBottomSheet() {}

    override fun hidePokitProfileImageSelectBottomSheet() {}

    override fun setPokitProfileImage(pokitImage: PokitImage) {}

    override fun hideToastMessage() {}

}

private val samplePokitList = listOf(
    Pokit(title = "안드로이드", id = "1", count = 2),
    Pokit(title = "IOS", id = "2", count = 2),
    Pokit(title = "디자인", id = "3", count = 2),
    Pokit(title = "PM", id = "4", count = 1),
    Pokit(title = "서버", id = "5", count = 2)
)
