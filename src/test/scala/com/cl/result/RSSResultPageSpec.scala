package com.cl.result

import com.cl.FileBasedTest
import org.joda.time.format._
import org.scalatest.{FlatSpec, Matchers}

class RSSResultPageSpec extends FlatSpec with Matchers with FileBasedTest{

    behavior of "an RSS result page"
    val rssFile = readFile("rss.xml")

    it should "throw IllegalArgumentException when given invalid RSS content" in {
        val invalidEmptyContent = ""
        val invalidNullContent  = null

        intercept[IllegalArgumentException]{
            new RssResultPage(invalidEmptyContent)
        }
        intercept[IllegalArgumentException]{
            new RssResultPage(invalidNullContent)
        }
    }

    it should "return result count" in {

        assertResult(25){
            new RssResultPage(rssFile).itemCount
        }
    }

    it should "return the link to the search page" in {

        assertResult("http://vancouver.craigslist.ca/search/pta?query=BMW%20tires&amp;s=52") {
            new RssResultPage(rssFile).link.toString
        }

    }

    it should "date time of the RSS feed" in {
        assertResult(DateTimeFormat.forPattern(RssResultPage.DATE_PATTERN).parseDateTime("2014-08-17T11:48:18-07:00")) {
            new RssResultPage(rssFile).updateDate
        }
    }

    it should "be able to return a list of RSS items" in {
        val listOfItems = new RssResultPage(rssFile).items

        assertResult(25) {
            listOfItems.size
        }
    }

}




