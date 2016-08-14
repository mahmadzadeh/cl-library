package com.cl.url

import com.cl.url.SearchCategory.SearchCategory

import com.utils.FormatUtil.fmtDouble
import com.cl.url.CLQueryParameter._

class Query (val queryParameters: Map[CLQueryParameter,String],
             val searchCategory :SearchCategory ) {

    def this(searchCategory :SearchCategory) = this(Map[CLQueryParameter,String](), searchCategory)

    def withImage :Query = add(CLQueryParameter.IMAGE, "true")

    def withMinPrice(minPrice:Double) :Query = add(CLQueryParameter.PRICE_MIN, fmtDouble(minPrice))

    def withMaxPrice(maxPrice:Double) :Query = add(CLQueryParameter.PRICE_MAX, fmtDouble(maxPrice))

    def withSearchScope(searchScope:String) :Query = add(CLQueryParameter.SEARCH_SCOPE, searchScope)

    def inRSSFormat:Query = add(CLQueryParameter.FORMAT, "rss")

    def addOffset(offset:Int) = add(CLQueryParameter.PAGINATION, s"${offset}")

    def buildQueryString :String =
        "search" + "/" + searchCategory.toString + "?" +
                                                queryParameters.collect { case (k,v) => s"${k}=${v}" }.mkString("&")

    def add(searchTerm:CLQueryParameter,  value: String):Query =
        new Query( queryParameters + (searchTerm-> value), searchCategory )

}
