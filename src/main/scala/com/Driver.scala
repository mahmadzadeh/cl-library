package com

import com.cl.HttpFetcher
import com.cl.io.mail.GMailMailConfiguration
import com.cl.io.mail.GMailMailer.send
import com.cl.io.mail.Util.{formatRSSItemsIntoHTML, mailSession, mimeMessageForSession, transportFromSession}
import com.cl.io.persistence.FileBasedPersistence
import com.cl.result.{ResultPagination, RssItems}
import com.cl.url.CLQueryParameter._
import com.cl.url.City._
import com.cl.url.SearchCategory._
import com.cl.url.{CLUrl, Query}
import com.utils.HttpUtil
import org.apache.commons.httpclient.HttpClient
import org.joda.time.DateTime

import scala.util.Try


object Driver extends App {

    private val pagination = new ResultPagination(new HttpFetcher(HttpUtil(new HttpClient())))
    private val persistence = FileBasedPersistence()
    
    private val userName= "mahmadzadeh"
    private val password = "boguspwd"
    private val queryString= "BMW 335i"
    private val emailFrom = s"${userName}@gmail.com"
    private val emailSubject = "All from a test code"
    
    val configuration = new GMailMailConfiguration(userName, password, emailFrom, emailFrom, emailFrom, emailSubject)
    
    val query = new Query(Map[ CLQueryParameter, String ](), CARS_TRUCKS_ALL)
        .withSearchScope("A")
        .withMinPrice(10000)
        .withMaxPrice(70000)
        .withImage
        .withQueryText(queryString)
        .inRSSFormat

    val url = new CLUrl(VANCOUVER, query)

    while (true) {

        val newResult = pagination.traverse(url, new RssItems())

        val collectedAdIds = newResult.ids

        val diff = persistence.read().map(newResult.minus(_)).getOrElse(newResult)

        persistence.write(newResult)

        if (shouldEmail(diff)) {
            val itemsInHTMLFormat = formatRSSItemsIntoHTML(diff)
            sendEmail(configuration, itemsInHTMLFormat)
        } 
        
        Thread.sleep(1000 * 60 * 60)
    }

    private def shouldEmail(diff: RssItems): Boolean = diff.size > 0

    private def sendEmail(mailConfig: GMailMailConfiguration, msg: String): Try[ Unit ] = {

        val session = mailSession(mailConfig.toConnectionProperties)

        val mime = mimeMessageForSession(msg, session, new DateTime(), mailConfig)

        val SMTPTransport = transportFromSession(session)

        send(SMTPTransport, mime, mailConfig)
    }
}
