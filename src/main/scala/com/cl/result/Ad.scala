package com.cl.result

import com.cl.PosterDesperationLevel
import com.cl.PosterDesperationLevel.PosterDesperationLevel

case class Ad(val id: String, val title: String,
              val postedDate: Long, val updatedDate: Option[Long],
              val url: String) {

    def isPostedBefore(anotherAd: Ad): Boolean = this.postedDate < anotherAd.postedDate

    def posterDesperationLevel(): PosterDesperationLevel =
                    PosterDesperationLevel.tellMeHowDesparatePosterIs(this.postedDate)
}
