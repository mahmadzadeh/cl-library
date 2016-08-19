package com.cl.result

import java.net.URL

import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

class ResultSpec extends FlatSpec with Matchers {

    behavior of "a url result known as Result"

    val rssItem1 = RssItem(12345, "best thing ever", "cool story bro", new DateTime(),
                              Some(new URL("http://some.site.com")), None)
    val rssItem2 = RssItem(4567, "second best thing ever", "lame story bro", new DateTime(),
                              Some(new URL("http://some.site.com")), None)
    val rssItem3 = RssItem(4567, "second best thing ever now changed", "lame story bro", new DateTime(),
                              Some(new URL("http://some.site.com")), None)
    val rssItem4 = RssItem(8910, "something ever", "yaya", new DateTime(),
                              Some(new URL("http://some.site.com")), None)


    it should "be able to diff two empty results" in {
        val thisResult = new RssItems()
        val thatResult = new RssItems()

        assertResult(0) {
            thisResult.minus(thatResult).items.size
        }
    }

    it should "be able to diff results one empty and one not empty" in {
        val thisResult = new RssItems(Set(rssItem1, rssItem2))
        val thatResult = new RssItems()

        assertResult(2) {
            thisResult.minus(thatResult).items.size
        }
        assertResult(0) {
            thatResult.minus(thisResult).items.size
        }
    }

    it should "be able to diff two non-empty results" in {
        val firstResult = new RssItems(Set(rssItem1, rssItem2))
        val secondResult = new RssItems(Set(rssItem3, rssItem4))

        val items = secondResult.minus(firstResult).items

        assertResult(1) {
            items.size
        }

        assertResult(8910) {
            items.head.adId
        }
    }

    behavior of "ids function"

    it should "return no IDs when result is empty" in {
        val firstResult = new RssItems()

        assertResult(Set.empty) {
            firstResult.ids
        }
    }

    it should "return IDs of the contained rssItem set" in {
        val firstResult = new RssItems(Set(rssItem1))

        assertResult(Set(12345L)) {
            firstResult.ids
        }
    }
}
