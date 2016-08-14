package com.cl.url

/**
 * Created by mahmadzadeh on 16/06/14.
 */
object SearchCategory extends Enumeration {
    type SearchCategory = Value

    val CARS_TRUCKS_ALL = Value("cta")
    val COLLECTABLES    = Value("cba")
    val ELECTRONICS     = Value("ela")
    val JEWLERY         = Value("jwa")
}
