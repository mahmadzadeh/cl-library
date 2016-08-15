package com.cl.result

import scala.xml.Node
import java.net.URL
import scala.util.Try
import org.joda.time.format.DateTimeFormat.forPattern
import com.cl.RssResultPage._
import scala.util.Failure
import scala.Some

class RssSingleItemParser {

    def anRssItem(singleRssItemXml:Node):Try[RssItem]= {
        val title = (singleRssItemXml \ "title").head.text

        val desc  = (singleRssItemXml \ "description").head.text
        val date  = forPattern(DATE_PATTERN).parseDateTime((singleRssItemXml \ "date").head.text)

        val link  = Try (new URL((singleRssItemXml \ "link").head.text))
        val image = Try (new URL((singleRssItemXml \ "enclosure" \ "@resource").head.text))

        for(
            l  <- link;
            id <- getRssItemId(l.toString)
        )
        yield RssItem(id, title = title, desc, date, link.toOption, image.toOption)
    }

    def getRssItemId(adUrl:String ):Try[Long] = {
        val idPattern = """\d{4,}\.html""".r

        idPattern findFirstIn (adUrl) match {
            case Some(id) => Try(id.replaceAll("""\.html""", "").toLong)
            case None     => Failure(new IllegalArgumentException(s"unable to parse ID from url ${adUrl}"))
        }
    }
}
