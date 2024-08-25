package pokitmons.pokit.terms

import androidx.compose.foundation.border
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
import pokitmons.pokit.core.ui.utils.noRippleClickable
import pokitmons.pokit.core.ui.R as UI
import pokitmons.pokit.login.R as Login

@Composable
fun TermsOfServiceScreen(
    onNavigateToInputNicknameScreen: () -> Unit,
    onBackPressed: () -> Unit,
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
                painter = painterResource(id = UI.drawable.icon_24_arrow_left),
                contentDescription = "뒤로가기",
                modifier = Modifier.noRippleClickable { onBackPressed() }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = Login.string.service_privacy_title),
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
                    text = stringResource(id = Login.string.privacy_all_agree),
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
                    url = "https://www.notion.so/de3468b3be1744538c22a333ae1d0ec8",
                    text = stringResource(id = Login.string.personal_data_agree),
                    isChecked = termsState.isPersonalDataChecked,
                    click = { termsViewModel.checkPersonalData() }
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))

                TermsCheckBoxItem(
                    url = "https://www.notion.so/3bddcd6fd00043abae6b92a50c39b132?pvs=4",
                    text = stringResource(id = Login.string.service_agree),
                    isChecked = termsState.isServiceChecked,
                    click = { termsViewModel.checkServiceTerm() }
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))

                TermsCheckBoxItem(
                    url = "https://www.notion.so/bb6d0d6569204d5e9a7b67e5825f9d10",
                    text = stringResource(id = Login.string.marketing_agree),
                    isChecked = termsState.isMarketingChecked,
                    click = { termsViewModel.checkMarketing() }
                )
            }
        }

        PokitButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(id = Login.string.next),
            icon = null,
            size = PokitButtonSize.LARGE,
            enable = termsState.isPersonalDataChecked && termsState.isServiceChecked,
            onClick = { onNavigateToInputNicknameScreen() }
        )
    }
}
