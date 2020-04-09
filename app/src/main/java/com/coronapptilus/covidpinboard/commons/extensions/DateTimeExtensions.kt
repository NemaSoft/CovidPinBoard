package com.coronapptilus.covidpinboard.commons.extensions

import android.content.Context
import android.text.format.DateFormat
import com.coronapptilus.covidpinboard.R
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToDateTime(timestamp: Long?): String =
    timestamp?.let {
        val date = convertTimestampToDate(timestamp)
        val time = convertTimestampToTime(timestamp)
        date.plus(" ").plus(time)
    } ?: ""

fun convertTimestampToDate(timestamp: Long?): String =
    timestamp?.let {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = (timestamp * 1000)
        }
        DateFormat.format("dd/MM/yyyy", calendar).toString()
    } ?: ""

fun convertTimestampToTime(timestamp: Long?): String =
    timestamp?.let {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = (timestamp * 1000)
        }
        DateFormat.format("HH:mm", calendar).toString()
    } ?: ""

fun convertDateToTimestamp(dateText: String, timeText: String): Long? =
    SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateText.plus(" ").plus(timeText))?.let {
        it.time / 1000
    }

fun Context.formatDate(dateText: String, timeText: String): String =
    when {
        isToday(dateText) -> getString(R.string.today_pattern, timeText)
        isTomorrow(dateText) -> getString(R.string.tomorrow_pattern, timeText)
        else -> getString(R.string.date_time_pattern, dateText, timeText)
    }

private fun isToday(dateText: String): Boolean {
    val currentTimestamp = Calendar.getInstance().timeInMillis / 1000
    val currentDate = convertTimestampToDate(currentTimestamp)

    return currentDate == dateText
}

private fun isTomorrow(dateText: String): Boolean {
    val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, 1)
    }
    val tomorrowTimestamp = calendar.timeInMillis / 1000
    val tomorrowDate = convertTimestampToDate(tomorrowTimestamp)

    return tomorrowDate == dateText
}