package com

import com.cl.HttpFetcher
import com.cl.io.FileBasedPersistence
import com.cl.result.{ResultPagination, RssItems}
import com.cl.url.CLQueryParameter._
import com.cl.url.City._
import com.cl.url.SearchCategory._
import com.cl.url.{CLUrl, Query}
import com.utils.HttpUtil
import org.apache.commons.httpclient.HttpClient


object Main extends App {

    val pagination = new ResultPagination( new HttpFetcher( HttpUtil( new HttpClient())))

    val query = new Query(Map[CLQueryParameter,String](), CARS_TRUCKS_ALL)
        .withSearchScope("A")
        .withMinPrice(10000)
        .withMaxPrice(70000)
        .withImage
        .withQueryText("m3")
        .inRSSFormat


    val url = new CLUrl(VANCOUVER, query)

    val result = pagination.traverse(url, new RssItems())

    val collectedAdIds = result.ids

     val persistence = FileBasedPersistence()
//
//    val prevResult = persistence.readPreviousRunData()
//
//    val diff  = result.minus(prevResult)
//
//    display.printResult(diff)
//
//    persistence.persistResult(result)

}
