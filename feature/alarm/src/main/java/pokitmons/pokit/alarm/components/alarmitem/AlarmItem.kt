package pokitmons.pokit.alarm.components.alarmitem

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pokitmons.pokit.alarm.R
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.alarm.util.diffString
import pokitmons.pokit.core.ui.components.block.pushcard.PushCard
import pokitmons.pokit.core.ui.theme.PokitTheme
import kotlin.math.roundToInt
import pokitmons.pokit.core.ui.R.drawable as coreDrawable

enum class DragValue { Expanded, Center }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmItem(
    alarm: Alarm,
    onClickAlarm: (String) -> Unit,
    onClickRemove: (String) -> Unit,
) {
    val density = LocalDensity.current
    val dragMaxOffset = with(density) {
        (-60).dp.toPx()
    }
    val draggableState = remember {
        AnchoredDraggableState(
            anchors = DraggableAnchors {
                DragValue.Expanded at dragMaxOffset
                DragValue.Center at 0f
            },
            initialValue = DragValue.Center,
            animationSpec = tween(),
            positionalThreshold = { totalDistance -> totalDistance * 0.5f },
            velocityThreshold = { with(density) { 60.dp.toPx() } }
        )
    }
    val timeDiffString = diffString(createdAtCalendar = alarm.createdAt.getCalendar())

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(60.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onClickRemove(alarm.id) }
                    )
                    .background(PokitTheme.colors.error)
                    .align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = coreDrawable.icon_24_trash),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(PokitTheme.colors.inverseWh),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(id = R.string.remove),
                    style = PokitTheme.typography.body3Medium.copy(color = PokitTheme.colors.inverseWh)
                )
            }

            Row(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = draggableState
                                .requireOffset()
                                .roundToInt(),
                            y = 0
                        )
                    }
                    .anchoredDraggable(
                        state = draggableState,
                        orientation = Orientation.Horizontal
                    )
                    .fillMaxWidth()
                    .background(PokitTheme.colors.backgroundBase),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PushCard(
                    title = alarm.title,
                    sub = "여기 문구 뭘로 하나요?\n뭐가 들어가야 됨????",
                    timeString = timeDiffString,
                    painter = rememberAsyncImagePainter(alarm.thumbnail),
                    read = !alarm.read,
                    onClick = remember {
                        {
                            onClickAlarm(alarm.id)
                        }
                    }
                )
            }
        }

        HorizontalDivider(thickness = 1.dp, color = PokitTheme.colors.borderTertiary)
    }
}
