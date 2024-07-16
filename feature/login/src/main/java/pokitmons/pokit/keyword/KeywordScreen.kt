package pokitmons.pokit.keyword

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.chip.PokitChip
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipSize
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R as Ui
import pokitmons.pokit.login.R as Login

@Composable
fun KeywordScreen(
    onNavigateToSignUpScreen: () -> Unit,
    popBackStack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp)
            .padding(bottom = 8.dp)
    ) {
        Column {
            Icon(painter = painterResource(id = Ui.drawable.icon_24_arrow_left), contentDescription = null)
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

            // TODO FlowRow도 사용해보기
            Column {
                val categories: List<List<String>> = listOf(
                    stringResource(id = Login.string.sports_and_leisure),
                    stringResource(id = Login.string.phrases_and_office),
                    stringResource(id = Login.string.fashion),
                    stringResource(id = Login.string.travel),
                    stringResource(id = Login.string.economy_and_politics),
                    stringResource(id = Login.string.movies_and_dramas),
                    stringResource(id = Login.string.restaurants),
                    stringResource(id = Login.string.interior),
                    stringResource(id = Login.string.it),
                    stringResource(id = Login.string.design),
                    stringResource(id = Login.string.self_development),
                    stringResource(id = Login.string.humor),
                    stringResource(id = Login.string.music),
                    stringResource(id = Login.string.job_info)
                ).chunked(3)

                Row {
                    categories[0].forEach { category ->
                        PokitChip(
                            data = null,
                            size = PokitChipSize.MEDIUM,
                            text = category,
                            removeIconPosition = null,
                            onClickRemove = { },
                            onClickItem = { }
                        )
                        Spacer(modifier = Modifier.padding(start = 12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    categories[1].forEach { category ->
                        PokitChip(
                            data = null,
                            size = PokitChipSize.MEDIUM,
                            text = category,
                            removeIconPosition = null,
                            onClickRemove = { },
                            onClickItem = { }
                        )
                        Spacer(modifier = Modifier.padding(start = 12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    categories[2].forEach { category ->
                        PokitChip(
                            data = null,
                            size = PokitChipSize.MEDIUM,
                            text = category,
                            removeIconPosition = null,
                            onClickRemove = { },
                            onClickItem = { }
                        )
                        Spacer(modifier = Modifier.padding(start = 12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    categories[3].forEach { category ->
                        PokitChip(
                            data = null,
                            size = PokitChipSize.MEDIUM,
                            text = category,
                            removeIconPosition = null,
                            onClickRemove = { },
                            onClickItem = { }
                        )
                        Spacer(modifier = Modifier.padding(start = 12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    categories[4].forEach { category ->
                        PokitChip(
                            data = null,
                            size = PokitChipSize.MEDIUM,
                            text = category,
                            removeIconPosition = null,
                            onClickRemove = { },
                            onClickItem = { }
                        )
                        Spacer(modifier = Modifier.padding(start = 12.dp))
                    }
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
            onClick = { onNavigateToSignUpScreen() }
        )
    }
}
