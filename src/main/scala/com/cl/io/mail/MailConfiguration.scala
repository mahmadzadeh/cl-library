package com.cl.io.mail

import java.util.Properties

class MailConfiguration(
                           val userName: String,
                           val password: String,
                           val host: String,
                           val port: Int,
                           val auth: Boolean,
                           val from: String,
                           val to: String,
                           val cc: String,
                           val subject: String) {

    def toConnectionProperties: Properties = {

        val props = new Properties()

        props.put("mail.smtp.host", host)
        props.put("mail.smtp.auth", auth.toString.toLowerCase())
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.port", port.toString)
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        props.put("mail.smtp.socketFactory.fallback", "false")

        props
    }
}

class GMailMailConfiguration(gMailUser: String,
                             gMailPassword: String,
                             gMailFrom: String,
                             gMailTo: String,
                             gMailCC: String,
                             gMailSubject: String) extends
MailConfiguration(
    gMailUser,
    gMailPassword,
    "smtp.gmail.com",
    465,
    true,
    gMailFrom,
    gMailTo,
    gMailCC,
    gMailSubject)
