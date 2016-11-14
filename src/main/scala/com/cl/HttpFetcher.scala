package com.cl

import com.cl.url.CLUrl
import com.utils.HttpUtil
import org.apache.commons.httpclient.methods.GetMethod

import scala.util.{Failure, Success, Try}

class HttpFetcher(httpUtil: HttpUtil) {

    def fetchUrl(clUrl: CLUrl): Try[ String ] = fetchUrl( clUrl.url.toString )

    def fetchUrl(url: String): Try[ String ] = {

        httpUtil.execute( new GetMethod( url ) )
            .fold(
                error => Failure( new PageFetchException( error ) ),
                success => Success( success )
            )
    }
}

class PageFetchException(cause: String) extends Exception( cause )