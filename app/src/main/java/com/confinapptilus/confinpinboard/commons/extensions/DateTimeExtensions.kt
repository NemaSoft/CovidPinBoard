package com.confinapptilus.confinpinboard.commons.extensions

import android.content.Context
import android.text.format.DateFormat
import com.confinapptilus.confinpinboard.R
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToDate(timestamp: Long?): String =
    timestamp?.let {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
        DateFormat.format("dd/MM/yyyy", calendar).toString()
    } ?: ""

fun convertTimestampToTime(timestamp: Long?): String =
    timestamp?.let {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
        DateFormat.format("HH:mm", calendar).toString()
    } ?: ""

fun convertDateToTimestamp(dateText: String, timeText: String): Long? {
    if (dateText.isEmpty()||timeText.isEmpty()){
        return 0
    }
    return SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault()
    ).parse(dateText.plus(" ").plus(timeText))?.time
}

fun Context.formatDate(dateText: String, timeText: String): String {
    if (dateText.isEmpty() || timeText.isEmpty()) {
        return ""
    }
    return when {
        isToday(dateText) -> getString(R.string.today_pattern, timeText)
        isTomorrow(dateText) -> getString(R.string.tomorrow_pattern, timeText)
        else -> getString(R.string.date_time_pattern, dateText, timeText)
    }
}

private fun isToday(dateText: String): Boolean {
    val currentTimestamp = Calendar.getInstance().timeInMillis
    val currentDate = convertTimestampToDate(currentTimestamp)

    return currentDate == dateText
}

private fun isTomorrow(dateText: String): Boolean {
    val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, 1)
    }
    val tomorrowTimestamp = calendar.timeInMillis
    val tomorrowDate = convertTimestampToDate(tomorrowTimestamp)

    return tomorrowDate == dateText
}

fun isAvailable(endTimestamp: Long): Boolean {
    val currentTimestamp = Calendar.getInstance().timeInMillis

    return currentTimestamp < endTimestamp
}
