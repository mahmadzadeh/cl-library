package com.cl.result


class Result(val items:Set[RssItem] = Nil.toSet) {

    def addSinglePageResult(rssItems:Set[RssItem]):Result = new Result(items ++ rssItems)
    def size:Int = items.size

    /**
     * set difference. rss items in this result that are missing
     * in the other result
     */
    def minus(other:Result):Result = {
        val thisAddIdSet = this.items.collect {case item => item.adId}.toSet
        val thatAddIdSet = other.items.collect {case item => item.adId}.toSet

        // rss ids that are present in this result but missing in other result
        val diff = thisAddIdSet -- thatAddIdSet

        new Result(items.filter {
            rssItem => diff.contains(rssItem.adId)
        })
    }
}
