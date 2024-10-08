package com.strayalpaca.pokitdetail.components.template.filterselectbottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalpaca.pokitdetail.R
import com.strayalpaca.pokitdetail.components.block.CheckboxText
import com.strayalpaca.pokitdetail.components.block.RadioText
import com.strayalpaca.pokitdetail.model.Filter
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

@Composable
internal fun FilterSelectBottomSheet(
    filter: Filter = Filter(),
    onHideRequest: () -> Unit = {},
    onFilterChange: (Filter) -> Unit = {},
    show: Boolean = false,
) {
    var currentFilter by remember { mutableStateOf(filter) }

    PokitBottomSheet(
        onHideBottomSheet = onHideRequest,
        show = show
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.filter),
                style = PokitTheme.typography.title3.copy(color = PokitTheme.colors.textPrimary)
            )

            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterEnd),
                onClick = onHideRequest
            ) {
                Icon(
                    painter = painterResource(id = coreDrawable.icon_24_x),
                    contentDescription = "close filter bottomSheet"
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sort),
                style = PokitTheme.typography.body1Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            RadioText(
                selected = currentFilter.recentSortUsed,
                title = stringResource(id = R.string.sort_recent),
                onClick = remember { { currentFilter = currentFilter.copy(recentSortUsed = true) } },
                modifier = Modifier.height(40.dp)
            )
            RadioText(
                selected = !currentFilter.recentSortUsed,
                title = stringResource(id = R.string.sort_old),
                onClick = remember { { currentFilter = currentFilter.copy(recentSortUsed = false) } },
                modifier = Modifier.height(40.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.collect_look),
                style = PokitTheme.typography.body1Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            CheckboxText(
                checked = currentFilter.bookmarkChecked,
                title = stringResource(id = R.string.bookmark),
                onClick = remember { { currentFilter = currentFilter.copy(bookmarkChecked = !currentFilter.bookmarkChecked) } },
                modifier = Modifier.height(36.dp)
            )
            CheckboxText(
                checked = currentFilter.notReadChecked,
                title = stringResource(id = R.string.not_read),
                onClick = remember { { currentFilter = currentFilter.copy(notReadChecked = !currentFilter.notReadChecked) } },
                modifier = Modifier.height(36.dp)
            )

            Spacer(modifier = Modifier.height(38.dp))

            PokitButton(
                text = stringResource(id = R.string.confirmation),
                icon = null,
                onClick = remember { { onFilterChange(currentFilter) } },
                modifier = Modifier.fillMaxWidth(),
                size = PokitButtonSize.LARGE
            )
        }
    }
}
