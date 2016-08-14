package com.cl.url

object CLQueryParameter extends Enumeration {

    type CLQueryParameter = Value

    val QUERY        = Value("query")
    val SEARCH_SCOPE = Value("srchType") // A (ALL ENTIRE POST ) OR T ( TITLE )
    val PRICE_MIN    = Value("minAsk")
    val PRICE_MAX    = Value("maxAsk")
    val IMAGE        = Value("hasPic")
    val SELLER_TYPE  = Value("catAbb")
    val PAGINATION   = Value("s")
    val FORMAT       = Value("format")
}
