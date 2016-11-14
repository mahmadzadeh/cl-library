package com.cl.io.mail

import javax.mail.internet.MimeMessage

import com.sun.mail.smtp.SMTPTransport

import scala.util.Try

trait MailAPI {

    def send(transport: SMTPTransport, msg: MimeMessage, mailConfig: MailConfiguration): Try[ Unit ]

}
