package com.cl.io.mail

import java.net.URL

import com.cl.result.{RssItem, RssItems}
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

class UtilSpec extends FlatSpec with Matchers {

    behavior of "utill's formatRSSItemsIntoHTML function "

    it should "return an empty unordered list of items when no RSSItems" in {

        val rssItems = new RssItems()

        val xml = scala.xml.XML.loadString(Util.formatRSSItemsIntoHTML(rssItems))

        assertResult("") {
            (xml \ "ul").text
        }
    }

    it should "return unordered list of size one when one item in RSSItems" in {

        val urlString = "http://some.site.com"
        val url = new URL(urlString)

        val rssItem1 = RssItem(12345, "best thing ever", "cool story bro", new DateTime(),
            Some(url), Some(url))

        val rssItems = new RssItems( Set(rssItem1))

        val xml = scala.xml.XML.loadString(Util.formatRSSItemsIntoHTML(rssItems))

        assertResult(1) {
            (xml \\ "li").size
        }

        assertResult(1) {
            (xml \\ "li" \ "a").size
        }

        assertResult(urlString) {
            ((xml \\ "li" \ "a").head \ "@href").text
        }

        assertResult(urlString) {
            ((xml \\ "li" \ "a" \ "image").head \ "@src").text
        }
    }

    it should "return multiple unordered list when multiple items in RSSItems" in {

        val urlString = "http://some.site.com"
        val url = new URL(urlString)

        val rssItem1 = RssItem(12345, "best thing ever", "cool story bro", new DateTime(),
            Some(url), Some(url))

        val rssItem2 = RssItem(123456, "best thing ever..", "cool story bro", new DateTime(),
            Some(url), Some(url))

        val rssItems = new RssItems( Set(rssItem1, rssItem2))

        val xml = scala.xml.XML.loadString(Util.formatRSSItemsIntoHTML(rssItems))

        assertResult(2) {
            (xml \\ "li").size
        }

        assertResult(2) {
            (xml \\ "a").size
        }

    }

}
