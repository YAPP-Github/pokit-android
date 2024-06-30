package pokitmons.pokit.core.ui.components.atom.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIcon
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIconPosition
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputShape
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputState
import pokitmons.pokit.core.ui.components.atom.input.subcomponents.container.PokitInputContainer
import pokitmons.pokit.core.ui.components.atom.input.subcomponents.icon.PokitInputIcon
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitInput(
    text: String,
    hintText: String,
    onChangeText: (String) -> Unit,
    icon: PokitInputIcon?,
    modifier: Modifier = Modifier,
    shape: PokitInputShape = PokitInputShape.RECTANGLE,
    readOnly: Boolean = false,
    enable: Boolean = true,
    isError: Boolean = false,
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
    val textStyle = PokitTheme.typography.body3Medium.copy(color = textColor)

    BasicTextField(
        value = text,
        onValueChange = onChangeText,
        textStyle = textStyle,
        enabled = (enable && !readOnly),
        maxLines = 1,
        modifier = Modifier.onFocusChanged { focusState ->
            focused = focusState.isFocused
        },
        decorationBox = { innerTextField ->
            PokitInputContainer(
                iconPosition = icon?.position,
                modifier = modifier,
                shape = shape,
                state = state
            ) {
                if (icon == null) {
                    Box(modifier = Modifier.height(24.dp)) // 아이콘 없을 떄 공간 맞추기용
                }

                if (icon?.position == PokitInputIconPosition.LEFT) {
                    PokitInputIcon(state = state, resourceId = icon.resourceId)
                    Box(modifier = Modifier.width(8.dp))
                }

                if (text.isEmpty() && !focused) {
                    Text(text = hintText, style = textStyle)
                }

                Box(modifier = Modifier.weight(1f)) {
                    innerTextField()
                }

                if (icon?.position == PokitInputIconPosition.RIGHT) {
                    PokitInputIcon(state = state, resourceId = icon.resourceId)
                }
            }
        }
    )
}

@Composable
private fun getTextColor(
    state: PokitInputState,
): Color {
    return when (state) {
        PokitInputState.DEFAULT -> PokitTheme.colors.textTertiary

        PokitInputState.INPUT -> PokitTheme.colors.textSecondary

        PokitInputState.ACTIVE -> PokitTheme.colors.textSecondary

        PokitInputState.DISABLE -> PokitTheme.colors.textDisable

        PokitInputState.READ_ONLY -> PokitTheme.colors.textTertiary

        PokitInputState.ERROR -> PokitTheme.colors.textSecondary
    }
}

private fun getState(
    enabled: Boolean,
    readOnly: Boolean,
    focused: Boolean,
    error: Boolean,
    text: String,
): PokitInputState {
    return when {
        !enabled -> {
            PokitInputState.DISABLE
        }

        readOnly -> {
            PokitInputState.READ_ONLY
        }

        focused -> {
            PokitInputState.ACTIVE
        }

        error -> {
            PokitInputState.ERROR
        }

        text.isEmpty() -> {
            PokitInputState.DEFAULT
        }

        else -> {
            PokitInputState.INPUT
        }
    }
}
