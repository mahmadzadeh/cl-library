package com.cl.url

import org.scalatest.{Matchers, FlatSpec}
import com.cl.url.SearchCategory._
import com.cl.url.City._
import com.cl.url.CLQueryParameter._

/**
 * Created by mahmadzadeh on 21/08/14.
 */
class CLUrlSpec extends  FlatSpec with Matchers  {

    behavior of "craigslist URL"

    it should "return the right URL for a given city and url" in {
        val query = new Query(Map[CLQueryParameter,String](), CARS_TRUCKS_ALL)
                    .withSearchScope("A")
                    .withMinPrice(1000)
                    .withMaxPrice(2000)
                    .withImage
                    .inRSSFormat

        val url = new CLUrl(VANCOUVER, query).url

        assert(url.contains(CLQueryParameter.SEARCH_SCOPE.toString))
        assert(url.contains(CLQueryParameter.PRICE_MIN.toString))
        assert(url.contains(CLQueryParameter.PRICE_MAX.toString))
        assert(url.contains(CLQueryParameter.FORMAT.toString))
        assert(url.contains(CLQueryParameter.IMAGE.toString))

    }

}
