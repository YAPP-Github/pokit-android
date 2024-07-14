package com.strayalpaca.addlink

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalpaca.addlink.model.AddLinkScreenState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
fun AddLinkScreenPreview() {
    PokitTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(PokitTheme.colors.backgroundBase)
        ) {
            AddLinkScreen(
                isModifyLink = false,
                url = "",
                title = "",
                memo = "",
                pokitName = "",
                state = AddLinkScreenState(),
                inputUrl = {},
                inputTitle = {},
                inputMemo = {},
                inputNewPokitName = {},
                onClickAddPokit = {},
                onClickSavePokit = {},
                dismissPokitAddBottomSheet = {},
                onClickSelectPokit = {},
                onClickSelectPokitItem = {},
                dismissPokitSelectBottomSheet = {},
                toggleRemindRadio = {},
                onBackPressed = {},
                onClickSaveButton = {}
            )
        }
    }
}
