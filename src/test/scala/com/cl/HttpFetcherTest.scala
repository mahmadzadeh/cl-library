package com.cl

import com.cl.url.CLUrl
import com.utils.HttpUtil
import org.apache.commons.httpclient.methods.GetMethod
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar


@RunWith(classOf[ JUnitRunner ])
class HttpFetcherTest extends FlatSpec with Matchers with MockitoSugar {


    "A PageFetcher" should "return Failure when http request fails" in {
        val mockHttpUtil = mock[ HttpUtil ]
        when(mockHttpUtil.execute(any[ GetMethod ])).thenReturn(Left("oops"))

        val mockQuery = mock[ CLUrl ]
        when(mockQuery.url).thenReturn("http://some.url.com/q?123")

        val pageFetcher = new HttpFetcher(mockHttpUtil)

        val failure = pageFetcher.fetchUrl(mockQuery)

        assert(failure.isFailure)

    }

    "A PageFetcher" should "return success when http request is successfully executed" in {
        val httpRequestResponse = "request content"

        val mockHttpUtil = mock[ HttpUtil ]
        when(mockHttpUtil.execute(any[ GetMethod ])).thenReturn(Right(httpRequestResponse))

        val mockQuery = mock[ CLUrl ]
        when(mockQuery.url).thenReturn("http://some.url.com/q?123")

        val pageFetcher = new HttpFetcher(mockHttpUtil)

        val failure = pageFetcher.fetchUrl(mockQuery)

        assert(failure.isSuccess)

        assertResult(httpRequestResponse) {
            failure.get
        }

    }

}
