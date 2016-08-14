package com.cl.result

import org.jsoup.Jsoup
import scala.collection.JavaConversions._
import org.jsoup.nodes.{Element => elem}


class AdPage(val page: Option[String]) {
    require(page isDefined)

    val document = Jsoup.parse(page get)
    val postingIdPattern = """\d{5,}""".r

    val postingInfoClassName: String = ".postinginfo"
    val postingInfoText: String = "Posted"
    val postingIDText: String = "Posting ID"


    def getTitle(): Option[String] = {
        val titleTag = document.select(".postingtitle")

        if (!titleTag.isEmpty)
            Some(titleTag.text())
        else
            None
    }

    /**
     *
     * TODO: return an option
     */
    def getAdId(): String = {

        extractElement(postingInfoClassName, postingIDText) match {

            case Some(element) =>

                postingIdPattern findFirstIn (element.text()) match {
                    case Some(g) => g
                    case None => "ZIP was extracted..."

                }

            case None => "Nothing was found....."
        }

    }

    def getAdPostingEpochTime(): Option[String] = {

        extractElement(postingInfoClassName, postingInfoText) match {

            case Some(ele) => Some(ele.select("date").attr("title"))
            case None => None
        }
    }

    def extractElement(postingInfoClassName: String, postingInfoText: String): Option[elem] = {

        document.select(postingInfoClassName).iterator().find {
            it: elem =>
                it.text().startsWith(postingInfoText)
        }

    }
}


