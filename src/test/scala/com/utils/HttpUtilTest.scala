package com.utils

import org.scalatest.Assertions
import org.apache.commons.httpclient.HttpClient
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.mock.MockitoSugar

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.apache.commons.httpclient.methods.{PostMethod, GetMethod}

import org.mockito.Mockito._
import java.io._
import com.cl.FileBasedTest
import scala.util.Try
import javax.xml.validation.SchemaFactory
import javax.xml.transform.stream.StreamSource

@RunWith(classOf[JUnitRunner])
class HttpUtilTest extends  FunSuite with BeforeAndAfter with Assertions with MockitoSugar with FileBasedTest {

    test("create http util object") {
        val hu = HttpUtil(new HttpClient())
    }

    test("can get content at url") {

        val mockHttpClient = mock[HttpClient]
        val mockGetMethod = mock[GetMethod]

        when(mockHttpClient.executeMethod(mockGetMethod)).thenReturn(200)

        val expectedResponse: String = "Here is the response from HTTP"

        when(mockGetMethod.getResponseBodyAsString()).thenReturn(expectedResponse)

        val hu = HttpUtil(mockHttpClient)


        expect(Right(expectedResponse)) {
            hu.execute(mockGetMethod)
        }
    }

    test("http client throws io exception when executing method") {

        val mockHttpClient = mock[HttpClient]
        val mockGetMethod = mock[GetMethod]

        when(mockHttpClient.executeMethod(mockGetMethod)).thenThrow(new IOException("Somethign went wrong..."))

        val hu = HttpUtil(mockHttpClient)

        val expectedError = hu.execute(mockGetMethod)

        assert(expectedError.isLeft)

        assert(expectedError.left.getOrElse(fail("Exepcted a left to be returned")).startsWith("IOException"))
    }
}
