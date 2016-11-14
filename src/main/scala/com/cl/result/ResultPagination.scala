package com.cl.result

import com.cl.url.{CLQueryParameter, CLUrl, Query}
import com.cl.{HttpFetcher, Logs}

import scala.annotation.tailrec
import scala.util.{Failure, Success}

object ResultPagination {
    /**
     * default max number of pages retrieved per query
     */
    val MAX_RESULT_PAGE_RETRIEVAL: Integer = 10

    /**
     * number of results per RSS page. It "seems" to be 25 for all
     * RSS result pages
     */
    val MAX_RESULT_PER_PAGE: Integer = 25
}

class ResultPagination(httpUtil: HttpFetcher, depth: Integer = ResultPagination.MAX_RESULT_PAGE_RETRIEVAL) extends Logs {

    import ResultPagination._

    @tailrec
    final def traverse(url: CLUrl, acc: RssItems, currDepth: Integer = 1): RssItems = {

        def hasReachedBaseCase(itemCount: Integer): Boolean = itemCount < MAX_RESULT_PER_PAGE ||
            currDepth > MAX_RESULT_PAGE_RETRIEVAL
        def queryForNextPage(currentItemCount: Int): Query = {

            val currOffset: Int = url.query
                .getQueryParameterValue( CLQueryParameter.PAGINATION )
                .map( _.toInt )
                .getOrElse( 0 )

            url.query.addOffset( currentItemCount + currOffset )
        }

        httpUtil.fetchUrl( url ) match {
            case Success( page ) =>
                val rssResult = new RssResultPage( page )

                val itemCount = rssResult.itemCount

                if (hasReachedBaseCase( itemCount )) {
                    return acc.addSinglePageResult( rssResult.items.toSet )
                } else {
                    val query = queryForNextPage( itemCount )
                    traverse( new CLUrl( url.city, query ),
                        acc.addSinglePageResult( rssResult.items.toSet ),
                        currDepth + 1 )
                }

            case Failure( e ) => return acc
        }
    }
}

