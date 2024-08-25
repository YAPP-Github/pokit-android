package pokitmons.pokit.settings.nickname

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.block.labeledinput.LabeledInput
import pokitmons.pokit.settings.EditNicknameState
import pokitmons.pokit.settings.SettingViewModel
import pokitmons.pokit.settings.R.string as StringResource

private const val NICKNAME_MAX_LENGTH = 10 // TODO 매직넘버를 포함하는 모듈화 추가 후 마이그레이션 예정

@Composable
fun EditNicknameScreen(
    settingViewModel: SettingViewModel,
    onBackPressed: () -> Unit,
) {
    val inputNicknameState by settingViewModel.inputNicknameState.collectAsState()
    val editNicknameState by settingViewModel.editNicknameState.collectAsState()
    val context = LocalContext.current

    when (editNicknameState) {
        is EditNicknameState.Init -> Unit
        is EditNicknameState.Success -> onBackPressed()
        is EditNicknameState.Error -> Toast.makeText(context, (editNicknameState as EditNicknameState.Error).message, Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 28.dp)
            .fillMaxSize()
    ) {
        Column {
            NicknameHeader(onBackPressed)
            LabeledInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                label = "",
                inputText = inputNicknameState.nickname,
                maxLength = NICKNAME_MAX_LENGTH,
                sub = when {
                    inputNicknameState.nickname.length < NICKNAME_MAX_LENGTH -> stringResource(id = StringResource.input_restriction_message)
                    !inputNicknameState.isDuplicate -> stringResource(id = StringResource.nickname_already_in_use)
                    else -> stringResource(id = StringResource.input_max_length)
                },
                isError = inputNicknameState.nickname.length > NICKNAME_MAX_LENGTH || !inputNicknameState.isDuplicate,
                hintText = stringResource(id = StringResource.input_nickname_hint),
                onChangeText = { text ->
                    if (text.length <= NICKNAME_MAX_LENGTH) {
                        settingViewModel.apply {
                            inputText(text)
                            checkDuplicateNickname(text)
                        }
                    }
                }
            )
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(id = StringResource.save),
            icon = null,
            size = PokitButtonSize.LARGE,
            enable = !inputNicknameState.isDuplicate,
            onClick = { settingViewModel.editNickname() }
        )
    }
}
