package com.cl.io

import java.net.URL

import com.cl.io.Converter.{convertRssItemFromXML, convertRssItemToXml, convertRssItemsFromXML, convertRssItemsToXML}
import com.cl.result.{RssItem, RssItems}
import org.joda.time.DateTime
import org.scalatest.{FlatSpec, Matchers}

class ConverterSpec extends FlatSpec with Matchers {

    private val date: DateTime = new DateTime()

    private val linkURL = Some(new URL("http://google.com/action?one=1&two=2"))
    private val imageURL = Some(new URL("http://google.com/action?one=3&two=4"))

    private val adId = 12345L
    private val adIdWithNoImage = 123456L

    private val rssItem = RssItem(adId, "title", "description", date, linkURL, imageURL)
    private val rssItemNoLinks = RssItem(adIdWithNoImage, "title", "description", date, None, None)

    behavior of "RSS item to XML"

    it should "serialize rss item with no link and image" in {
        assertResult("") {
            (convertRssItemToXml(rssItemNoLinks) \\ "link").text
        }

        assertResult("") {
            (convertRssItemToXml(rssItemNoLinks) \\ "image").text
        }
    }

    it should "serialize rss item with link and image" in {
        assert(!(convertRssItemToXml(rssItem) \\ "link").text.isEmpty)
        assert(!(convertRssItemToXml(rssItem) \\ "image").text.isEmpty)
    }

    behavior of "RSS item from XML"

    it should "create an rss item with from an rss xml node with no optional links" in {
        val rssXML = convertRssItemToXml(rssItem)

        assertResult(rssItem) {
            convertRssItemFromXML(rssXML)
        }
    }

    it should "create an rss item with from an rss xml node with a link and image" in {
        val rssXML = convertRssItemToXml(rssItem)

        assertResult(rssItem) {
            convertRssItemFromXML(rssXML)
        }
    }

    behavior of "RssItems convertRssItemsToXML "

    it should "create XML from an RssItem instance" in {
        val rssItems = new RssItems(Set(rssItem))

        val rssItemsXML = convertRssItemsToXML(rssItems)

        assertResult(1) {
            (rssItemsXML \ "item").size
        }
    }

    behavior of "RssItems convertRssItemsFromXML "

    it should "converts XML to an RssItems instance" in {
        val rssItems = new RssItems(Set(rssItem))

        val rssItemsXML = convertRssItemsToXML(rssItems)

        val items = convertRssItemsFromXML(rssItemsXML)

        assertResult(1) {
            items.ids.size
        }
    }

    it should "converts XML with multiple items in it to an RssItems instance" in {
        val rssItems = new RssItems(Set(rssItem, rssItemNoLinks))
        val rssItemsXML = convertRssItemsToXML(rssItems)

        val items = convertRssItemsFromXML(rssItemsXML)

        assertResult(2) {
            items.size
        }

        assertResult(Set(adId, adIdWithNoImage)) {
            items.ids
        }
    }
}
