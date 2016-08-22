package com.cl.url

import java.net.URLEncoder

import com.cl.url.CLQueryParameter._
import com.cl.url.SearchCategory.SearchCategory
import com.utils.FormatUtil.fmtDouble

class Query(val queryParameters: Map[CLQueryParameter, String],
            val searchCategory: SearchCategory) {

    def this(searchCategory: SearchCategory) = this(Map[CLQueryParameter, String](), searchCategory)


    def withImage: Query = add(CLQueryParameter.IMAGE, "true")

    def withMinPrice(minPrice: Double): Query = add(CLQueryParameter.PRICE_MIN, fmtDouble(minPrice))

    def withMaxPrice(maxPrice: Double): Query = add(CLQueryParameter.PRICE_MAX, fmtDouble(maxPrice))

    def withSearchScope(searchScope: String): Query = add(CLQueryParameter.SEARCH_SCOPE, searchScope)

    def inRSSFormat: Query = add(CLQueryParameter.FORMAT, "rss")

    def withQueryText(queryString: String): Query = add(CLQueryParameter.QUERY, URLEncoder.encode(queryString, "UTF-8"))

    def addOffset(offset: Int) = add(CLQueryParameter.PAGINATION, s"${offset}")

    def buildQueryString: String =
        "search" + "/" + searchCategory.toString + "?" +
            queryParameters.collect { case (k, v) => s"${k}=${v}" }.mkString("&")

    def add(searchTerm: CLQueryParameter, value: String): Query =
        new Query(queryParameters + (searchTerm -> value), searchCategory)

    def getQueryParameterValue(param: CLQueryParameter): Option[String] =
        queryParameters.filter { case (k,v) => param.compare(k) == 0 }
            .map{ case (k,v) => v.toString }.headOption

}
