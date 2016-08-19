package com.cl.result

import com.cl.url.{CLUrl, City, Query, SearchCategory}
import com.cl.{FileBasedTest, HttpFetcher}
import com.utils.HttpUtil
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class RssItemsPaginationTest extends FlatSpec with Matchers with FileBasedTest  with MockitoSugar{

    behavior of "a Craigs list rss search result traverser"

    val query  = new CLUrl(City.VANCOUVER, new Query(SearchCategory.CARS_TRUCKS_ALL).withImage.withMaxPrice(2000))
    val result = new RssItems()

    it should "take a craigs list url and an http fetcher"  in {
        val httpClient = HttpUtil(new HttpClient())
        new ResultPagination(new HttpFetcher(httpClient))
    }

    it should "return an empty result when url returns no result"  in {
        val httpRequestResponse= readFile("rssZeroItem.xml")
        val mockHttpUtil = mock[HttpUtil]
        when(mockHttpUtil.execute(any[GetMethod])).thenReturn(Right(httpRequestResponse))

        val fetcher: HttpFetcher = new HttpFetcher(mockHttpUtil)

        assertResult(0) {
            new ResultPagination(fetcher).traverse(query, result).size
        }

        verify(mockHttpUtil).execute(any[GetMethod])
    }

    it should "return a result of size 1 when url returns single result"  in {
        val httpRequestResponse= readFile("rssSingleItem.xml")
        val mockHttpUtil = mock[HttpUtil]

        when(mockHttpUtil.execute(any[GetMethod])).thenReturn(Right(httpRequestResponse))

        val fetcher: HttpFetcher = new HttpFetcher(mockHttpUtil)

        assertResult(1) {
            new ResultPagination(fetcher).traverse(query, result).size
        }

        verify(mockHttpUtil).execute(any[GetMethod])
    }

    it should "return a result with a single page result"  in {

        val httpRequestResponse= readFile("rss.xml")
        val secondRequestResponse= readFile("rssZeroItem.xml")

        val mockHttpUtil = mock[HttpUtil]

        when(mockHttpUtil.execute(any[GetMethod]))
                          .thenReturn(Right(httpRequestResponse))
                          .thenReturn(Right(secondRequestResponse))

        val fetcher: HttpFetcher = new HttpFetcher(mockHttpUtil)

        assertResult(25) {
            new ResultPagination(fetcher).traverse(query, result).size
        }

        verify(mockHttpUtil, times(2)).execute(any[GetMethod])
    }

    it should "return a result with 2 pages"  in {
        val firstHttpResponse= readFile("rss.xml")
        val secondHttpResponse= readFile("rssPageTwo.xml")
        val thirdHttpResponse = readFile("rssZeroItem.xml")

        val mockHttpUtil = mock[HttpUtil]

        when(mockHttpUtil.execute(any[GetMethod]))
                    .thenReturn(Right(firstHttpResponse))
                    .thenReturn(Right(secondHttpResponse))
                    .thenReturn(Right(thirdHttpResponse))

        val fetcher: HttpFetcher = new HttpFetcher(mockHttpUtil)

        assertResult(50) {
            new ResultPagination(fetcher).traverse(query, result).size
        }

        verify(mockHttpUtil, times(3)).execute(any[GetMethod])
    }
}





