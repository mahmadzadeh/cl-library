package com.cl.io.mail

import javax.mail._
import javax.mail.internet._

import com.sun.mail.smtp._

import scala.util.Try

object GMailMailer extends MailAPI {

    def send(transport: SMTPTransport, msg: MimeMessage, mailConfig: MailConfiguration ) : Try[Unit]  = Try {

        try {
            transport.connect(mailConfig.host, mailConfig.userName, mailConfig.password)
            transport.sendMessage(msg, msg.getAllRecipients)
        } catch {
            case m: MessagingException  => throw new RuntimeException(s"Messaging Exception", m)
            case m: SendFailedException => throw new RuntimeException(s"Send Failed", m)
        } finally {
            transport.close()
        }

    }
}
