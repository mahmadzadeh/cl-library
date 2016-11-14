package com.cl

import com.cl.result.{ResultPagination, RssItems}
import com.cl.url.CLUrl

import scala.util.{Success, Try}

class Monitor() {

    def execute(resultPagination: ResultPagination, url: CLUrl): RssItems = resultPagination.traverse(url, new RssItems())

}
