package com.cl.io

import com.cl.result.RssItem

import scala.util.Try

trait Persistence {

    def read(): Try[Seq[RssItem]]

    def write(jsonData: Seq[RssItem])
}
