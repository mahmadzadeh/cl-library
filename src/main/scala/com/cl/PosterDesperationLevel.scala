package com.cl

import com.utils.DateTimeUtil._

object PosterDesperationLevel extends Enumeration {

    type PosterDesperationLevel = Value

    val HOPEFUL   = Value("hopeful")
    val OPTIMISTIC = Value("optimistic")
    val DESPERATE = Value("desperate")

    def tellMeHowDesparatePosterIs(postedDate:Long ) : PosterDesperationLevel = {
        val now = System.currentTimeMillis()

        if (postedWithinLastTwoDays(postedDate, now )) HOPEFUL
        else if (postedWithinLastTenDays(postedDate, now)) OPTIMISTIC
        else DESPERATE

    }

    def postedWithinLastTwoDays(postedDate:Long, now:Long ): Boolean = postedDate >= now - millisInDays(2)
    def postedWithinLastTenDays(postedDate:Long, now:Long ): Boolean = postedDate >= now - millisInDays(10)

}
