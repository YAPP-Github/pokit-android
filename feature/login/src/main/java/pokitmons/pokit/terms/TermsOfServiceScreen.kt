package pokitmons.pokit.terms

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.checkbox.PokitCheckbox
import pokitmons.pokit.core.ui.components.atom.checkbox.attributes.PokitCheckboxStyle
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.login.R

@Composable
fun TermsOfServiceScreen(
    onNavigateToInputNicknameScreen: () -> Unit,
    popBackStack: () -> Unit,
) {
    val termsViewModel: TermsViewModel = viewModel() // TODO hiltViewModel 마이그레이션 예정
    val termsState by termsViewModel.termsState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 28.dp)
    ) {
        Column {
            Icon(
                painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_arrow_left),
                contentDescription = "뒤로가기",
                modifier = Modifier.clickable { popBackStack() }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.service_privacy_title),
                style = PokitTheme.typography.title1
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .border(
                        shape = RoundedCornerShape(8.dp),
                        width = 1.dp,
                        color = PokitTheme.colors.brand
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.padding(start = 16.dp))

                PokitCheckbox(
                    style = PokitCheckboxStyle.STROKE,
                    checked = termsState.isAllChecked,
                    onClick = { termsViewModel.checkAllTerms() }
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.privacy_all_agree),
                    style = PokitTheme.typography.body1Bold
                )
            }

            Spacer(modifier = Modifier.padding(top = 20.dp))

            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                TermsCheckBoxItem(
                    text = stringResource(id = R.string.personal_data_agree),
                    isChecked = termsState.isPersonalDataChecked,
                    click = { termsViewModel.checkPersonalData() }
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))

                TermsCheckBoxItem(
                    text = stringResource(id = R.string.service_agree),
                    isChecked = termsState.isServiceChecked,
                    click = { termsViewModel.checkServiceTerm() }
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))

                TermsCheckBoxItem(
                    text = stringResource(id = R.string.marketing_agree),
                    isChecked = termsState.isMarketingChecked,
                    click = { termsViewModel.checkMarketing() }
                )
            }
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.next),
            icon = null,
            size = PokitButtonSize.LARGE,
            enable = termsState.isPersonalDataChecked && termsState.isServiceChecked,
            onClick = { onNavigateToInputNicknameScreen() }
        )
    }
}
