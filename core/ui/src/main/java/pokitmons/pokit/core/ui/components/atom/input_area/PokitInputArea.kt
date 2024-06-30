package pokitmons.pokit.core.ui.components.atom.input_area

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import pokitmons.pokit.core.ui.components.atom.input_area.attributes.PokitInputAreaState
import pokitmons.pokit.core.ui.components.atom.input_area.subcomponents.PokitInputAreaContainer
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitInputArea(
    text : String,
    hintText : String,
    onChangeText : (String) -> Unit,
    modifier : Modifier = Modifier,
    readOnly : Boolean = false,
    enable : Boolean = true,
    isError : Boolean = false,
) {
    var focused by remember { mutableStateOf(false) }
    val state = remember(focused, isError, readOnly, enable) {
        getState(
            enabled = enable,
            readOnly = readOnly,
            focused = focused,
            error = isError,
            text = text
        )
    }
    val textColor = getTextColor(state = state)
    val textStyle = PokitTheme.typography.body3Regular.copy(color = textColor)

    BasicTextField(
        value = text,
        onValueChange = onChangeText,
        textStyle = textStyle,
        enabled = (enable && !readOnly),
        minLines = 5,
        modifier = Modifier.onFocusChanged { focusState ->
            focused = focusState.isFocused
        },
        decorationBox = { innerTextField ->
            PokitInputAreaContainer(
                state = state,
                modifier = modifier,
            ) {
                if (text.isEmpty() && !focused) {
                    Text(text = hintText, style = textStyle)
                }

                Box(modifier = Modifier.weight(1f)) {
                    innerTextField()
                }
            }
        }
    )
}

@Composable
private fun getTextColor(
    state: PokitInputAreaState,
) : Color {
    return when(state) {
        PokitInputAreaState.DEFAULT -> {
            PokitTheme.colors.textTertiary
        }
        PokitInputAreaState.INPUT -> {
            PokitTheme.colors.textSecondary
        }
        PokitInputAreaState.ACTIVE -> {
            PokitTheme.colors.textSecondary
        }
        PokitInputAreaState.DISABLE -> {
            PokitTheme.colors.textDisable
        }
        PokitInputAreaState.READ_ONLY -> {
            PokitTheme.colors.textTertiary
        }
        PokitInputAreaState.ERROR -> {
            PokitTheme.colors.textSecondary
        }
    }
}

private fun getState(
    enabled : Boolean,
    readOnly : Boolean,
    focused : Boolean,
    error : Boolean,
    text : String,
) : PokitInputAreaState {
    return when {
        !enabled -> {
            PokitInputAreaState.DISABLE
        }
        readOnly -> {
            PokitInputAreaState.READ_ONLY
        }
        focused -> {
            PokitInputAreaState.ACTIVE
        }
        error -> {
            PokitInputAreaState.ERROR
        }
        text.isEmpty() -> {
            PokitInputAreaState.DEFAULT
        }
        else -> {
            PokitInputAreaState.INPUT
        }
    }
}
