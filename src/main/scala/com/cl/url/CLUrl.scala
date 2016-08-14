package com.cl.url

import com.cl.url.City.CLCity

/**
 * Created by mahmadzadeh on 16/06/14.
 */
class CLUrl(val city:CLCity, val query :Query) {

    val hostName =   UrlHostName.getHostNameForCity(city)
    val url  =  hostName  + "/" + query.buildQueryString
}
