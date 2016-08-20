package com.cl.io

import com.cl.result.RssItems

trait Persistence {

    def read(): RssItems

    def write(rssItems: RssItems)
}
