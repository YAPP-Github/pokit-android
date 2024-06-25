package pokitmons.pokit.core.ui.components.block.labeled_input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
    state: PokitInputState = PokitInputState.DEFAULT,
) {
    val subTextColor = getSubTextColor(state = state)

    Column(
       modifier = modifier
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
            state = state,
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
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            enumValues<PokitInputState>().forEach { state ->
                LabeledInput(label = "Label", sub = "내용을 입력해주세요", maxLength = 10, inputText = "으앙", hintText = "내용을 입력해주세요", onChangeText = {}, state = state)
            }
        }
    }
}
