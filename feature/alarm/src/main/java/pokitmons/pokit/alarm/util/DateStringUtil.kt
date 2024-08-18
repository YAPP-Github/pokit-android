package pokitmons.pokit.alarm.util

import android.icu.util.Calendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pokitmons.pokit.alarm.R

private const val HOUR_SECOND = 60 * 60
private const val DAY_SECOND = 24 * HOUR_SECOND
private const val MONTH_SECOND = 30 * DAY_SECOND
private const val YEAR_SECOND = 365 * DAY_SECOND

@Composable
fun diffString(createdAtCalendar: Calendar, currentCalendar: Calendar = Calendar.getInstance()): String {
    val diffTimeSecond = (currentCalendar.timeInMillis - createdAtCalendar.timeInMillis) / 1000
    return when {
        (diffTimeSecond < 60) -> {
            stringResource(id = R.string.just_now)
        }
        (diffTimeSecond < HOUR_SECOND) -> {
            stringResource(id = R.string.minute_before, (diffTimeSecond / 60))
        }
        (diffTimeSecond < DAY_SECOND) -> {
            stringResource(id = R.string.hour_before, (diffTimeSecond / HOUR_SECOND))
        }
        (diffTimeSecond < MONTH_SECOND) -> {
            stringResource(id = R.string.day_before, (diffTimeSecond / DAY_SECOND))
        }
        (diffTimeSecond < YEAR_SECOND) -> {
            stringResource(id = R.string.month_before, (diffTimeSecond / MONTH_SECOND))
        }
        else -> {
            stringResource(id = R.string.year_before, (diffTimeSecond / YEAR_SECOND))
        }
    }
}
