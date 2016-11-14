package com.utils


object DateTimeUtil {

    def millisInDays (days: Int): Long = days * 24 * 60 * 60 * 1000.toLong

    def millisInHours (hours: Float): Long = hours.toLong * 60 * 60 * 1000.toLong

    def millisInTwoDays (): Long = millisInDays( 2 )

    def millisInFiveDays = millisInDays( 5 )

    def millisInFifteenDays = millisInDays( 15 )

}
