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

    val pagination = new ResultPagination(new HttpFetcher(HttpUtil(new HttpClient())))

    val query = new Query(Map[CLQueryParameter, String](), CARS_TRUCKS_ALL)
        .withSearchScope("A")
        .withMinPrice(10000)
        .withMaxPrice(70000)
        .withImage
        .withQueryText("m3")
        .inRSSFormat

    val url = new CLUrl(VANCOUVER, query)

    while(true) {

        val newResult = pagination.traverse(url, new RssItems())

        val collectedAdIds = newResult.ids

        val persistence = FileBasedPersistence()

        val diff = persistence.read().map(newResult.minus(_)).getOrElse(newResult)

        println(diff.ids)

        persistence.write(newResult)

        Thread.sleep(1000 * 60  * 60 )
    }

}
