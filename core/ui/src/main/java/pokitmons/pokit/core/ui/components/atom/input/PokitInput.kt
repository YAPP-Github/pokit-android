package pokitmons.pokit.core.ui.components.atom.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.R
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIcon
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputIconPosition
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputShape
import pokitmons.pokit.core.ui.components.atom.input.attributes.PokitInputState
import pokitmons.pokit.core.ui.components.atom.input.subcomponents.container.PokitInputContainer
import pokitmons.pokit.core.ui.components.atom.input.subcomponents.icon.PokitInputIcon
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun PokitInput(
    text : String,
    hintText : String,
    onChangeText : (String) -> Unit,
    icon: PokitInputIcon?,
    modifier : Modifier = Modifier,
    shape: PokitInputShape = PokitInputShape.RECTANGLE,
    state: PokitInputState = PokitInputState.DEFAULT,
) {
    val textColor = getTextColor(state = state)
    val textStyle = PokitTheme.typography.body3Medium.copy(color = textColor)

    BasicTextField(
        value = text,
        onValueChange = onChangeText,
        textStyle = textStyle,
        decorationBox = { innerTextFiled ->
            PokitInputContainer(
                iconPosition = icon?.position,
                modifier = modifier,
                shape = shape,
                state = state,
            ) {
                if (icon == null) {
                    Box(modifier = Modifier.height(24.dp)) // 아이콘 없을 떄 공간 맞추기용
                }

                if (icon?.position == PokitInputIconPosition.LEFT) {
                    PokitInputIcon(state = state, resourceId = icon.resourceId)
                    Box(modifier = Modifier.width(8.dp))
                }

                if (text.isEmpty()) {
                    Text(text = hintText, style = textStyle)
                }

                Box(modifier = Modifier.weight(1f)) {
                    innerTextFiled()
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
) : Color {
    return when(state) {
        PokitInputState.DEFAULT -> {
            PokitTheme.colors.textTertiary
        }
        PokitInputState.INPUT -> {
            PokitTheme.colors.textSecondary
        }
        PokitInputState.ACTIVE -> {
            PokitTheme.colors.textSecondary
        }
        PokitInputState.DISABLE -> {
            PokitTheme.colors.textDisable
        }
        PokitInputState.READ_ONLY -> {
            PokitTheme.colors.textTertiary
        }
        PokitInputState.ERROR -> {
            PokitTheme.colors.textSecondary
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PokitInputPreview() {
    val scrollState = rememberScrollState()
    PokitTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            enumValues<PokitInputShape>().forEach { shape ->
                enumValues<PokitInputState>().forEach { state ->
                    enumValues<PokitInputIconPosition>().forEach { iconPosition ->
                        PokitInput(
                            text = "",
                            hintText = "내용을 입력해주세요.",
                            onChangeText = {},
                            state = state,
                            shape = shape,
                            icon = PokitInputIcon(iconPosition, R.drawable.icon_24_search)
                        )
                    }
                    PokitInput(
                        text = "",
                        hintText = "내용을 입력해주세요.",
                        onChangeText = {},
                        state = state,
                        shape = shape,
                        icon = null,
                    )
                }
            }
        }
    }
}
