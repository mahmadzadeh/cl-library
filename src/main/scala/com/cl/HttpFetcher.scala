package com.cl

import com.utils.HttpUtil
import scala.util.{Success, Failure, Try}
import org.apache.commons.httpclient.methods.GetMethod
import com.cl.url.{CLUrl, Query}

class HttpFetcher(httpUtil: HttpUtil) {

    def fetchUrl(clUrl: CLUrl): Try[String] = fetchUrl(clUrl.url)

    def fetchUrl(url: String): Try[String] = {

        httpUtil.execute(new GetMethod(url))
            .fold(
                error => Failure(new PageFetchException(error)),
                success => Success(success)
            )
    }
}

class PageFetchException(cause: String) extends Exception(cause)