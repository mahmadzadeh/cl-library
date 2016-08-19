package com.cl.result

import org.joda.time.DateTime
import java.net.URL

case class RssItem(adId: Long,
                   title: String,
                   description: String,
                   date: DateTime,
                   link: Option[URL],
                   image: Option[URL])