package com.cl.result

import java.net.URL

import org.joda.time.DateTime

case class RssItem(adId: Long,
                   title: String,
                   description: String,
                   date: DateTime,
                   link: Option[ URL ],
                   image: Option[ URL ])