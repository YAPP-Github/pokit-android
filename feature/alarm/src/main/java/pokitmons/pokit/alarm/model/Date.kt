package pokitmons.pokit.alarm.model

import android.icu.util.Calendar
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Date(
    val year: Int = 2024,
    val month: Int = 8,
    val day: Int = 17,
    val hour: Int = 15,
    val minute: Int = 41,
) {
    fun getCalendar(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
    }

    companion object {
        fun getInstanceFromString(
            createdAtString: String,
            pattern: String = "yyyy-MM-dd HH:mm:ss",
        ): Date {
            try {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val formatter = DateTimeFormatter.ofPattern(pattern)
                    val dateTime = LocalDateTime.parse(createdAtString, formatter)
                    Date(
                        year = dateTime.year,
                        month = dateTime.monthValue,
                        day = dateTime.dayOfMonth,
                        hour = dateTime.hour,
                        minute = dateTime.minute
                    )
                } else {
                    val date = SimpleDateFormat(pattern, Locale.KOREA).parse(createdAtString)
                    val calendar = Calendar.getInstance().apply { time = date }
                    Date(
                        year = calendar.get(Calendar.YEAR),
                        month = calendar.get(Calendar.MONTH) + 1,
                        day = calendar.get(Calendar.DAY_OF_MONTH),
                        hour = calendar.get(Calendar.HOUR_OF_DAY),
                        minute = calendar.get(Calendar.MINUTE)
                    )
                }
            } catch (e: Exception) {
                return Date(year = 2000, month = 1, day = 1, hour = 0, minute = 0)
            }
        }
    }
}
