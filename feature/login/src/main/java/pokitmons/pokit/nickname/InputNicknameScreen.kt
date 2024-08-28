package pokitmons.pokit.nickname

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import pokitmons.pokit.LoginViewModel
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.block.labeledinput.LabeledInput
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.core.ui.R as UI
import pokitmons.pokit.login.R as Login

private const val NICKNAME_MAX_LENGTH = 10 // TODO 매직넘버를 포함하는 모듈화 추가 후 마이그레이션 예정
private const val NICKNAME_MIN_LENGTH = 1 // TODO 매직넘버를 포함하는 모듈화 추가 후 마이그레이션 예정

@Composable
fun InputNicknameScreen(
    viewModel: LoginViewModel,
    onNavigateToKeywordScreen: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val inputNicknameState by viewModel.inputNicknameState.collectAsState()

    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 28.dp)
            .fillMaxSize()
    ) {
        Column {
            Icon(
                modifier = Modifier.noRippleClickable { onBackPressed() },
                painter = painterResource(id = UI.drawable.icon_24_arrow_left),
                contentDescription = "뒤로가기"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                style = PokitTheme.typography.title1,
                text = stringResource(id = Login.string.input_nickname_title)
            )

            LabeledInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                label = "",
                inputText = inputNicknameState.nickname,
                maxLength = NICKNAME_MAX_LENGTH,
                sub = when {
                    inputNicknameState.isDuplicate -> stringResource(id = Login.string.nickname_already_in_use)
                    inputNicknameState.isRegex -> stringResource(id = Login.string.input_restriction_message)
                    inputNicknameState.nickname.length < NICKNAME_MAX_LENGTH -> stringResource(id = Login.string.input_max_length)
                    else -> ""
                },
                isError = inputNicknameState.nickname.length > NICKNAME_MAX_LENGTH || inputNicknameState.isDuplicate || inputNicknameState.isRegex,
                hintText = stringResource(id = Login.string.input_nickname_hint),
                onChangeText = { text ->
                    Log.d("!! : ", text)
                    if (text.length <= NICKNAME_MAX_LENGTH) {
                        viewModel.apply {
                            inputText(text)
                            if (checkNicknameRegex(text)) {
                                Log.d("!! : ", "else call")
                                checkDuplicateNickname(text)
                            } else {

                            }
                        }
                    }
                }
            )
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(id = pokitmons.pokit.login.R.string.next),
            icon = null,
            size = PokitButtonSize.LARGE,
            enable = !inputNicknameState.isDuplicate && inputNicknameState.isRegex && inputNicknameState.nickname.length < NICKNAME_MAX_LENGTH,
            onClick = { onNavigateToKeywordScreen() }
        )
    }
}
