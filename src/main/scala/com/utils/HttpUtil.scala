package com.utils

import org.apache.commons.httpclient.{HttpException, HttpClient, HttpMethod}
import org.apache.commons.httpclient.HttpStatus._
import java.io.IOException


object HttpUtil {
    def apply(httpClient: HttpClient): HttpUtil = new HttpUtil(httpClient)
}

/**
 *
 * @param httpClient: http client used to make an http request.
 *                    If the util class is going to be used within multiple threads then the client
 *                    of the class has the responsibility to use an http client with a connection manager.
 *
 */
class HttpUtil(httpClient: HttpClient) {

    require(httpClient !=null )

    def execute(method: HttpMethod): Either[String, String] =
        executeMethod(method) fold (
            error    => Left(error), 
            httpCode => getResponseAsString(httpCode, method)
        )


    private def executeMethod(method: HttpMethod): Either[String, Int] = try {

        Right(httpClient.executeMethod(method))

    } catch {
        case ioe: IOException => Left("IOException while executing the http method..." + ioe.getMessage)

        case he: HttpException => Left("HttpException  while executing the http method..." + he.getMessage)
    }

    private def getResponseAsString(status: Int, httpMethod: HttpMethod): Either[String, String] = {
        status match {
            case SC_OK | SC_CREATED | SC_ACCEPTED => try {
                Right(httpMethod.getResponseBodyAsString)
            } catch {
                case e: IOException => Left("Got http 200 but unable to get response body as string. Error: " + e.getMessage)
            }
            case SC_BAD_REQUEST => Left("Http SC_BAD_REQUEST was received from " + httpMethod.getURI)
            case error: Int => Left("Http error status was received from server. Http error code: " + error)
        }
    }
}
