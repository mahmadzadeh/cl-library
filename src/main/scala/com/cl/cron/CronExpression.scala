package com.cl.cron

class CronExpression {
    def everyNSeconds(n: Int): String =
        if (isValidMinutes( n ))
            s"${n} * * * ? *"
        else
            throw new IllegalArgumentException( "number of seconds has to be 0<= n <= 59 " )


    def everyNMinutes(n: Int): String =
        if (isValidMinutes( n ))
            s"* ${n} * * ? *"
        else
            throw new IllegalArgumentException( "number of minutes has to be 0<= n <= 59 " )

    def everyHour: String = "0 0 * * ? *"

    def everyNHour(n: Int): String =
        if (isValidHours( n ))
            s"0 0 0/${n} * ? *"
        else
            throw new IllegalArgumentException( "hour value has to be 0<= n <= 23 " )

    def atHourN(n: Int): String =
        if (isValidHours( n ))
            s"0 0 ${n} * ? *"
        else
            throw new IllegalArgumentException( "hour value has to be 0<= n <= 23 " )

    def atNMinutesPassedHourM(n: Int, m: Int): String =
        if (isValidMinutes( n ) && isValidHours( m ))
            s"0 ${n} ${m} * ? *"
        else
            throw new IllegalArgumentException( "number of minutes has to be 0<= n <= 59 and hour value has to be 0<= n <= 23 " )


    def isValidMinutes(n: Int): Boolean = ( n >= 0 ) && ( n <= 59 )

    def isValidHours(n: Int): Boolean = ( n >= 0 ) && ( n <= 23 )
}
