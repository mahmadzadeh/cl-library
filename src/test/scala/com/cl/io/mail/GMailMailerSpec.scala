package com.cl.io.mail

import javax.mail.internet.MimeMessage
import javax.mail.{MessagingException, SendFailedException}

import com.sun.mail.smtp.SMTPTransport
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class GMailMailerSpec extends FlatSpec with Matchers with MockitoSugar {

    val mailConfig = new GMailMailConfiguration("joe", "password", "joe@gmail.com", "joe@gmail.com", "joe@gmail.com", "test mail body")

    def fixture = new {
        val mockTransport = mock[ SMTPTransport ]
        val mockMimeMessage = mock[ MimeMessage ]
    }

    behavior of "send method"

    it should "close the transport when connect throws an exception" in {

        val mockTransport = fixture.mockTransport
        val mockMimeMessage = fixture.mockMimeMessage

        when(mockTransport.connect(mailConfig.host, mailConfig.userName, mailConfig.password)).thenThrow(new MessagingException("oopsie"))

        val failedTry = GMailMailer.send(mockTransport, mockMimeMessage, mailConfig)

        verify(mockTransport).close()

        assert(failedTry.isFailure)
    }

    it should "close the transport when sendMessage throws an exception" in {

        val mockTransport = fixture.mockTransport
        val mockMimeMessage = fixture.mockMimeMessage

        doNothing().when(mockTransport).connect(mailConfig.host, mailConfig.userName, mailConfig.password)
        when(mockTransport.sendMessage(any[ MimeMessage ], anyObject())).thenThrow(new SendFailedException("say what!!!"))

        val failedTry = GMailMailer.send(mockTransport, mockMimeMessage, mailConfig)

        verify(mockTransport).close()

        assert(failedTry.isFailure)
    }

    it should "return success of Unit when all is well" in {

        val mockTransport = fixture.mockTransport
        val mockMimeMessage = fixture.mockMimeMessage

        doNothing().when(mockTransport).connect(mailConfig.host, mailConfig.userName, mailConfig.password)
        doNothing().when(mockTransport).sendMessage(any[ MimeMessage ], anyObject())

        val successTry = GMailMailer.send(mockTransport, mockMimeMessage, mailConfig)

        verify(mockTransport).close()

        assert(successTry.isSuccess)
    }


}
