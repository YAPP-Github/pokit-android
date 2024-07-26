package pokitmons.pokit.search.components.filter

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIcon
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonIconPosition
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonShape
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonStyle
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonType
import pokitmons.pokit.search.R
import pokitmons.pokit.core.ui.R.drawable as coreDrawable
import pokitmons.pokit.search.model.Filter

@Composable
fun FilterArea(
    filter: Filter? = null,
    onClickFilter: () -> Unit = {},
    onClickPeriod: () -> Unit = {},
    onClickPokitName: () -> Unit = {},
    onClickBookmark: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 24.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Spacer(modifier = Modifier.width(20.dp))

        if (filter == null) {
            PokitButton(
                text = stringResource(id = R.string.filter),
                icon = PokitButtonIcon(
                    resourceId = coreDrawable.icon_24_filter,
                    position = PokitButtonIconPosition.LEFT
                ),
                onClick = onClickFilter,
                size = PokitButtonSize.SMALL,
                shape = PokitButtonShape.ROUND,
                style = PokitButtonStyle.STROKE,
                type = PokitButtonType.SECONDARY
            )

            PokitButton(
                text = stringResource(id = R.string.pokit_name),
                icon = PokitButtonIcon(
                    resourceId = coreDrawable.icon_24_arrow_down,
                    position = PokitButtonIconPosition.RIGHT
                ),
                onClick = onClickPokitName,
                size = PokitButtonSize.SMALL,
                shape = PokitButtonShape.ROUND,
                style = PokitButtonStyle.STROKE,
                type = PokitButtonType.SECONDARY
            )

            PokitButton(
                text = stringResource(id = R.string.collect_show),
                icon = PokitButtonIcon(
                    resourceId = coreDrawable.icon_24_arrow_down,
                    position = PokitButtonIconPosition.RIGHT
                ),
                onClick = onClickBookmark,
                size = PokitButtonSize.SMALL,
                shape = PokitButtonShape.ROUND,
                style = PokitButtonStyle.STROKE,
                type = PokitButtonType.SECONDARY
            )

            PokitButton(
                text = stringResource(id = R.string.period),
                icon = PokitButtonIcon(
                    resourceId = coreDrawable.icon_24_arrow_down,
                    position = PokitButtonIconPosition.RIGHT
                ),
                onClick = onClickPeriod,
                size = PokitButtonSize.SMALL,
                shape = PokitButtonShape.ROUND,
                style = PokitButtonStyle.STROKE,
                type = PokitButtonType.SECONDARY
            )
        } else {
            PokitButton(
                text = stringResource(id = R.string.filter),
                icon = PokitButtonIcon(
                    resourceId = coreDrawable.icon_24_filter,
                    position = PokitButtonIconPosition.LEFT
                ),
                onClick = onClickFilter,
                size = PokitButtonSize.SMALL,
                shape = PokitButtonShape.ROUND,
                style = PokitButtonStyle.FILLED
            )

            filter.selectedPokits.map { pokit ->
                PokitButton(
                    text = pokit.title,
                    icon = PokitButtonIcon(
                        resourceId = coreDrawable.icon_24_arrow_down,
                        position = PokitButtonIconPosition.RIGHT
                    ),
                    onClick = onClickPokitName,
                    size = PokitButtonSize.SMALL,
                    shape = PokitButtonShape.ROUND,
                    style = PokitButtonStyle.STROKE
                )
            }

            if (filter.bookmark) {
                PokitButton(
                    text = stringResource(id = R.string.bookmark),
                    icon = PokitButtonIcon(
                        resourceId = coreDrawable.icon_24_arrow_down,
                        position = PokitButtonIconPosition.RIGHT
                    ),
                    onClick = onClickBookmark,
                    size = PokitButtonSize.SMALL,
                    shape = PokitButtonShape.ROUND,
                    style = PokitButtonStyle.STROKE
                )
            }

            if (filter.notRead) {
                PokitButton(
                    text = stringResource(id = R.string.not_read),
                    icon = PokitButtonIcon(
                        resourceId = coreDrawable.icon_24_arrow_down,
                        position = PokitButtonIconPosition.RIGHT
                    ),
                    onClick = onClickBookmark,
                    size = PokitButtonSize.SMALL,
                    shape = PokitButtonShape.ROUND,
                    style = PokitButtonStyle.STROKE
                )
            }

            filter.getDateString()?.let { dateString ->
                PokitButton(
                    text = dateString,
                    icon = PokitButtonIcon(
                        resourceId = coreDrawable.icon_24_arrow_down,
                        position = PokitButtonIconPosition.RIGHT
                    ),
                    onClick = onClickPeriod,
                    size = PokitButtonSize.SMALL,
                    shape = PokitButtonShape.ROUND,
                    style = PokitButtonStyle.STROKE
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))
    }
}
