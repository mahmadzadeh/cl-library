package com.cl.io

import java.net.URL

import com.cl.result.{RssItem, RssItems}
import org.joda.time.DateTime

import scala.xml.{Node, Elem, PCData}

object Converter {

    private val itemsNodeName= "ads"
    private val itemNodeName = "ad"

    def convertRssItemsFromXML(xml: Elem): RssItems =
        new RssItems((xml \\ itemsNodeName \ itemNodeName).map { oneItem => convertRssItemFromXML(oneItem) }.toSet)

    def convertRssItemsToXML(items: RssItems): Elem =
        <ads>
            {items.items.map(oneItem => convertRssItemToXml(oneItem))}
        </ads>

    def convertRssItemFromXML(elem: Node): RssItem =
        RssItem(
            (elem \ "adId").text.trim.toLong,
            (elem \ "title").text.trim,
            (elem \ "description").text.trim,
            new DateTime((elem \ "date").text.trim),
            (elem \ "link").filter(!_.text.isEmpty).map {linkNode => new URL(linkNode.text.trim) }.headOption,
            (elem \ "image").filter(!_.text.isEmpty).map(linkNode => new URL(linkNode.text.trim)).headOption)

    def convertRssItemToXml(item: RssItem): Elem =
        <ad>
            <adId>
                {item.adId}
            </adId>
            <title>
                {PCData(item.title)}
            </title>
            <description>
                {PCData(item.description)}
            </description>
            <date>
                {item.date}
            </date>
            {item.link.map(url => <link> {PCData(url.toString)}</link>).getOrElse(<link/>)}
            {item.image.map(url => <image>{PCData(url.toString)}</image>).getOrElse(<image/>)}
        </ad>

}
