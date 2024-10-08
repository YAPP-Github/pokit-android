package pokitmons.pokit.keyword

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.LoginViewModel
import pokitmons.pokit.SignUpState
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.chip.PokitChip
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.core.ui.R as Ui
import pokitmons.pokit.login.R as Login

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordScreen(
    viewModel: LoginViewModel,
    onNavigateToSignUpScreen: () -> Unit,
    onBackPressed: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.setCategories()
    }

    val context: Context = LocalContext.current

    val signUpState by viewModel.signUpState.collectAsState()

    when (signUpState) {
        is SignUpState.Init -> Unit
        is SignUpState.SignUp -> onNavigateToSignUpScreen()
        is SignUpState.Failed -> {
            Toast.makeText(context, (signUpState as SignUpState.Failed).error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp)
            .padding(bottom = 8.dp)
    ) {
        Column {
            Icon(
                modifier = Modifier.noRippleClickable { onBackPressed() },
                painter = painterResource(id = Ui.drawable.icon_24_arrow_left),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = Login.string.keyword_title),
                style = PokitTheme.typography.title1
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = Login.string.select_keyword),
                style = PokitTheme.typography.title3
            )
            Spacer(modifier = Modifier.height(36.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                maxItemsInEachRow = 3
            ) {
                viewModel.categories.forEachIndexed { index, category ->
                    PokitChip(
                        state = if (category.isSelected.value) PokitChipState.FILLED else PokitChipState.DEFAULT,
                        data = null,
                        size = PokitChipSize.MEDIUM,
                        text = category.name,
                        removeIconPosition = null,
                        onClickRemove = { },
                        onClickItem = { viewModel.onClickCategoryItem(category) }
                    )
                }
            }
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(id = pokitmons.pokit.login.R.string.next),
            icon = null,
            size = PokitButtonSize.LARGE,
            onClick = { viewModel.signUp() },
            enable = viewModel.categories.count { it.isSelected.value } >= 1
        )
    }
}
