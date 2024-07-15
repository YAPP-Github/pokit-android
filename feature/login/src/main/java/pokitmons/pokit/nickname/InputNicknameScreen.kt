package pokitmons.pokit.nickname

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.block.labeledinput.LabeledInput
import pokitmons.pokit.core.ui.theme.PokitTheme

private const val NICKNAME_MAX_LENGTH = 10 // TODO 매직넘버를 포함하는 모듈화 추가 후 마이그레이션 예정
private const val NICKNAME_MIN_LENGTH = 1 // TODO 매직넘버를 포함하는 모듈화 추가 후 마이그레이션 예정

@Composable
fun InputNicknameScreen(
    onNavigateToKeywordScreen: () -> Unit,
    popBackStack: () -> Unit
) {
    val inputNicknameViewModel: InputNicknameViewModel = viewModel() // TODO hiltViewModel 마이그레이션 예정
    val inputNicknameState by inputNicknameViewModel.inputNicknameState.collectAsState()

    Box(modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 28.dp)
        .fillMaxSize()
    ) {
        Column() {
            Icon(
                modifier = Modifier.clickable { popBackStack() },
                painter = painterResource(id = R.drawable.icon_24_arrow_left),
                contentDescription = "뒤로가기"
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            Text(
                style = PokitTheme.typography.title1,
                text = stringResource(id = pokitmons.pokit.login.R.string.input_nickname_title)
            )

            Spacer(modifier = Modifier.padding(top = 28.dp))

            LabeledInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                label = "",
                inputText = inputNicknameState,
                maxLength = NICKNAME_MAX_LENGTH,
                sub = if (inputNicknameState.length < NICKNAME_MAX_LENGTH) {
                    stringResource(id = pokitmons.pokit.login.R.string.input_restriction_message)
                } else {
                    stringResource(id = pokitmons.pokit.login.R.string.input_max_length)
                },
                isError = inputNicknameState.length > NICKNAME_MAX_LENGTH,
                hintText = stringResource(id = pokitmons.pokit.login.R.string.input_nickname_hint),
                onChangeText = { text ->
                    if (text.length <= NICKNAME_MAX_LENGTH) {
                        inputNicknameViewModel.inputText(text)
                    }
                },
            )
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(id = pokitmons.pokit.login.R.string.next),
            icon = null,
            size = PokitButtonSize.LARGE,
            enable = inputNicknameState.length >= NICKNAME_MIN_LENGTH,
            onClick = { onNavigateToKeywordScreen() }
        )
    }
}
