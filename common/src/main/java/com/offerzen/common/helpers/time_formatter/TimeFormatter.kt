package com.offerzen.common.helpers.time_formatter

interface TimeFormatter {
    fun format(dateTimeValue: String?, inputPattern: String, outputPattern: String): String
    fun parseToEpoch(dateTimeValue: String?, inputPattern: String): Long
}