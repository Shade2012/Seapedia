package com.example.seapedia.global.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJavaInstant

object TimeFormatting {

    @OptIn(ExperimentalTime::class)
    fun formatRelativeTime(
        instant: Instant
    ): String {
        val now = Clock.System.now()

        val duration = now - instant

        return when {
            duration.inWholeMinutes < 1 ->
                "Just now"

            duration.inWholeHours < 1 ->
                "${duration.inWholeMinutes} minute${if (duration.inWholeMinutes > 1) "s" else ""} ago"

            duration.inWholeDays < 1 ->
                "${duration.inWholeHours} hour${if (duration.inWholeHours > 1) "s" else ""} ago"

            duration.inWholeDays < 7 ->
                "${duration.inWholeDays} day${if (duration.inWholeDays > 1) "s" else ""} ago"

            else -> {
                val localDate = instant
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date

                "${localDate.day} ${
                    localDate.month.name.lowercase()
                        .replaceFirstChar { it.uppercase() }
                        .take(3)
                } ${localDate.year}"
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun formatOverdueTime(
        instant: Instant
    ): String {
        val now = Clock.System.now()

        // Already overdue
        if (instant <= now) {
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

            return "%02d %s %d, %02d:%02d".format(
                localDateTime.day,
                localDateTime.month.name.lowercase()
                    .replaceFirstChar { it.uppercase() }
                    .take(3),
                localDateTime.year,
                localDateTime.hour,
                localDateTime.minute
            )
        }

        val duration = instant - now

        return when {
            duration.inWholeMinutes < 1 ->
                "Closing now"

            duration.inWholeHours < 1 ->
                "${duration.inWholeMinutes} minute${if (duration.inWholeMinutes > 1) "s" else ""} remaining"

            duration.inWholeDays < 1 ->
                "${duration.inWholeHours} hour${if (duration.inWholeHours > 1) "s" else ""} remaining"

            duration.inWholeDays <= 2 ->
                "${duration.inWholeDays} day${if (duration.inWholeDays > 1) "s" else ""} remaining"

            else -> {
                val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

                "%02d %s %d, %02d:%02d".format(
                    localDateTime.day,
                    localDateTime.month.name.lowercase()
                        .replaceFirstChar { it.uppercase() }
                        .take(3),
                    localDateTime.year,
                    localDateTime.hour,
                    localDateTime.minute
                )
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalTime::class)
    fun formatOrderCard(instant: Instant): String {
        val localDateTime = instant
            .toLocalDateTime(TimeZone.currentSystemDefault())

        val formatter = DateTimeFormatter.ofPattern("d MMM, h:mm a")

        return localDateTime
            .toJavaLocalDateTime()
            .format(formatter)
    }

    @OptIn(ExperimentalTime::class)
    fun Instant.toDisplayString(): String {
        val dateTime = toLocalDateTime(TimeZone.currentSystemDefault())

        val month = when (dateTime.month.number) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }

        return "%02d %s %04d %02d:%02d".format(
            dateTime.day,
            month,
            dateTime.year,
            dateTime.hour,
            dateTime.minute
        )
    }
}