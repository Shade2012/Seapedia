package com.example.seapedia.global.utils

import kotlinx.datetime.*
import kotlinx.datetime.LocalDate
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
}