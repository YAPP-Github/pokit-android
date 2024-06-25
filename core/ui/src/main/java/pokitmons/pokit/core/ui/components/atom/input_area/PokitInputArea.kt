package pokitmons.pokit.core.ui.components.atom.input_area

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.input_area.attributes.PokitInputAreaState
import pokitmons.pokit.core.ui.components.atom.input_area.subcomponents.PokitInputAreaContainer
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitInputArea(
    text : String,
    hintText : String,
    onChangeText : (String) -> Unit,
    modifier : Modifier = Modifier,
    state : PokitInputAreaState = PokitInputAreaState.DEFAULT,
) {
    val textColor = getTextColor(state = state)
    val textStyle = PokitTheme.typography.body3Regular.copy(color = textColor)

    BasicTextField(
        value = text,
        onValueChange = onChangeText,
        textStyle = textStyle,
        minLines = 5,
        decorationBox = { innerTextField ->
            PokitInputAreaContainer(
                state = state,
                modifier = modifier,
            ) {
                if (text.isEmpty()) {
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

@Preview(showBackground = true)
@Composable
fun PokitInputAreaPreview() {
    val scrollState = rememberScrollState()
    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            enumValues<PokitInputAreaState>().forEach { state ->
                PokitInputArea(
                    text = "text",
                    hintText = "내용을 입력해주세요",
                    onChangeText = {},
                    state = state,
                )

            }
        }
    }
}
