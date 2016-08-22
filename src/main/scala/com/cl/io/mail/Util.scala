package com.cl.io.mail

import java.util.Properties
import javax.activation.DataHandler
import javax.mail.internet.{InternetAddress, MimeMessage}
import javax.mail.util.ByteArrayDataSource
import javax.mail.{Message, Session}

import com.cl.result.{RssItem, RssItems}
import com.sun.mail.smtp.SMTPTransport
import org.joda.time.DateTime

import scala.xml.{Node, Null, Text}

object Util {

    val MESSAGE_TYPE = "text/html"
    val SMTP_TRANSPORT_PROTOCOL = "smtp"

    def mailSession(properties: Properties): Session = Session.getInstance(properties, null)

    def mimeMessageForSession(message: String,
                              session: Session,
                              messageTime: DateTime,
                              mailConfig: MailConfiguration): MimeMessage = {

        val msg = new MimeMessage(session)
        msg.setFrom(new InternetAddress(mailConfig.from))
        msg.setRecipients(Message.RecipientType.TO, mailConfig.to)

        msg.setSubject(mailConfig.subject)
        msg.setSentDate(messageTime.toDate)

        msg.setDataHandler(new DataHandler(new ByteArrayDataSource(message, MESSAGE_TYPE)))

        msg
    }

    def transportFromSession(session: Session): SMTPTransport = session.getTransport(SMTP_TRANSPORT_PROTOCOL).asInstanceOf[ SMTPTransport ]

    def formatRSSItemsIntoHTML(rssItems: RssItems): String =
        ( <ul>
            {rssItems.items.map {
                item =>
                    <li>
                        {anchorTag(item)}
                    </li>
            }}
        </ul> ).toString()

    def anchorTag(rssItem: RssItem): Node =
        <a>
            {rssItem.title}{imageTag(rssItem)}
        </a> % scala.xml.Attribute(None, "href", Text(rssItem.link.getOrElse("#").toString), Null)

    def imageTag(rssItem: RssItem): Node = <image/> % scala.xml.Attribute(None, "src", Text(rssItem.image.getOrElse("#").toString), Null)
}
