package com.cl.result

import com.cl.url.CLUrl
import com.cl.{HttpFetcher, Logs}

import scala.annotation.tailrec
import scala.util.{Failure, Success}

object ResultPagination {
    /**
     * default max number of pages retrieved per query
     */
    val MAX_RESULT_PAGE_RETRIEVAL: Integer = 5

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

        httpUtil.fetchUrl(url) match {
            case Success(page) =>
                val rssResult = new RssResultPage(page)
                val itemCount = rssResult.itemCount

                if (hasReachedBaseCase(itemCount)) {
                    return acc.addSinglePageResult(rssResult.items.toSet)
                } else {
                    val query = url.query.addOffset(itemCount)
                    traverse(new CLUrl(url.city, query),
                        acc.addSinglePageResult(rssResult.items.toSet),
                        currDepth + 1)
                }

            case Failure(e) => return acc
        }
    }
}

