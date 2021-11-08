package com.lealpy.notebook.utils

import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun getGMT(): Long {
        return GregorianCalendar().timeZone.rawOffset.toLong()
    }

    fun getTimestampByInt(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {
        return GregorianCalendar(year, month, day, hour, minute).timeInMillis
    }

    fun getDateStringByInt(year: Int, month: Int, day: Int): String {
        return SimpleDateFormat("dd.MM.yyyy")
            .format(
                Date(
                    getTimestampByInt(year, month, day, 0, 0)
                )
            )
    }

    fun getTimeStringByInt(hour: Int, minute: Int): String {
        return SimpleDateFormat("HH:mm")
            .format(
                Date(
                    getTimestampByInt(1970, 0, 1, hour, minute)
                )
            )
    }

    fun getDateStringByTimestamp(date: Long): String {
        return SimpleDateFormat("dd.MM.yyyy").format(Date(date))
    }

    fun getTimeStringByTimestamp(date: Long): String {
        return SimpleDateFormat("HH:mm").format(Date(date))
    }

    fun getYearIntByTimestamp(date: Long): Int {
        return SimpleDateFormat("yyyy").format(Date(date)).toInt()
    }

    fun getMonthIntByTimestamp(date: Long): Int {
        return SimpleDateFormat("MM").format(Date(date)).toInt() - 1
    }

    fun getDayIntByTimestamp(date: Long): Int {
        return SimpleDateFormat("dd").format(Date(date)).toInt()
    }

    fun getHourIntByTimestamp(date: Long): Int {
        return SimpleDateFormat("HH").format(Date(date)).toInt()
    }

    fun getMinuteIntByTimestamp(date: Long): Int {
        return SimpleDateFormat("mm").format(Date(date)).toInt()
    }

    fun getCurrentYearInt(): Int {
        return SimpleDateFormat("yyyy").format(Date()).toInt()
    }

    fun getCurrentMonthInt(): Int {
        return SimpleDateFormat("MM").format(Date()).toInt() - 1
    }

    fun getCurrentDayInt(): Int {
        return SimpleDateFormat("dd").format(Date()).toInt()
    }

    fun getCurrentHourInt(): Int {
        return SimpleDateFormat("HH").format(Date()).toInt()
    }

    fun getCurrentMinuteInt(): Int {
        return SimpleDateFormat("mm").format(Date()).toInt()
    }

    fun getTimeRange(date: Long?): String {
        return if (date != null) {
            when (date % Const.MILLIS_IN_DAY) {
                in  0 * Const.MILLIS_IN_HOUR until  1 * Const.MILLIS_IN_HOUR - 1 -> "00:00-01:00"
                in  1 * Const.MILLIS_IN_HOUR until  2 * Const.MILLIS_IN_HOUR - 1 -> "01:00-02:00"
                in  2 * Const.MILLIS_IN_HOUR until  3 * Const.MILLIS_IN_HOUR - 1 -> "02:00-03:00"
                in  3 * Const.MILLIS_IN_HOUR until  4 * Const.MILLIS_IN_HOUR - 1 -> "03:00-04:00"
                in  4 * Const.MILLIS_IN_HOUR until  5 * Const.MILLIS_IN_HOUR - 1 -> "04:00-05:00"
                in  5 * Const.MILLIS_IN_HOUR until  6 * Const.MILLIS_IN_HOUR - 1 -> "05:00-06:00"
                in  6 * Const.MILLIS_IN_HOUR until  7 * Const.MILLIS_IN_HOUR - 1 -> "06:00-07:00"
                in  7 * Const.MILLIS_IN_HOUR until  8 * Const.MILLIS_IN_HOUR - 1 -> "07:00-08:00"
                in  8 * Const.MILLIS_IN_HOUR until  9 * Const.MILLIS_IN_HOUR - 1 -> "08:00-09:00"
                in  9 * Const.MILLIS_IN_HOUR until 10 * Const.MILLIS_IN_HOUR - 1 -> "09:00-10:00"
                in 10 * Const.MILLIS_IN_HOUR until 11 * Const.MILLIS_IN_HOUR - 1 -> "10:00-11:00"
                in 11 * Const.MILLIS_IN_HOUR until 12 * Const.MILLIS_IN_HOUR - 1 -> "11:00-12:00"
                in 12 * Const.MILLIS_IN_HOUR until 13 * Const.MILLIS_IN_HOUR - 1 -> "12:00-13:00"
                in 13 * Const.MILLIS_IN_HOUR until 14 * Const.MILLIS_IN_HOUR - 1 -> "13:00-14:00"
                in 14 * Const.MILLIS_IN_HOUR until 15 * Const.MILLIS_IN_HOUR - 1 -> "14:00-15:00"
                in 15 * Const.MILLIS_IN_HOUR until 16 * Const.MILLIS_IN_HOUR - 1 -> "15:00-16:00"
                in 16 * Const.MILLIS_IN_HOUR until 17 * Const.MILLIS_IN_HOUR - 1 -> "16:00-17:00"
                in 17 * Const.MILLIS_IN_HOUR until 18 * Const.MILLIS_IN_HOUR - 1 -> "17:00-18:00"
                in 18 * Const.MILLIS_IN_HOUR until 19 * Const.MILLIS_IN_HOUR - 1 -> "18:00-19:00"
                in 19 * Const.MILLIS_IN_HOUR until 20 * Const.MILLIS_IN_HOUR - 1 -> "19:00-20:00"
                in 20 * Const.MILLIS_IN_HOUR until 21 * Const.MILLIS_IN_HOUR - 1 -> "20:00-21:00"
                in 21 * Const.MILLIS_IN_HOUR until 22 * Const.MILLIS_IN_HOUR - 1 -> "21:00-22:00"
                in 22 * Const.MILLIS_IN_HOUR until 23 * Const.MILLIS_IN_HOUR - 1 -> "22:00-23:00"
                in 23 * Const.MILLIS_IN_HOUR until 24 * Const.MILLIS_IN_HOUR - 1 -> "23:00-24:00"
                else -> ""
            }
        }
        else ""
    }

}