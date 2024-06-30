package pokitmons.pokit.core.ui.components.block.labeled_input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.input.PokitInput
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun LabeledInput(
    label : String,
    sub : String,
    maxLength : Int,
    inputText : String,
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
            text = inputText
        )
    }
    val subTextColor = getSubTextColor(state)

    Column(
       modifier = modifier
           .onFocusChanged { focusState ->
               focused = focusState.hasFocus
           }
    ) {
        Text(
            text = label,
            style = PokitTheme.typography.body2Medium.copy(color = PokitTheme.colors.textSecondary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        PokitInput(
            text = inputText,
            hintText = hintText,
            onChangeText = onChangeText,
            icon = null,
            isError = isError,
            enable = enable,
            readOnly = readOnly
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (state == PokitInputState.ERROR) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_24_info),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = PokitTheme.colors.error
                )

                Spacer(modifier = Modifier.width(4.dp))
            }

            Text(
                text = sub,
                style = PokitTheme.typography.detail1.copy(color = subTextColor)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${inputText.length}/${maxLength}",
                style = PokitTheme.typography.detail1.copy(color = subTextColor)
            )
        }
    }
}

private fun getState(
    enabled : Boolean,
    readOnly : Boolean,
    focused : Boolean,
    error : Boolean,
    text : String,
) : PokitInputState {
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

@Composable
private fun getSubTextColor(state: PokitInputState) : Color {
    return when(state) {
        PokitInputState.ERROR -> {
            PokitTheme.colors.error
        }
        PokitInputState.DISABLE -> {
            PokitTheme.colors.textDisable
        }
        else -> {
            PokitTheme.colors.textTertiary
        }
    }
}
