package com.strayalpaca.addlink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strayalpaca.addlink.components.block.Link
import com.strayalpaca.addlink.components.block.Toolbar
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.inputarea.PokitInputArea
import pokitmons.pokit.core.ui.components.block.labeledinput.LabeledInput
import pokitmons.pokit.core.ui.components.block.select.PokitSelect
import pokitmons.pokit.core.ui.components.block.switchradio.PokitSwitchRadio
import pokitmons.pokit.core.ui.components.block.switchradio.attributes.PokitSwitchRadioStyle
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun AddLinkScreen(
    onBackPressed: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var useRemind by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            onClickBack = onBackPressed,
            title = stringResource(id = R.string.add_link)
        )

        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
                .verticalScroll(state = scrollState)
        ) {
            Link()

            Spacer(modifier = Modifier.height(16.dp))

            LabeledInput(
                label = stringResource(id = R.string.link),
                sub = "",
                maxLength = 1000,
                inputText = "",
                hintText = stringResource(id = R.string.placeholder_link),
                onChangeText = {}
            )

            Spacer(modifier = Modifier.height(24.dp))

            LabeledInput(
                label = stringResource(id = R.string.title),
                sub = "",
                maxLength = 1000,
                inputText = "",
                hintText = stringResource(id = R.string.placeholder_title),
                onChangeText = {}
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                PokitSelect(
                    text = stringResource(id = R.string.uncategorized),
                    hintText = stringResource(id = R.string.uncategorized),
                    label = stringResource(id = R.string.pokit),
                    modifier = Modifier.weight(1f),
                    onClick = { showBottomSheet = true }
                )

                Spacer(modifier = Modifier.width(8.dp))

                PokitButton(
                    text = null,
                    icon = PokitButtonIcon(
                        resourceId = pokitmons.pokit.core.ui.R.drawable.icon_24_plus,
                        position = PokitButtonIconPosition.LEFT
                    ),
                    size = PokitButtonSize.LARGE,
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.memo),
                style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PokitInputArea(
                text = "",
                hintText = stringResource(id = R.string.placeholder_memo),
                onChangeText = { }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "00/100",
                style = PokitTheme.typography.detail1.copy(color = PokitTheme.colors.textTertiary),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.title_remind),
                style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
            )

            Spacer(modifier = Modifier.height(12.dp))

            PokitSwitchRadio(
                modifier = Modifier.fillMaxWidth(),
                itemList = listOf(
                    Pair(stringResource(id = R.string.reject_remind), false),
                    Pair(stringResource(id = R.string.accept_remind), true)
                ),
                style = PokitSwitchRadioStyle.STROKE,
                selectedItem =
                if (useRemind) {
                    Pair(stringResource(id = R.string.accept_remind), true)
                } else {
                    Pair(stringResource(id = R.string.reject_remind), false)
                },
                onClickItem = {
                    useRemind = it.second
                },
                getTitleFromItem = { it.first }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.sub_remind),
                style = PokitTheme.typography.detail1.copy(color = PokitTheme.colors.textTertiary)
            )

            Spacer(modifier = Modifier.height(32.dp))

            PokitButton(
                text = stringResource(id = R.string.save),
                icon = null,
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                size = PokitButtonSize.LARGE
            )
        }

        if (showBottomSheet) {
            PokitBottomSheet(onHideBottomSheet = { showBottomSheet = false }) {
            }
        }
    }
}
