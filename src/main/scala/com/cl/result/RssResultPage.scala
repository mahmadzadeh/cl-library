package com.cl.result

import java.net.URL

import com.cl.Logs
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.util.{Failure, Success}
import scala.xml.XML


object RssResultPage {
    val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ"
}

class RssResultPage(content: String) extends Logs {
    require( content != null && content != "" )

    val xml = XML.loadString( content )

    def itemCount: Int = ( xml \\ "items" \ "Seq" \ "li" ).size

    def link: URL = new URL( ( xml \\ "channel" \ "link" ).text )

    def updateDate: DateTime = DateTimeFormat.forPattern( RssResultPage.DATE_PATTERN ).parseDateTime( ( xml \\ "updateBase" ).text )

    def items: List[ RssItem ] = ( xml \\ "item" ).foldLeft( List[ RssItem ]( ) ) { (acc, item) =>
        new RssSingleItemParser( ).anRssItem( item ) match {
            case Success( item ) => item :: acc
            case Failure( e )    => /*no-op ignore the failed RSS item */
                error( "unable to parse an RSS item from ", e )
                acc
        }
    }
}