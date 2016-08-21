package com.cl.io

import com.cl.result.RssItems

import scala.util.Try

trait Persistence {

    def read(): Try[RssItems]

    def write(rssItems: RssItems)
}
