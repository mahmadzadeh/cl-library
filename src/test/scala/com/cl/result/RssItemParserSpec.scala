package com.cl.result

import java.net.URL

import com.cl.FileBasedTest
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Success
import scala.xml.XML

class RssItemParserSpec extends FlatSpec with Matchers with FileBasedTest {

    val date: DateTime = new DateTime()
    val linkURL = Some(new URL("http://google.com/action?one=1&two=2"))
    val imageURL = Some(new URL("http://google.com/action?one=1&two=2"))

    behavior of "RSS item parser"

    it should "return an RssItem when all is well" in {
        val xml = XML.loadString(readFile("rssSingleItem.xml"))
        val itemNode = (xml \\ "item").head

        val anItem = new RssSingleItemParser().anRssItem(itemNode)

        assert(anItem.isSuccess)

        val item = anItem.get
        assertResult( """BMW 525i  15" STOCK RIMS Wheels 1991 Used with BOLTS Tires (South-Surrey) &#x0024;120""") {
            item.title
        }

        assertResult(Some(new URL("http://vancouver.craigslist.ca/rds/pts/4612923391.html"))) {
            item.link
        }

        assertResult(4612923391l) {
            item.adId
        }

        assertResult(Some(new URL("http://images.craigslist.org/00101_jnwmmtQrMiC_300x300.jpg"))) {
            item.image
        }

        assert(item.description.contains("15\" BMW 525i Stock Rims Wheels"))

        assertResult(DateTimeFormat.forPattern(RssResultPage.DATE_PATTERN).parseDateTime("2014-08-15T19:27:21-07:00")) {
            item.date
        }
    }

    it should "return None for ad ID given a mal-formed CL URL" in {
        assertResult(true) {
            new RssSingleItemParser().getRssItemId("http://some.yurl.does.not.have/id").isFailure
        }
    }

    it should "return Some ID for ad ID given a well-formed CL URL" in {
        assertResult(Success(12345)) {
            new RssSingleItemParser().getRssItemId("http://some.yurl.does.not.have/12345.html")
        }
    }


}
