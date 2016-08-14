package com.cl

import org.scalatest.{BeforeAndAfter, FunSuite, Assertions}
import com.cl.url._
import scala.Some
import CLQueryParameter._
import SearchCategory._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.cl.url.CLQueryParameter
import com.cl.url.SearchParameter
import scala.Some

@RunWith(classOf[JUnitRunner])
class QueryTest extends FunSuite with BeforeAndAfter with Assertions {

    test("create a craigs list query") {
        val v = new Query(Map(), CARS_TRUCKS_ALL)
    }

    test("query adding a parameter to query") {

        val query = new Query(Map[CLQueryParameter,String](), CARS_TRUCKS_ALL)
                    .withImage

        assertResult(1) {
            query.queryParameters.size
        }
    }

    test("query adding multiple parameters to query") {

        val query = new Query(Map[CLQueryParameter,String](), CARS_TRUCKS_ALL)
                        .withImage
                        .withMaxPrice(2000)
                        .withMinPrice(1000)
                        .withSearchScope("T")

        assertResult(4) {
            query.queryParameters.size
        }

        assertSearchTermExist(query, CLQueryParameter.IMAGE)
        assertSearchTermExist(query, CLQueryParameter.PRICE_MAX)
        assertSearchTermExist(query, CLQueryParameter.PRICE_MIN)
        assertSearchTermExist(query, CLQueryParameter.SEARCH_SCOPE)
    }

    test("a query with required images in the result can be built") {
        val query = new Query(SearchCategory.CARS_TRUCKS_ALL)

        assertResult(1) {
            query.withImage.queryParameters.size
        }

    }

    test("a query with required min/max price in the result can be built") {
        val query = new Query(SearchCategory.CARS_TRUCKS_ALL)

        assertResult(3) {
            query
            .withImage
            .withMinPrice(2000)
            .withMaxPrice(2500)
            .queryParameters
            .size
        }
    }

    test("generate the right http query") {
        val query = new Query(SearchCategory.CARS_TRUCKS_ALL)


         assertResult("search/cta?hasPic=true&minAsk=2000&maxAsk=2500&format=rss"){
             query
            .withImage
            .withMinPrice(2000)
            .withMaxPrice(2500)
            .inRSSFormat
            .buildQueryString
        }
    }

    test("can add pagination offset to query") {
        val query = new Query(SearchCategory.CARS_TRUCKS_ALL)

        assertResult(1) {
            query.addOffset(100).queryParameters.size
        }
    }

    test("can add pagination offset to query that already includes a pagination offset param") {
        val query = new Query(SearchCategory.CARS_TRUCKS_ALL)

        assertResult(1) {
            val q = query.addOffset(100).addOffset(200).addOffset(300)
            q.queryParameters.size
        }
    }

    def assertQueryContains(query: String, term: SearchParameter) {
        assert(query.contains(term.toString))
    }

    def assertSearchTermExist(query: Query, queryParam: CLQueryParameter) {
        assert(query.queryParameters.exists { _._1 == queryParam})
    }
}


